package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.domein.Medewerker;
import nl.dias.mapper.Mapper;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.MetricsService;
import nl.lakedigital.djfc.commons.json.JsonKantoor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

@RequestMapping("/kantoor")
@Controller("kantoorMedewerkerController")
public class KantoorController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorController.class);

    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private Mapper mapper;
    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonKantoor lees(HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        metricsService.addMetric("lees", KantoorController.class, null, null);
        Timer.Context timer = metricsService.addTimerMetric("lees", KantoorController.class);

        Long kantoorId = ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor().getId();

        LOGGER.debug("Ophalen Kantoor met id : " + kantoorId);

        JsonKantoor jsonKantoor = mapper.map(kantoorRepository.lees(kantoorId), JsonKantoor.class);

        metricsService.stop(timer);

        return jsonKantoor;
    }
}
