package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.messaging.sender.OpslaanTaakRequestSender;
import nl.dias.web.mapper.JsonToDtoTaakMapper;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.response.Taak;
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
import java.util.stream.Collectors;

@RequestMapping("/taak")
@Controller
public class TaakController extends AbstractController {
    @Inject
    private TaakClient taakClient;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private OpslaanTaakRequestSender nieuweTaakRequestSender;

    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees/{identificatieCode}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Taak lees(@PathVariable("identificatieCode") String identificatieCode, HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" lees", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);
        Taak taak = new JsonToDtoTaakMapper(identificatieClient).apply(taakClient.lees(identificatie.getEntiteitId()));

        metricsService.stop(timer);

        return taak;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/alles/{identificatieCode}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public List<Taak> alles(@PathVariable("identificatieCode") String identificatieCode, HttpServletRequest httpServletRequest) {
        Timer.Context timer = metricsService.addTimerMetric(" alles", TaakController.class);

        zetSessieWaarden(httpServletRequest);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);
        List<Taak> taken = taakClient.alles(identificatie.getSoortEntiteit(), identificatie.getEntiteitId()).stream().map(new JsonToDtoTaakMapper(identificatieClient)).collect(Collectors.toList());

        metricsService.stop(timer);

        return taken;
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

        List<Taak> taken = taakClient.allesopenstaand().stream().map(new JsonToDtoTaakMapper(identificatieClient)).collect(Collectors.toList());

        metricsService.stop(timer);

        return taken;
    }
}
