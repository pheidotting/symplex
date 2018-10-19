package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.dias.messaging.sender.OpslaanSchadeOpdrachtSender;
import nl.dias.service.BedrijfService;
import nl.dias.service.GebruikerService;
import nl.dias.service.TakenOpslaanService;
import nl.dias.web.medewerker.mappers.JsonTaakNaarOpdrachtMapper;
import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanSchadeOpdracht;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.opdrachtenadministratie.OpdrachtenClient;
import nl.lakedigital.djfc.client.polisadministratie.SchadeClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonSoortSchade;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/schade")
@Controller
public class SchadeController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeController.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private BedrijfService bedrijfService;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;
    @Inject
    private MetricsService metricsService;
    @Inject
    private TakenOpslaanService takenOpslaanService;
    @Inject
    private OpslaanSchadeOpdrachtSender opslaanSchadeOpdrachtSender;
    @Inject
    private OpdrachtenClient opdrachtenClient;
    @Inject
    private SchadeClient schadeClient;

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan")
    @ResponseBody
    public void opslaan(@RequestBody nl.lakedigital.djfc.commons.domain.response.Schade jsonSchade, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("schadeOpslaan", SchadeController.class, null, jsonSchade.getIdentificatie() == null);
        Timer.Context timer = metricsService.addTimerMetric("opslaan", SchadeController.class);

        LOGGER.debug("Opslaan {}", jsonSchade);

        zetSessieWaarden(httpServletRequest);

        Identificatie identificatieSchade = identificatieClient.zoekIdentificatieCode(jsonSchade.getIdentificatie());

        OpslaanSchadeOpdracht opslaanSchadeOpdracht = opslaanSchadeOpdrachtSender.maakMessage(jsonSchade);
        if (identificatieSchade != null) {
            opslaanSchadeOpdracht.getSchade().setId(identificatieSchade.getEntiteitId());
        }
        opslaanSchadeOpdracht.getSchade().setPolis(jsonSchade.getPolis());
        opslaanSchadeOpdracht.setTaken(jsonSchade.getTaken().stream().map(new JsonTaakNaarOpdrachtMapper(identificatieClient, null, SoortEntiteit.SCHADE)).collect(Collectors.toList()));
        opslaanSchadeOpdrachtSender.send(opslaanSchadeOpdracht);

        opdrachtenClient.wachtTotOpdrachtKlaarIs(getTrackAndTraceId(httpServletRequest));

        metricsService.stop(timer);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void verwijder(@PathVariable("id") String id, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("schadeVerwijderen", SchadeController.class, null, null);

        LOGGER.debug("verwijderen Schade met id " + id);

        zetSessieWaarden(httpServletRequest);

        //        try {
        //            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(id);
        //            schadeService.verwijder(identificatie.getEntiteitId());
        //        } catch (IllegalArgumentException e) {
        //            LOGGER.error("Fout bij verwijderen Schade", e);
        //        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/soortenSchade/{query}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<JsonSoortSchade> soortenSchade(@PathVariable("query") String query) {
        return schadeClient.soortenSchade(query);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/lijstStatusSchade", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<String> lijstStatusSchade() {
        return schadeClient.lijstStatusSchade();
    }

}
