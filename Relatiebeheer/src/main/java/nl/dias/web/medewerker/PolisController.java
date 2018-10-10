package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.domein.polis.Polis;
import nl.dias.mapper.Mapper;
import nl.dias.messaging.sender.*;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.TakenOpslaanService;
import nl.dias.web.mapper.PolisMapper;
import nl.dias.web.medewerker.mappers.DomainOpmerkingNaarMessagingOpmerkingMapper;
import nl.dias.web.medewerker.mappers.JsonPakketNaarDomainPakketMapper;
import nl.dias.web.medewerker.mappers.JsonTaakNaarOpdrachtMapper;
import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanPolisOpdracht;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.opdrachtenadministratie.OpdrachtenClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.response.Pakket;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
import nl.lakedigital.djfc.commons.json.JsonPakket;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private OpslaanPolisOpdrachtSender opslaanPolisOpdrachtSender;
    @Inject
    private VerwijderPolisOpdrachtSender verwijderPolisOpdrachtSender;
    @Inject
    private Destination polisOpslaanResponseDestination;
    @Inject
    private PolisClient polisClient;
    //    @Inject
    private BeindigenPolisRequestSender beindigenPolisRequestSender;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private OpdrachtenClient opdrachtenClient;
    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;
    @Inject
    private MetricsService metricsService;
    @Inject
    private TakenOpslaanService takenOpslaanService;

    @RequestMapping(method = RequestMethod.GET, value = "/alleParticulierePolisSoorten", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, String> alleParticulierePolisSoorten() {
        return polisClient.alleParticulierePolisSoorten();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleZakelijkePolisSoorten", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, String> alleZakelijkePolisSoorten() {
        return polisClient.alleZakelijkePolisSoorten();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Long opslaan(@RequestBody Pakket jsonPakket, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("polisOpslaan", PolisController.class, null, jsonPakket.getIdentificatie() == null);
        Timer.Context timer = metricsService.addTimerMetric("opslaan", PolisController.class);

        LOGGER.debug("Opslaan " + ReflectionToStringBuilder.toString(jsonPakket));

        zetSessieWaarden(httpServletRequest);

        JsonPakket pakket = new JsonPakketNaarDomainPakketMapper(polisClient, identificatieClient).map(jsonPakket);

        Identificatie identificatieParent = identificatieClient.zoekIdentificatieCode(jsonPakket.getParentIdentificatie());
        pakket.setEntiteitId(identificatieParent.getEntiteitId());
        pakket.setSoortEntiteit(identificatieParent.getSoortEntiteit());

        LOGGER.debug("ID van de Polis {}", pakket.getId());

        OpslaanPolisOpdracht opslaanPolisOpdracht = opslaanPolisOpdrachtSender.maakMessage(pakket);
        opslaanPolisOpdracht.setOpmerkingen(jsonPakket.getOpmerkingen().stream().map(new DomainOpmerkingNaarMessagingOpmerkingMapper(pakket.getId(), SoortEntiteit.POLIS)).collect(Collectors.toList()));
        opslaanPolisOpdracht.setTaken(jsonPakket.getTaken().stream().map(new JsonTaakNaarOpdrachtMapper(identificatieClient, pakket.getId(), SoortEntiteit.POLIS)).collect(Collectors.toList()));
        opslaanPolisOpdrachtSender.send(opslaanPolisOpdracht);

        opdrachtenClient.wachtTotOpdrachtKlaarIs(getTrackAndTraceId(httpServletRequest));

        metricsService.stop(timer);

        return pakket.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Response verwijder(@PathVariable("id") String id, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("polisVerwijderen", PolisController.class, null, null);

        LOGGER.debug("verwijderen Polis met id " + id);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(id);

        verwijderPolisOpdrachtSender.send(identificatie.getEntiteitId());

        opdrachtenClient.wachtTotOpdrachtKlaarIs(getTrackAndTraceId(httpServletRequest));

        return Response.status(202).entity(new JsonFoutmelding("")).build();
    }
}