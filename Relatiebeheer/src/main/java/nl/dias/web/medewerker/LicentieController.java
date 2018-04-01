package nl.dias.web.medewerker;

import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.messaging.sender.LicentieGekochtRequestSender;
import nl.lakedigital.djfc.client.licentie.LicentieClient;
import nl.lakedigital.djfc.commons.json.Licentie;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/licentie")
@Controller
public class LicentieController extends AbstractController {
    @Inject
    private LicentieClient licentieClient;
    @Inject
    private MetricsService metricsService;
    @Inject
    private LicentieGekochtRequestSender licentieGekochtRequestSender;

    @RequestMapping(method = RequestMethod.GET, value = "/einddatum", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, String> einddatum(HttpServletRequest httpServletRequest) {
        metricsService.addMetric("actievelicentie", LicentieController.class, null, null);

        Kantoor kantoor = ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor();

        Map<String, String> result = new HashMap<>();
        Licentie licentie = licentieClient.eindDatumLicentie(kantoor.getId());
        result.put("einddatum", licentie.getEinddatum());
        result.put("soort", licentie.getSoort());

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/licentie-kopen")
    @ResponseBody
    public boolean licentieKopen(@RequestBody String soortLicentie, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("licentieKopen", LicentieController.class, null, null);

        Kantoor kantoor = ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor();

        licentieGekochtRequestSender.send(kantoor, soortLicentie);

        Map<String, String> result = new HashMap<>();
        result.put("einddatum", licentieClient.eindDatumLicentie(kantoor.getId()).getEinddatum());

        return true;
    }
}
