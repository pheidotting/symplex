package nl.dias.web.medewerker;

import nl.dias.domein.*;
import nl.dias.mapper.JsonMedewerkerNaarMedewerkerMapper;
import nl.dias.mapper.Mapper;
import nl.dias.mapper.MedewerkerNaarJsonMedewerkerMapper;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.dias.web.mapper.RelatieMapper;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.*;
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
    private Mapper mapper;
    @Inject
    private AuthorisatieService authorisatieService;
    @Inject
    private MedewerkerNaarJsonMedewerkerMapper medewerkerNaarJsonMedewerkerMapper;
    @Inject
    private JsonMedewerkerNaarMedewerkerMapper jsonMedewerkerNaarMedewerkerMapper;
    @Inject
    private IdentificatieClient identificatieClient;

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

        JsonRelatie jsonRelatie = null;
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

    @RequestMapping(method = RequestMethod.GET, value = "/lijstRelaties", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonLijstRelaties lijstRelaties(@QueryParam("weglaten") String weglaten) {
        LOGGER.debug("Ophalen lijst met alle Relaties");

        Long idWeglaten = null;
        if (weglaten != null) {
            LOGGER.debug("id " + weglaten + " moet worden weggelaten");
            idWeglaten = Long.parseLong(weglaten);
        }

        JsonLijstRelaties lijst = new JsonLijstRelaties();

        for (Gebruiker r : gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor())) {
            if (idWeglaten == null || !idWeglaten.equals(r.getId())) {
                lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie) r));
            }
        }
        LOGGER.debug("Opgehaald, lijst met " + lijst.getJsonRelaties().size() + " relaties");

        return lijst;
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
    public String opslaan(@RequestBody JsonRelatie jsonRelatie, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LOGGER.info("Opslaan " + jsonRelatie);

        zetSessieWaarden(httpServletRequest);

        if (jsonRelatie.getIdentificatie() != null) {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonRelatie.getIdentificatie());
            if (identificatie != null) {
                jsonRelatie.setId(identificatie.getEntiteitId());
            }
        }

        String sessie = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }

        Medewerker medewerker = (Medewerker) getIngelogdeGebruiker(httpServletRequest);

        Kantoor kantoor = kantoorRepository.lees(medewerker.getKantoor().getId());

        Relatie relatie = relatieMapper.mapVanJson(jsonRelatie);
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
            }else{
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

        return relatie.getIdentificatie();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijderen/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijderen(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Verwijderen Relatie met id " + id);

        zetSessieWaarden(httpServletRequest);

        gebruikerService.verwijder(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/zoekOpNaamAdresOfPolisNummer", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonLijstRelaties zoekOpNaamAdresOfPolisNummer(@QueryParam("zoekTerm") String zoekTerm, @QueryParam("weglaten") String weglaten) {
        LOGGER.info("zoekOpNaamAdresOfPolisNummer met zoekterm " + zoekTerm);

        JsonLijstRelaties lijst = new JsonLijstRelaties();

        if (zoekTerm == null || "".equals(zoekTerm)) {
            for (Gebruiker r : gebruikerService.alleRelaties(kantoorRepository.getIngelogdKantoor())) {
                lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie) r));
            }
        } else {
            Long idWeglaten = null;
            if (weglaten != null) {
                LOGGER.debug("id " + weglaten + " moet worden weggelaten");
                idWeglaten = Long.parseLong(weglaten);
            }

            for (Gebruiker r : gebruikerService.zoekOpNaamAdresOfPolisNummer(zoekTerm)) {
                if (idWeglaten == null || !idWeglaten.equals(r.getId())) {
                    lijst.getJsonRelaties().add(relatieMapper.mapNaarJson((Relatie) r));
                }
            }
        }
        return lijst;
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
