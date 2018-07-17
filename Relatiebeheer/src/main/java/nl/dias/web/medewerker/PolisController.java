package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import com.google.common.collect.Lists;
import nl.dias.domein.polis.Polis;
import nl.dias.mapper.Mapper;
import nl.dias.messaging.sender.BeindigenPolisRequestSender;
import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.dias.messaging.sender.PolisOpslaanRequestSender;
import nl.dias.messaging.sender.PolisVerwijderenRequestSender;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.PolisService;
import nl.dias.service.TakenOpslaanService;
import nl.dias.web.mapper.PolisMapper;
import nl.dias.web.medewerker.mappers.DomainOpmerkingNaarMessagingOpmerkingMapper;
import nl.dias.web.medewerker.mappers.JsonPolisNaarDomainPolisMapper;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.metrics.MetricsService;
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
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private PolisOpslaanRequestSender polisOpslaanRequestSender;
    @Inject
    private Destination polisOpslaanResponseDestination;
    @Inject
    private PolisClient polisClient;
    //    @Inject
    private BeindigenPolisRequestSender beindigenPolisRequestSender;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;
    @Inject
    private MetricsService metricsService;
    @Inject
    private TakenOpslaanService takenOpslaanService;

    @RequestMapping(method = RequestMethod.GET, value = "/alleParticulierePolisSoorten", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<String> alleParticulierePolisSoorten() {
        return polisClient.alleParticulierePolisSoorten();

        //        return polisService.allePolisSoorten(SoortVerzekering.PARTICULIER);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alleZakelijkePolisSoorten", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<String> alleZakelijkePolisSoorten() {
        return polisClient.alleZakelijkePolisSoorten();
        //        return polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonPolis lees(@QueryParam("id") String id) {
        LOGGER.debug("ophalen Polis met id " + id);

        //                return polisClient.lees(id);
        if (id != null && !"".equals(id) && !"0".equals(id)) {
            LOGGER.debug("ophalen Polis");
            return polisMapper.mapNaarJson(polisService.lees(Long.valueOf(id)));
        } else {
            LOGGER.debug("Nieuwe Polis tonen");
            return new JsonPolis();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/beeindigen/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void beeindigen(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("polisOpslaan", PolisController.class, null, false);

        LOGGER.debug("beeindigen Polis met id " + id);

        zetSessieWaarden(httpServletRequest);

        polisService.beeindigen(id);
        //
        //        beindigenPolisRequestSender.send(newArrayList(id));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijst", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonPolis> lijst(@QueryParam("relatieId") String relatieId) {
        LOGGER.debug("Ophalen alle polissen voor Relatie " + relatieId);
        //                        Relatie relatie = (Relatie) gebruikerService.lees(Long.valueOf(relatieId));
        Long relatie = Long.valueOf(relatieId);

        List<JsonPolis> result = Lists.newArrayList();
        for (Polis polis : polisService.allePolissenBijRelatie(relatie)) {
            result.add(polisMapper.mapNaarJson(polis));
        }

        return result;
        //
        //        return polisClient.lijst(relatieId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstBijBedrijf", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonPolis> lijstBijBedrijf(@QueryParam("bedrijfId") Long bedrijfId) {
        LOGGER.debug("Ophalen alle polissen voor Bedrijf " + bedrijfId);

        Set<Polis> polissen = new HashSet<>();
        for (Polis polis : polisService.allePolissenBijBedrijf(bedrijfId)) {
            polissen.add(polis);
        }
        //
        return polisMapper.mapAllNaarJson(polisService.allePolissenBijBedrijf(bedrijfId));

        //        return polisClient.lijstBijBedrijf(bedrijfId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Long opslaan(@RequestBody nl.lakedigital.djfc.domain.response.Polis jsonPolis, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("polisOpslaan", PolisController.class, null, jsonPolis.getIdentificatie() == null);
        Timer.Context timer = metricsService.addTimerMetric("opslaan", PolisController.class);

        LOGGER.debug("Opslaan " + ReflectionToStringBuilder.toString(jsonPolis));

        zetSessieWaarden(httpServletRequest);

        //        nl.lakedigital.djfc.domain.response.Polis polis = jsonPolis;
        //                Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polis.getParentIdentificatie());
        //                polis.setEntiteitId(identificatie.getEntiteitId());
        //                polis.setSoortEntiteit(identificatie.getSoortEntiteit());
        //
        //                polisOpslaanRequestSender.setReplyTo(polisOpslaanResponseDestination);
        //                polisOpslaanRequestSender.send(polis);

        //                Polis polis = polisMapper.mapVanJson(jsonPolis);
        //        Polis polis = new JsonPolisNaarDomainPolisMapper(polisService, identificatieClient).map(jsonPolis);
        //        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonPolis.getIdentificatie());
        JsonPolis polis = new JsonPolisNaarDomainPolisMapper(polisClient, identificatieClient).map(jsonPolis);//         polisClient.lees(String.valueOf(identificatie.getEntiteitId()));
        //        polis.set
        //        polis.setMaatschappij(jsonPolis.getMaatschappij());
        LOGGER.debug("ID van de Polis {}", polis.getId());
        //        try {
        //            polisService.opslaan(polis);
        //        } catch (IllegalArgumentException e) {
        //            LOGGER.debug("Fout opgetreden bij opslaan Polis", e);
        //            throw new IllegalStateException(e.getLocalizedMessage());
        //        }

        polisOpslaanRequestSender.setReplyTo(polisOpslaanResponseDestination);
        polisOpslaanRequestSender.send(polis);

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();
        opslaanEntiteitenRequest.getLijst().addAll(jsonPolis.getOpmerkingen().stream().map(new DomainOpmerkingNaarMessagingOpmerkingMapper(polis.getId(), SoortEntiteit.POLIS)).collect(Collectors.toList()));

        opslaanEntiteitenRequest.setEntiteitId(polis.getId());
        opslaanEntiteitenRequest.setSoortEntiteit(SoortEntiteit.POLIS);

        opslaanEntiteitenRequestSender.send(opslaanEntiteitenRequest);

        takenOpslaanService.opslaan(jsonPolis.getTaken(), polis.getId());

        metricsService.stop(timer);

        return polis.getId();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Response verwijder(@PathVariable("id") String id, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("polisVerwijderen", PolisController.class, null, null);

        LOGGER.debug("verwijderen Polis met id " + id);

        //        zetSessieWaarden(httpServletRequest);
        //
        //        try {
        //            polisService.verwijder(id);
        //        } catch (IllegalArgumentException e) {
        //            LOGGER.error("Fout bij verwijderen Polis", e);
        //            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        //        }
        //        return Response.status(202).entity(new JsonFoutmelding()).build();

        try {
            polisService.verwijder(id);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fout bij verwijderen Polis", e);
            return Response.status(500).entity(new JsonFoutmelding(e.getMessage())).build();
        }
        return Response.status(202).entity(new JsonFoutmelding("")).build();
        //        polisVerwijderenRequestSender.send(newArrayList(id));
    }
}