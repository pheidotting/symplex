package nl.dias.web.medewerker;

import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.dias.messaging.sender.VerwijderEntiteitRequestSender;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestMapping("/opmerking")
@Controller
public class OpmerkingController extends AbstractController {

    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;
    @Inject
    private VerwijderEntiteitRequestSender verwijderEntiteitRequestSender;

    @RequestMapping(method = RequestMethod.POST, value = "/verwijder/{identificatieCode}")
    @ResponseBody
    public void verwijder(@PathVariable("identificatieCode") String identificatieCode, HttpServletRequest httpServletRequest) {
        metricsService.addMetric("verwijder", OpmerkingController.class, null, null);

        zetSessieWaarden(httpServletRequest);

        Long id = identificatieClient.zoekIdentificatieCode(identificatieCode).getEntiteitId();

        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId(SoortEntiteit.OPMERKING, id);

        verwijderEntiteitRequestSender.send(soortEntiteitEnEntiteitId);
    }
}
