package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.messaging.sender.WijzigingTaakOpslaanRequestSender;
import nl.dias.web.mapper.JsonToDtoTaakMapper;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.OpmerkingClient;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.domain.response.Taak;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.commons.json.WijzigingTaakOpslaan;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping("/taak")
@Controller
public class TaakController extends AbstractController {
    @Inject
    private TaakClient taakClient;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private OpmerkingClient opmerkingClient;
    @Inject
    private WijzigingTaakOpslaanRequestSender wijzigingTaakOpslaanRequestSender;

    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{identificatieCode}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Taak lees(@PathVariable("identificatieCode") String identificatieCode, HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" lees", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);
        Taak taak = new JsonToDtoTaakMapper(identificatieClient).apply(taakClient.lees(identificatie.getEntiteitId()));

        //Opmerkingen er bij zoeken
        taak.getWijzigingTaaks().stream().forEach(wijzigingTaak -> {
            Identificatie identificatie1 = identificatieClient.zoekIdentificatieCode(wijzigingTaak.getIdentificatie());

            List<JsonOpmerking> opmerkings = opmerkingClient.lijst("WIJZIGINGTAAK", identificatie1.getEntiteitId());
            if (opmerkings.size() > 0) {
                wijzigingTaak.setJsonOpmerking(opmerkings.get(0));
            }
        });

        metricsService.stop(timer);

        return taak;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaanWijziging", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaanWijziging(@RequestBody WijzigingTaakOpslaan wijzigingTaakOpslaan, HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" lees", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        wijzigingTaakOpslaanRequestSender.send(wijzigingTaakOpslaan);

        metricsService.stop(timer);
    }
}
