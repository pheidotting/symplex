package nl.dias.web.medewerker;

import nl.dias.domein.*;
import nl.dias.mapper.JsonMedewerkerNaarMedewerkerMapper;
import nl.dias.mapper.Mapper;
import nl.dias.mapper.MedewerkerNaarJsonMedewerkerMapper;
import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.JsonRelatieMapper;
import nl.dias.web.medewerker.mappers.DomainAdresNaarMessagingAdresMapper;
import nl.dias.web.medewerker.mappers.DomainOpmerkingNaarMessagingOpmerkingMapper;
import nl.dias.web.medewerker.mappers.DomainRekeningNummerNaarMessagingRekeningNummerMapper;
import nl.dias.web.medewerker.mappers.DomainTelefoonnummerNaarMessagingTelefoonnummerMapper;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonContactPersoon;
import nl.lakedigital.djfc.commons.json.JsonKoppelenOnderlingeRelatie;
import nl.lakedigital.djfc.commons.json.JsonMedewerker;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.joda.time.LocalDateTime;
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
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;

    @RequestMapping(method = RequestMethod.GET, value = "/alleContactPersonen", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonContactPersoon> alleContactPersonen(@QueryParam("bedrijfsId") Long bedrijfsId) {
        List<JsonContactPersoon> result = new ArrayList<>();

        for (ContactPersoon contactPersoon : gebruikerService.alleContactPersonen(bedrijfsId)) {
            result.add(mapper.map(contactPersoon, JsonContactPersoon.class));
        }

        return result;
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

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();
        opslaanEntiteitenRequest.getLijst().addAll(jsonRelatie.getAdressen().stream().map(new DomainAdresNaarMessagingAdresMapper(relatie.getId(), SoortEntiteit.RELATIE)).collect(Collectors.toList()));
        opslaanEntiteitenRequest.getLijst().addAll(jsonRelatie.getTelefoonnummers().stream().map(new DomainTelefoonnummerNaarMessagingTelefoonnummerMapper(relatie.getId(), SoortEntiteit.RELATIE)).collect(Collectors.toList()));
        opslaanEntiteitenRequest.getLijst().addAll(jsonRelatie.getOpmerkingen().stream().map(new DomainOpmerkingNaarMessagingOpmerkingMapper(relatie.getId(), SoortEntiteit.RELATIE)).collect(Collectors.toList()));
        opslaanEntiteitenRequest.getLijst().addAll(jsonRelatie.getRekeningNummers().stream().map(new DomainRekeningNummerNaarMessagingRekeningNummerMapper(relatie.getId(), SoortEntiteit.RELATIE)).collect(Collectors.toList()));

        opslaanEntiteitenRequest.setEntiteitId(relatie.getId());
        opslaanEntiteitenRequest.setSoortEntiteit(SoortEntiteit.RELATIE);

        opslaanEntiteitenRequestSender.send(opslaanEntiteitenRequest);

        return relatie.getIdentificatie();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("id") String identificatieString, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Verwijderen Relatie met id {}", identificatieString);

        zetSessieWaarden(httpServletRequest);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieString);

        gebruikerService.verwijder(identificatie.getEntiteitId());
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

    @RequestMapping(method = RequestMethod.POST, value = "/wijzigWachtwoord", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void wijzigWachtwoord(@RequestBody String nieuwWactwoord, HttpServletRequest httpServletRequest) {
        LOGGER.info("Wachtwoord wijzigen");

        zetSessieWaarden(httpServletRequest);

        Gebruiker gebruiker = getIngelogdeGebruiker(httpServletRequest);
        try {
            gebruiker.setHashWachtwoord(nieuwWactwoord);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("Fout opgetreden bij het wijzigen van het wachtwoord van {} : {}", gebruiker.getIdentificatie(), e);
            throw new RuntimeException("Onbekende fout opgetreden");
        }

        gebruiker.setWachtwoordLaatstGewijzigd(new LocalDateTime());
        gebruiker.setMoetWachtwoordUpdaten(false);

        gebruikerService.opslaan(gebruiker);
    }
}
