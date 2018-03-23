package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.communicatie.HerinnerLicentiesRequest;
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
public class HerinnerLicentiesRequestReciever extends AbstractReciever<HerinnerLicentiesRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HerinnerLicentiesRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private CommunicatieProductService communicatieProductService;

    public HerinnerLicentiesRequestReciever() {
        super(HerinnerLicentiesRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(HerinnerLicentiesRequest herinnerLicentiesRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(herinnerLicentiesRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", HerinnerLicentiesRequestReciever.class);

        Map<String, String> var = new HashMap<>();
        var.put("huidigeLicentie", herinnerLicentiesRequest.getHuidigeLicentie());
        var.put("aantalDagenNog", String.valueOf(herinnerLicentiesRequest.getAantalDagenNog()));

        communicatieProductService.versturen(herinnerLicentiesRequest.getGeadresseerden(), null, var, CommunicatieProductService.TemplateNaam.HERINNER_LICENTIE);

        metricsService.stop(timer);
    }
}
