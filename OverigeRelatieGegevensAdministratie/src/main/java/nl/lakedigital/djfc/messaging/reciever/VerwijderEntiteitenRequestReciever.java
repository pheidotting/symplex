package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.VerwijderEntiteitenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class VerwijderEntiteitenRequestReciever extends AbstractReciever<VerwijderEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitenRequestReciever.class);

    @Inject
    private VerwijderEntiteitenService verwijderEntiteitenService;
    @Inject
    private MetricsService metricsService;

    public VerwijderEntiteitenRequestReciever() {
        super(VerwijderEntiteitenRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(VerwijderEntiteitenRequest verwijderEntiteitenRequest) {
        LOGGER.debug("Verwijder entiteiten bij soortEntiteit {} en entiteitId {}", verwijderEntiteitenRequest.getSoortEntiteit(), verwijderEntiteitenRequest.getEntiteitId());

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", OpslaanEntiteitenRequestReciever.class);

        verwijderEntiteitenService.verwijderen(SoortEntiteit.valueOf(verwijderEntiteitenRequest.getSoortEntiteit().name()), verwijderEntiteitenRequest.getEntiteitId());

        metricsService.stop(timer);
    }
}
