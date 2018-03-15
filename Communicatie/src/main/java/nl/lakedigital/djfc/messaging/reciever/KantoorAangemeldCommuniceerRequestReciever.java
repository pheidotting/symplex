package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.communicatie.KantoorAangemeldCommuniceerRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.CommunicatieProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Component
public class KantoorAangemeldCommuniceerRequestReciever extends AbstractReciever<KantoorAangemeldCommuniceerRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldCommuniceerRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private CommunicatieProductService communicatieProductService;

    public KantoorAangemeldCommuniceerRequestReciever() {
        super(KantoorAangemeldCommuniceerRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(KantoorAangemeldCommuniceerRequest kantoorAangemeldCommuniceerRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(kantoorAangemeldCommuniceerRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", KantoorAangemeldCommuniceerRequestReciever.class);

        Map<String, String> var = new HashMap<>();
        var.put("wachtwoord", kantoorAangemeldCommuniceerRequest.getWachtwoord());

        communicatieProductService.versturen(kantoorAangemeldCommuniceerRequest.getGeadresseerden(), null, var, CommunicatieProductService.TemplateNaam.KANTOOR_AANGEMELD);

        metricsService.stop(timer);
    }
}
