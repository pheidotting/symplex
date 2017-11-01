package nl.dias.web.medewerker;

import nl.dias.domein.polis.Polis;
import nl.dias.mapper.Mapper;
import nl.dias.messaging.sender.BeindigenPolisRequestSender;
import nl.dias.messaging.sender.PolisOpslaanRequestSender;
import nl.dias.messaging.sender.PolisVerwijderenRequestSender;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.web.mapper.PolisMapper;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@RequestMapping("/polis")
@Controller
public class PolisController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisController.class);

    @Inject
    private PolisService polisService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private PolisMapper polisMapper;
    @Inject
    private List<Polis> polissen;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private Mapper mapper;
    @Inject
    private PolisVerwijderenRequestSender polisVerwijderenRequestSender;
    @Inject
    private PolisOpslaanRequestSender polisOpslaanRequestSender;
    @Inject
    private Destination polisOpslaanResponseDestination;
    @Inject
    private PolisClient polisClient;
    //    @Inject
    private BeindigenPolisRequestSender beindigenPolisRequestSender;
    @Inject
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.GET, value = "/alleParticulierePolisSoorten", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<String> alleParticulierePolisSoorten() {
        return polisClient.alleParticulierePolisSoorten();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleZakelijkePolisSoorten", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<String> alleZakelijkePolisSoorten() {
        return polisClient.alleZakelijkePolisSoorten();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonPolis lees(@QueryParam("id") String id) {
        LOGGER.debug("ophalen Polis met id " + id);

        return polisClient.lees(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/beeindigen/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void beeindigen(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("beeindigen Polis met id " + id);

        zetSessieWaarden(httpServletRequest);

        beindigenPolisRequestSender.send(newArrayList(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonPolis> lijst(@QueryParam("relatieId") String relatieId) {
        LOGGER.debug("Ophalen alle polissen voor Relatie " + relatieId);

        return polisClient.lijst(relatieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonPolis> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Ophalen alle polissen voor Bedrijf " + bedrijfId);

        return polisClient.lijstBijBedrijf(bedrijfId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody JsonPolis polis, HttpServletRequest httpServletRequest) {
        LOGGER.debug("Opslaan " + ReflectionToStringBuilder.toString(polis));

        zetSessieWaarden(httpServletRequest);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polis.getParentIdentificatie());
        polis.setEntiteitId(identificatie.getEntiteitId());
        polis.setSoortEntiteit(identificatie.getSoortEntiteit());

        polisOpslaanRequestSender.setReplyTo(polisOpslaanResponseDestination);
        polisOpslaanRequestSender.send(polis);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        LOGGER.debug("verwijderen Polis met id " + id);

        zetSessieWaarden(httpServletRequest);

        polisVerwijderenRequestSender.send(newArrayList(id));
    }
}