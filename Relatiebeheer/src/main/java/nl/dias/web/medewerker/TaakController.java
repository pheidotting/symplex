package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.messaging.sender.OpslaanTaakRequestSender;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.json.Taak;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private OpslaanTaakRequestSender nieuweTaakRequestSender;

    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{id}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Taak lees(@PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" lees", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        Taak taak = taakClient.lees(id);

        metricsService.stop(timer);

        return taak;
    }

    //    @RequestMapping(method = RequestMethod.GET, value = "/alles/{soortentiteit}/{parentid}", produces = MediaType.APPLICATION_JSON)
    //    @ResponseBody
    //    public List<Taak> alles(@PathVariable("soortentiteit") String soortentiteit, @PathVariable("parentid") Long parentid, HttpServletRequest httpServletRequest) {
    //        Timer.Context timer = metricsService.addTimerMetric(" alles", TaakController.class);
    //
    //        zetSessieWaarden(httpServletRequest);
    //
    //        List<Taak> taken = taakClient.alles(soortentiteit, parentid);
    //
    //        metricsService.stop(timer);
    //
    //        return taken;
    //    }

    @RequestMapping(method = RequestMethod.GET, value = "/allesopenstaand", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<Taak> allesopenstaand(HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" allesopenstaand", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        List<Taak> taken = taakClient.allesopenstaand();

        metricsService.stop(timer);

        return taken;
    }
}
