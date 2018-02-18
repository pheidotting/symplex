package nl.dias.web.medewerker;

import com.codahale.metrics.Timer;
import nl.dias.domein.Medewerker;
import nl.dias.mapper.Mapper;
import nl.dias.repository.KantoorRepository;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonKantoor;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Inject
    private IdentificatieClient identificatieClient;

    @RequestMapping(method = RequestMethod.GET, value = "/lees", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public JsonKantoor lees(HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        metricsService.addMetric("lees", KantoorController.class, null, null);
        Timer.Context timer = metricsService.addTimerMetric("lees", KantoorController.class);

        Long kantoorId = ((Medewerker) getIngelogdeGebruiker(httpServletRequest)).getKantoor().getId();

        Identificatie identificatie = identificatieClient.zoekIdentificatie("KANTOOR", kantoorId);

        LOGGER.debug("Ophalen Kantoor met id : " + kantoorId);

        JsonKantoor jsonKantoor = mapper.map(kantoorRepository.lees(kantoorId), JsonKantoor.class);
        jsonKantoor.setIdentificatie(identificatie.getIdentificatie());
        jsonKantoor.setId(null);

        metricsService.stop(timer);

        return jsonKantoor;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opslaan", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void opslaan(@RequestBody JsonKantoor jsonKantoor, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        metricsService.addMetric("opslaan", KantoorController.class, null, jsonKantoor.getId() == null);
        Timer.Context timer = metricsService.addTimerMetric("opslaan", KantoorController.class);

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonKantoor.getIdentificatie());
        Long kantoorId = identificatie.getEntiteitId();
        jsonKantoor.setId(kantoorId);

        metricsService.stop(timer);
    }
}
