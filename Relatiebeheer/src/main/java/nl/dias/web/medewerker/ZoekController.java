package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import com.google.gson.Gson;
import nl.dias.ZoekResultaat;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.ZoekService;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.request.SoortEntiteit;
import nl.lakedigital.djfc.request.SoortEntiteitEnEntiteitId;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/zoeken")
public class ZoekController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZoekController.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private AdresClient adresClient;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private ZoekService zoekService;
    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ZoekResultaatResponse zoeken(HttpServletRequest httpServletRequest) {
        return zoeken("", 0L, httpServletRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekterm}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ZoekResultaatResponse zoeken(@PathVariable("zoekterm") String zoekTerm, HttpServletRequest httpServletRequest) {
        return zoeken(zoekTerm, 0L, httpServletRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoeken/{zoekterm}/{weglaten}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ZoekResultaatResponse zoeken(@PathVariable("zoekterm") String zoekTerm, @QueryParam("weglaten") Long weglaten, HttpServletRequest httpServletRequest) {
        Timer.Context timer;
        zetSessieWaarden(httpServletRequest);
        String decoded = new String(Base64.getDecoder().decode(zoekTerm));
        LOGGER.debug("Decoded zoekstring {}", decoded);
        ZoekVelden zoekVelden = new Gson().fromJson(decoded, ZoekVelden.class);

        List<Relatie> relaties = new ArrayList<>();
        List<Bedrijf> bedrijven = new ArrayList<>();

        if (zoekVelden != null && !zoekVelden.isEmpty()) {
            timer = metricsService.addTimerMetric("zoekenMetZoekwaarden", ZoekController.class);
            LOGGER.debug("We gaan zoeken");
            LocalDate geboortedatum = null;
            if (zoekVelden.getGeboortedatum() != null && !"".equals(zoekVelden.getGeboortedatum())) {
                geboortedatum = LocalDate.parse(zoekVelden.getGeboortedatum());
            }

            ZoekResultaat zoekResultaat = zoekService.zoek(zoekVelden.getNaam(), geboortedatum, zoekVelden.getTussenvoegsel(), zoekVelden.getPolisnummer(), zoekVelden.getVoorletters(), zoekVelden.getSchadenummer(), zoekVelden.getAdres(), zoekVelden.getPostcode(), zoekVelden.getWoonplaats(), zoekVelden.getBedrijf());
            relaties = zoekResultaat.getRelaties();
            bedrijven = zoekResultaat.getBedrijven();
        } else {
            timer = metricsService.addTimerMetric("zoekenZonderZoekwaarden", ZoekController.class);
            LOGGER.debug("We laten alles zien");
            List<Relatie> lijst = gebruikerService.alleRelaties(((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor());
            LOGGER.debug("Gevonden {} Relaties/Gebruikers", lijst.size());
            for (Gebruiker r : lijst) {
                LOGGER.trace("{}", r);
                relaties.add((Relatie) r);
            }
            LOGGER.debug("Dat waren de Relaties");
            List<Bedrijf> bedrijfjes = bedrijfService.alles();
            LOGGER.debug("En {} bedrijven gevonden", bedrijfjes.size());
            for (Bedrijf bedrijf : bedrijfjes) {
                LOGGER.trace("{}", bedrijf);
                bedrijven.add(bedrijf);
            }
            LOGGER.debug("En dat waren de bedrijven");
        }

        ZoekResultaatResponse zoekResultaatResponse = new ZoekResultaatResponse();

        zoekResultaatResponse.getBedrijfOfRelatieList().addAll(relaties.stream()//
                .filter(relatie -> {
                    if (getIngelogdeGebruiker(httpServletRequest) != null) {
                        return relatie.getKantoor().getId() == ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor().getId();
                    }
                    return false;
                }).map(relatie -> {
                    RelatieZoekResultaat relatieZoekResultaat = new RelatieZoekResultaat();

                    Identificatie identificatie = identificatieClient.zoekIdentificatie("RELATIE", relatie.getId());

                    if (identificatie != null) {
                        relatieZoekResultaat.setIdentificatie(identificatie.getIdentificatie());
                    }
                    relatieZoekResultaat.setId(relatie.getId());
                    relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                    relatieZoekResultaat.setRoepnaam(relatie.getVoornaam());
                    relatieZoekResultaat.setTussenvoegsel(relatie.getTussenvoegsel());
                    relatieZoekResultaat.setAchternaam(relatie.getAchternaam());
                    if (relatie.getGeboorteDatum() != null) {
                        relatieZoekResultaat.setGeboortedatum(relatie.getGeboorteDatum().toString("dd-MM-yyyy"));
                    }

                    List<JsonAdres> adressenBijRelatie = adresClient.lijst("RELATIE", relatie.getId());
                    relatieZoekResultaat.setAdres(adressenBijRelatie.stream()//
                            .filter(adres -> adres.getEntiteitId().equals(relatie.getId()))//
                            .filter(adres -> "WOONADRES".equals(adres.getSoortAdres()))//
                            .findFirst().orElse(null));
                    if (relatieZoekResultaat.getAdres() == null) {
                        relatieZoekResultaat.setAdres(adressenBijRelatie.stream()//
                                .filter(adres -> adres.getEntiteitId().equals(relatie.getId()))//
                                .filter(adres -> "POSTADRES".equals(adres.getSoortAdres()))//
                                .findFirst().orElse(null));
                    }

                    return relatieZoekResultaat;
                })//
                .collect(Collectors.toList()));

        zoekResultaatResponse.getBedrijfOfRelatieList().addAll(bedrijven.stream()//
                .filter(bedrijf -> {
                    if (getIngelogdeGebruiker(httpServletRequest) != null) {
                        return bedrijf.getKantoor() == ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor().getId();
                    }
                    return false;
                }).map(bedrijf -> {
                    BedrijfZoekResultaat bedrijfZoekResultaat = new BedrijfZoekResultaat();

                    Identificatie identificatie = identificatieClient.zoekIdentificatie("BEDRIJF", bedrijf.getId());

                    if (identificatie != null) {
                        bedrijfZoekResultaat.setIdentificatie(identificatie.getIdentificatie());
                    }

                    bedrijfZoekResultaat.setId(bedrijf.getId());
                    bedrijfZoekResultaat.setNaam(bedrijf.getNaam());

                    List<JsonAdres> adressenBijBedrijf = adresClient.lijst("BEDRIJF", bedrijf.getId());
                    bedrijfZoekResultaat.setAdres(adressenBijBedrijf.stream()//
                            .filter(adres -> adres.getEntiteitId().equals(bedrijf.getId()))//
                            .filter(adres -> "POSTADRES".equals(adres.getSoortAdres()))//
                            .findFirst().orElse(null));
                    if (bedrijfZoekResultaat.getAdres() == null) {
                        bedrijfZoekResultaat.setAdres(adressenBijBedrijf.stream()//
                                .filter(adres -> adres.getEntiteitId().equals(bedrijf.getId()))//
                                .filter(adres -> "WOONADRES".equals(adres.getSoortAdres()))//
                                .findFirst().orElse(null));
                    }

                    return bedrijfZoekResultaat;
                }).collect(Collectors.toList()));

        zoekResultaatResponse.getBedrijfOfRelatieList().sort((o1, o2) -> {
            String naam1 = bepaalNaam(o1);
            String naam2 = bepaalNaam(o2);

            return naam1.compareTo(naam2);
        });

        zoekResultaatResponse.setBedrijfOfRelatieList(zoekResultaatResponse.getBedrijfOfRelatieList().stream().map(new Function<BedrijfOfRelatie, BedrijfOfRelatie>() {
            @Override
            public BedrijfOfRelatie apply(BedrijfOfRelatie bedrijfOfRelatie) {
                if (bedrijfOfRelatie.getAdres() != null) {
                    SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
                    soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.ADRES);
                    soortEntiteitEnEntiteitId.setEntiteitId(bedrijfOfRelatie.getAdres().getId());
                    String identificatie = identificatieClient.zoekIdentificatie("ADRES", bedrijfOfRelatie.getAdres().getId()).getIdentificatie();

                    bedrijfOfRelatie.getAdres().setIdentificatie(identificatie);
                    bedrijfOfRelatie.getAdres().setId(null);
                    bedrijfOfRelatie.getAdres().setSoortEntiteit(null);
                    bedrijfOfRelatie.getAdres().setEntiteitId(null);
                }

                return bedrijfOfRelatie;
            }
        }).collect(Collectors.toList()));

        zoekResultaatResponse.getBedrijfOfRelatieList().stream().forEach(new Consumer<BedrijfOfRelatie>() {
            @Override
            public void accept(BedrijfOfRelatie bedrijfOfRelatie) {
                bedrijfOfRelatie.setId(null);
            }
        });

        metricsService.stop(timer);

        return zoekResultaatResponse;
    }

    private String bepaalNaam(BedrijfOfRelatie bedrijfOfRelatie) {
        if (bedrijfOfRelatie instanceof BedrijfZoekResultaat) {
            return ((BedrijfZoekResultaat) bedrijfOfRelatie).getNaam();
        } else if (bedrijfOfRelatie instanceof RelatieZoekResultaat) {
            return ((RelatieZoekResultaat) bedrijfOfRelatie).getAchternaam();
        }
        return null;
    }

}
