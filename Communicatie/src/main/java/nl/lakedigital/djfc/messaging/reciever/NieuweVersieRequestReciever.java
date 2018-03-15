package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.communicatie.NieuweVersieRequest;
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
public class NieuweVersieRequestReciever extends AbstractReciever<NieuweVersieRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NieuweVersieRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private CommunicatieProductService communicatieProductService;

    public NieuweVersieRequestReciever() {
        super(NieuweVersieRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(NieuweVersieRequest nieuweVersieRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(nieuweVersieRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", NieuweVersieRequestReciever.class);

        Map<String, String> var = new HashMap<>();
        var.put("versie", nieuweVersieRequest.getVersie());
        var.put("releasenotes", nieuweVersieRequest.getReleasenotes());

        communicatieProductService.versturen(nieuweVersieRequest.getGeadresseerden(), null, var, CommunicatieProductService.TemplateNaam.NIEUWE_VERSIE);

        metricsService.stop(timer);
    }
}
