package nl.dias.web.medewerker;

import nl.dias.domein.ContactPersoon;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.mapper.JsonMedewerkerNaarMedewerkerMapper;
import nl.dias.mapper.Mapper;
import nl.dias.mapper.MedewerkerNaarJsonMedewerkerMapper;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.JsonRelatieMapper;
import nl.dias.web.mapper.RelatieMapper;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.*;
import nl.lakedigital.djfc.domain.response.Adres;
import nl.lakedigital.djfc.domain.response.Opmerking;
import nl.lakedigital.djfc.domain.response.RekeningNummer;
import nl.lakedigital.djfc.domain.response.Telefoonnummer;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestMapping("/gebruiker")
@Controller
public class GebruikerController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GebruikerController.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private RelatieMapper relatieMapper;
    @Inject
    private JsonRelatieMapper jsonRelatieMapper;
    @Inject
    private Mapper mapper;
    @Inject
    private AuthorisatieService authorisatieService;
    @Inject
    private MedewerkerNaarJsonMedewerkerMapper medewerkerNaarJsonMedewerkerMapper;
    @Inject
    private JsonMedewerkerNaarMedewerkerMapper jsonMedewerkerNaarMedewerkerMapper;
    @Inject
    private IdentificatieClient identificatieClient;

    @Inject
    private AdresController adresController;
    @Inject
    private TelefoonnummerController telefoonnummerController;
    @Inject
    private RekeningNummerController rekeningNummerController;
    @Inject
    private OpmerkingController opmerkingController;

    @RequestMapping(method = RequestMethod.GET, value = "/alleContactPersonen", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonContactPersoon> alleContactPersonen(@QueryParam("bedrijfsId") Long bedrijfsId) {
        List<JsonContactPersoon> result = new ArrayList<>();

        for (ContactPersoon contactPersoon : gebruikerService.alleContactPersonen(bedrijfsId)) {
            result.add(mapper.map(contactPersoon, JsonContactPersoon.class));
        }

        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonRelatie lees(@QueryParam("id") String id) {
        LOGGER.debug("Ophalen Relatie met id : " + id);

        JsonRelatie jsonRelatie;
        if (id != null && !"0".equals(id.trim())) {
            Relatie relatie = (Relatie) gebruikerService.lees(Long.parseLong(id));

            jsonRelatie = relatieMapper.mapNaarJson(relatie);
        } else {
            jsonRelatie = new JsonRelatie();
        }

        LOGGER.debug("Naar de front-end : " + jsonRelatie);

        return jsonRelatie;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/leesMedewerker", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonMedewerker leesMedewerker(@QueryParam("id") String id) {
        LOGGER.debug("Ophalen Relatie met id : " + id);

        JsonMedewerker jsonMedewerker;
        if (id != null && !"0".equals(id.trim())) {
            Medewerker medewerker = (Medewerker) gebruikerService.lees(Long.parseLong(id));

            jsonMedewerker = medewerkerNaarJsonMedewerkerMapper.map(medewerker);
        } else {
            jsonMedewerker = new JsonMedewerker();
        }

        LOGGER.debug("Naar de front-end : " + jsonMedewerker);

        return jsonMedewerker;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanMedewerker", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaanMedewerker(@RequestBody JsonMedewerker jsonMedewerker) {
        LOGGER.debug("opslaan medewerker");

        Medewerker medewerker = (Medewerker) gebruikerService.lees(jsonMedewerker.getId());

        gebruikerService.opslaan(jsonMedewerkerNaarMedewerkerMapper.map(jsonMedewerker, null, medewerker));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanContactPersoon")
    //, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String opslaanContactPersoon(@RequestBody JsonContactPersoon jsonContactPersoon, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        ContactPersoon contactPersoon = mapper.map(jsonContactPersoon, ContactPersoon.class);

        gebruikerService.opslaan(contactPersoon);

        Identificatie identificatie = identificatieClient.zoekIdentificatie("CONTACTPERSOON", contactPersoon.getId());

        return identificatie.getIdentificatie();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")//, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String opslaan(@RequestBody nl.lakedigital.djfc.domain.response.Relatie jsonRelatie, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LOGGER.info("Opslaan {}", ReflectionToStringBuilder.toString(jsonRelatie));

        zetSessieWaarden(httpServletRequest);

        if (jsonRelatie.getIdentificatie() != null) {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonRelatie.getIdentificatie());
            if (identificatie != null) {
                jsonRelatie.setId(identificatie.getEntiteitId());
            }
        }

        Medewerker medewerker = (Medewerker) getIngelogdeGebruiker(httpServletRequest);

        Kantoor kantoor = kantoorRepository.lees(medewerker.getKantoor().getId());

        Relatie relatie = jsonRelatieMapper.mapVanJson(jsonRelatie);
        relatie.setKantoor(kantoor);
        LOGGER.debug("Uit mapper " + relatie);

        LOGGER.debug("Opslaan Relatie met id " + relatie.getId());

        gebruikerService.opslaan(relatie);

        LOGGER.debug("Relatie met id " + relatie.getId() + " opgeslagen");

        if (jsonRelatie.getIdentificatie() == null) {
            Future<Identificatie> identificatieFuture = identificatieClient.zoekIdentificatieMetFuture("RELATIE", relatie.getId());

            Identificatie identificatie = null;
            try {
                identificatie = identificatieFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("Fout bij ophalen identificatie", e);
            }

            if (identificatie != null) {
                relatie.setIdentificatie(identificatie.getIdentificatie());
            } else {
                try {
                    Thread.sleep(3000);
                    Future<Identificatie> identificatieFuture1 = identificatieClient.zoekIdentificatieMetFuture("RELATIE", relatie.getId());

                    identificatie = identificatieFuture1.get();
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error("Fout bij ophalen identificatie", e);
                }
                if (identificatie != null) {
                    relatie.setIdentificatie(identificatie.getIdentificatie());
                }
            }
        }

        LOGGER.debug("Return {}", relatie.getIdentificatie());

        adresController.opslaan(jsonRelatie.getAdressen().stream().map(new Function<Adres, JsonAdres>() {
            @Override
            public JsonAdres apply(Adres adres) {
                JsonAdres jsonAdres = new JsonAdres();
                jsonAdres.setHuisnummer(adres.getHuisnummer());
                //                jsonAdres.setId(adres.geti);
                jsonAdres.setPlaats(adres.getPlaats());
                jsonAdres.setPostcode(adres.getPostcode());
                jsonAdres.setSoortAdres(adres.getSoortAdres());
                jsonAdres.setStraat(adres.getStraat());
                jsonAdres.setToevoeging(adres.getToevoeging());
                jsonAdres.setEntiteitId(relatie.getId());
                jsonAdres.setIdentificatie(adres.getIdentificatie());
                jsonAdres.setParentIdentificatie(relatie.getIdentificatie());
                jsonAdres.setSoortEntiteit("RELATIE");

                return jsonAdres;
            }
        }).collect(Collectors.toList()), httpServletRequest);
        telefoonnummerController.opslaan(jsonRelatie.getTelefoonnummers().stream().map(new Function<Telefoonnummer, JsonTelefoonnummer>() {
            @Override
            public JsonTelefoonnummer apply(Telefoonnummer telefoonnummer) {
                JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

                jsonTelefoonnummer.setOmschrijving(telefoonnummer.getOmschrijving());
                jsonTelefoonnummer.setSoort(telefoonnummer.getSoort());
                jsonTelefoonnummer.setTelefoonnummer(telefoonnummer.getTelefoonnummer());
                jsonTelefoonnummer.setEntiteitId(relatie.getId());
                jsonTelefoonnummer.setSoortEntiteit("RELATIE");
                jsonTelefoonnummer.setParentIdentificatie(relatie.getIdentificatie());

                return jsonTelefoonnummer;
            }
        }).collect(Collectors.toList()), httpServletRequest);
        rekeningNummerController.opslaan(jsonRelatie.getRekeningNummers().stream().map(new Function<RekeningNummer, JsonRekeningNummer>() {
            @Override
            public JsonRekeningNummer apply(RekeningNummer rekeningNummer) {
                JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();

                jsonRekeningNummer.setBic(rekeningNummer.getBic());
                jsonRekeningNummer.setRekeningnummer(rekeningNummer.getRekeningnummer());
                jsonRekeningNummer.setEntiteitId(relatie.getId());
                jsonRekeningNummer.setSoortEntiteit("RELATIE");
                jsonRekeningNummer.setParentIdentificatie(relatie.getIdentificatie());

                return jsonRekeningNummer;
            }
        }).collect(Collectors.toList()), httpServletRequest);
        opmerkingController.opslaan(jsonRelatie.getOpmerkingen().stream().map(new Function<Opmerking, JsonOpmerking>() {
            @Override
            public JsonOpmerking apply(Opmerking opmerking) {
                JsonOpmerking jsonOpmerking = new JsonOpmerking();

                jsonOpmerking.setMedewerker(opmerking.getMedewerker());
                jsonOpmerking.setOpmerking(opmerking.getOpmerking());
                jsonOpmerking.setTijd(opmerking.getTijd());
                jsonOpmerking.setEntiteitId(relatie.getId());
                jsonOpmerking.setSoortEntiteit("RELATIE");
                jsonOpmerking.setParentIdentificatie(relatie.getIdentificatie());
                jsonOpmerking.setMedewerkerId(opmerking.getMedewerkerId());

                return jsonOpmerking;
            }
        }).collect(Collectors.toList()), httpServletRequest);

        return relatie.getIdentificatie();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Verwijderen Relatie met id " + id);

        zetSessieWaarden(httpServletRequest);

        gebruikerService.verwijder(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/koppelenOnderlingeRelatie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void koppelenOnderlingeRelatie(@RequestBody JsonKoppelenOnderlingeRelatie jsonKoppelenOnderlingeRelatie, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        gebruikerService.koppelenOnderlingeRelatie(jsonKoppelenOnderlingeRelatie.getRelatie(), jsonKoppelenOnderlingeRelatie.getRelatieMet(), jsonKoppelenOnderlingeRelatie.getSoortRelatie());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanoauthcode", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaanOAuthCode(@RequestBody String code, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Opslaan Authcode {}", code);
        zetSessieWaarden(httpServletRequest);

        gebruikerService.opslaanOAuthCodeTodoist(code, getIngelogdeGebruiker(httpServletRequest).getId());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/leesoauthcode", produces = MediaType.TEXT_PLAIN)
    @ResponseBody
    public String leesOAuthCode(HttpServletRequest httpServletRequest) {
        LOGGER.debug("Lees Authcode ");

        return gebruikerService.leesOAuthCodeTodoist(getIngelogdeGebruiker(httpServletRequest).getId());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/wijzig-wachtwoord", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void wijzigWachtwoord(@RequestBody WachtwoordWijzigen wachtwoordWijzigen, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        LOGGER.debug(wachtwoordWijzigen.getIdentificatie());
        LOGGER.debug(wachtwoordWijzigen.getWachtwoord());
    }
}
