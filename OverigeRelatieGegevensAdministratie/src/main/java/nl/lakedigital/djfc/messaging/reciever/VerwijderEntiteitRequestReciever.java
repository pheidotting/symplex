package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.VerwijderEntiteitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class VerwijderEntiteitRequestReciever extends AbstractReciever<VerwijderEntiteitRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitRequestReciever.class);

    @Inject
    private VerwijderEntiteitService verwijderEntiteitService;
    @Inject
    private MetricsService metricsService;

    public VerwijderEntiteitRequestReciever() {
        super(VerwijderEntiteitRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(VerwijderEntiteitRequest verwijderEntiteitRequest) {
        LOGGER.debug("Verwijder entiteit : soortEntiteit {} en entiteitId {}", verwijderEntiteitRequest.getSoortEntiteitEnEntiteitId().getSoortEntiteit(), verwijderEntiteitRequest.getSoortEntiteitEnEntiteitId().getEntiteitId());

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", OpslaanEntiteitenRequestReciever.class);

        verwijderEntiteitService.verwijderen(verwijderEntiteitRequest.getSoortEntiteitEnEntiteitId().getSoortEntiteit(), verwijderEntiteitRequest.getSoortEntiteitEnEntiteitId().getEntiteitId());

        metricsService.stop(timer);
    }
}
