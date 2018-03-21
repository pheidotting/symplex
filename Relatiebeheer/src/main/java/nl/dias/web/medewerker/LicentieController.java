package nl.dias.web.medewerker;

import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.lakedigital.djfc.client.licentie.LicentieClient;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

@RequestMapping("/licentie")
@Controller
public class LicentieController extends AbstractController {
    @Inject
    private LicentieClient licentieClient;
    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.GET, value = "/actievelicentie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public String actievelicentie(HttpServletRequest httpServletRequest) {
        metricsService.addMetric("actievelicentie", LicentieController.class, null, null);

        Kantoor kantoor = ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor();

        return licentieClient.eindDatumLicentie(kantoor.getId()).getEinddatum();
    }
}
