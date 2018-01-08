package nl.dias.web.medewerker;

import nl.dias.domein.Bedrijf;
import nl.dias.mapper.Mapper;
import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.dias.service.*;
import nl.dias.web.mapper.*;
import nl.dias.web.medewerker.mappers.DomainAdresNaarMessagingAdresMapper;
import nl.dias.web.medewerker.mappers.DomainOpmerkingNaarMessagingOpmerkingMapper;
import nl.dias.web.medewerker.mappers.DomainTelefoonnummerNaarMessagingTelefoonnummerMapper;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.*;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import nl.lakedigital.djfc.commons.json.JsonTelefonieBestand;
import nl.lakedigital.djfc.domain.response.Telefoongesprek;
import nl.lakedigital.djfc.domain.response.TelefoonnummerMetGesprekken;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/bedrijf")
@Controller
public class BedrijfController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfController.class);

    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private Mapper mapper;
    @Inject
    private BedrijfMapper bedrijfMapper;
    @Inject
    private JsonBedrijfMapper jsonBedrijfMapper;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private AdresClient adresClient;
    @Inject
    private BijlageClient bijlageClient;
    @Inject
    private GroepBijlagesClient groepBijlagesClient;
    @Inject
    private OpmerkingClient opmerkingClient;
    @Inject
    private RekeningClient rekeningClient;
    @Inject
    private TelefoonnummerClient telefoonnummerClient;
    @Inject
    private TelefonieBestandClient telefonieBestandClient;
    @Inject
    private RelatieService relatieService;
    @Inject
    private PolisClient polisClient;
    @Inject
    private PolisService polisService;
    @Inject
    private PolisMapper polisMapper;
    @Inject
    private SchadeService schadeService;
    @Inject
    private SchadeMapper schadeMapper;
    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")//, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String opslaanBedrijf(@RequestBody nl.lakedigital.djfc.domain.response.Bedrijf jsonBedrijf, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Opslaan {}", ReflectionToStringBuilder.toString(jsonBedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        zetSessieWaarden(httpServletRequest);

        LOGGER.debug("Opzoeken identificatie {}", jsonBedrijf.getIdentificatie());

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonBedrijf.getIdentificatie());
        if (identificatie != null) {
            LOGGER.debug("Opgehaalde identificatie : {}", ReflectionToStringBuilder.toString(identificatie));
            jsonBedrijf.setId(identificatie.getEntiteitId());
        }

        Bedrijf bedrijf = jsonBedrijfMapper.mapVanJson(jsonBedrijf);
        bedrijfService.opslaan(bedrijf);

        LOGGER.debug("Return {}", jsonBedrijf.getIdentificatie());

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();
        opslaanEntiteitenRequest.getLijst().addAll(jsonBedrijf.getAdressen().stream().map(new DomainAdresNaarMessagingAdresMapper(bedrijf.getId(), SoortEntiteit.BEDRIJF)).collect(Collectors.toList()));
        opslaanEntiteitenRequest.getLijst().addAll(jsonBedrijf.getTelefoonnummers().stream().map(new DomainTelefoonnummerNaarMessagingTelefoonnummerMapper(bedrijf.getId(), SoortEntiteit.BEDRIJF)).collect(Collectors.toList()));
        opslaanEntiteitenRequest.getLijst().addAll(jsonBedrijf.getOpmerkingen().stream().map(new DomainOpmerkingNaarMessagingOpmerkingMapper(bedrijf.getId(), SoortEntiteit.BEDRIJF)).collect(Collectors.toList()));

        opslaanEntiteitenRequest.setEntiteitId(bedrijf.getId());
        opslaanEntiteitenRequest.setSoortEntiteit(SoortEntiteit.BEDRIJF);

        opslaanEntiteitenRequestSender.send(opslaanEntiteitenRequest);

        return jsonBedrijf.getIdentificatie();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonBedrijf> lijst(@QueryParam("zoekTerm") String zoekTerm) {
        List<JsonBedrijf> bedrijven = new ArrayList<>();

        if (zoekTerm == null) {
            for (Bedrijf bedrijf : bedrijfService.alles()) {
                bedrijven.add(mapper.map(bedrijf, JsonBedrijf.class));
            }
        } else {
            for (Bedrijf bedrijf : bedrijfService.zoekOpNaam(zoekTerm)) {
                bedrijven.add(mapper.map(bedrijf, JsonBedrijf.class));
            }

        }
        return bedrijven;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("verwijderen Bedrijf met id " + id);

        zetSessieWaarden(httpServletRequest);

        try {
            bedrijfService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{identificatieCode}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public nl.lakedigital.djfc.domain.response.Bedrijf lees(@PathVariable("identificatieCode") String identificatieCode, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);
        LOGGER.debug("Zoeken met identificatiecode {}", identificatieCode);

        nl.lakedigital.djfc.domain.response.Bedrijf bedrijf = null;

        try {

            nl.dias.domein.Bedrijf bedrijfDomain = bedrijfService.zoekBedrijf(identificatieCode);
            Identificatie identificatie = identificatieClient.zoekIdentificatie("BEDRIJF", bedrijfDomain.getId());

            bedrijf = new BedrijfToDtoBedrijfMapper().apply(bedrijfDomain);
            bedrijf.setIdentificatie(identificatie.getIdentificatie());

            bedrijf.setAdressen(adresClient.lijst("BEDRIJF", bedrijfDomain.getId()).stream().map(new JsonToDtoAdresMapper(identificatieClient)).collect(Collectors.toList()));
            bedrijf.setBijlages(bijlageClient.lijst("BEDRIJF", bedrijfDomain.getId()).stream().map(new JsonToDtoBijlageMapper(identificatieClient)).collect(Collectors.toList()));
            bedrijf.setGroepBijlages(groepBijlagesClient.lijstGroepen("BEDRIJF", bedrijfDomain.getId()).stream().map(new JsonToDtoGroepBijlageMapper(identificatieClient)).collect(Collectors.toList()));
            bedrijf.setTelefoonnummers(telefoonnummerClient.lijst("BEDRIJF", bedrijfDomain.getId()).stream().map(new JsonToDtoTelefoonnummerMapper(identificatieClient)).collect(Collectors.toList()));
            bedrijf.setOpmerkingen(opmerkingClient.lijst("BEDRIJF", bedrijfDomain.getId()).stream().map(new JsonToDtoOpmerkingMapper(identificatieClient, gebruikerService)).collect(Collectors.toList()));

            List<nl.dias.domein.polis.Polis> polissen = polisService.allePolissenBijBedrijf(bedrijfDomain.getId());
            bedrijf.setPolissen(polissen.stream().map(new JsonToDtoPolisMapper(bijlageClient, groepBijlagesClient, opmerkingClient, identificatieClient, gebruikerService)).collect(Collectors.toList()));
            //        bedrijf.setPolissen(polisClient.lijstBijBedrijf(bedrijfDomain.getId()).stream().map(new JsonToDtoPolisMapper(bijlageClient, groepBijlagesClient, opmerkingClient, identificatieClient, gebruikerService)).collect(Collectors.toList()));

            bedrijf.setContactPersoons(gebruikerService.alleContactPersonen(bedrijfDomain.getId()).stream().map(new DomainToDtoContactPersoonMapper(identificatieClient, telefoonnummerClient)).collect(Collectors.toList()));

            List<String> telefoonnummers = bedrijf.getTelefoonnummers().stream().map(telefoonnummer -> telefoonnummer.getTelefoonnummer()).collect(Collectors.toList());
            telefoonnummers.addAll(bedrijf.getContactPersoons().stream().map(contactPersoon -> contactPersoon.getTelefoonnummers())//
                    .flatMap(List::stream).map(telefoonnummer -> telefoonnummer.getTelefoonnummer()).collect(Collectors.toList()));

            Map<String, List<JsonTelefonieBestand>> telefonieResult = telefonieBestandClient.getRecordingsAndVoicemails(telefoonnummers);
            for (String nummer : telefonieResult.keySet()) {
                TelefoonnummerMetGesprekken telefoonnummerMetGesprekken = new TelefoonnummerMetGesprekken();
                telefoonnummerMetGesprekken.setTelefoonnummer(nummer);
                telefoonnummerMetGesprekken.setTelefoongesprekken(telefonieResult.get(nummer).stream().map(s -> {
                    Telefoongesprek telefoongesprek = new Telefoongesprek();
                    telefoongesprek.setBestandsnaam(s.getBestandsnaam());
                    telefoongesprek.setTijdstip(s.getTijdstip());
                    telefoongesprek.setTelefoonnummer(s.getTelefoonnummer());

                    return telefoongesprek;
                }).collect(Collectors.toList()));


                bedrijf.getTelefoonnummerMetGesprekkens().add(telefoonnummerMetGesprekken);
            }
        } catch (Exception e) {
            LOGGER.error("Fout bij lezen Bedrijf {} - {}", e.getMessage(), e.getStackTrace());
            throw e;
        }

        return bedrijf;
    }

}
