package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.communicatie.LicentieGekochtCommuniceerRequest;
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
public class LicentieGekochtCommuniceerRequestReciever extends AbstractReciever<LicentieGekochtCommuniceerRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieGekochtCommuniceerRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private CommunicatieProductService communicatieProductService;

    public LicentieGekochtCommuniceerRequestReciever() {
        super(LicentieGekochtCommuniceerRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(LicentieGekochtCommuniceerRequest licentieGekochtCommuniceerRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(licentieGekochtCommuniceerRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", LicentieGekochtCommuniceerRequestReciever.class);

        Map<String, String> var = new HashMap<>();
        var.put("licentie", licentieGekochtCommuniceerRequest.getLicentieType());
        var.put("prijs", licentieGekochtCommuniceerRequest.getPrijs().toString());

        communicatieProductService.versturen(licentieGekochtCommuniceerRequest.getGeadresseerden(), null, var, CommunicatieProductService.TemplateNaam.LICENTIE_GEKOCHT);

        metricsService.stop(timer);
    }
}
