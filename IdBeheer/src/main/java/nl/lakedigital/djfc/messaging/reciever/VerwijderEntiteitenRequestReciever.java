package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.domain.Identificatie;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

public class VerwijderEntiteitenRequestReciever  extends AbstractReciever<VerwijderEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitenRequestReciever.class);

    @Inject
    private IdentificatieService identificatieService;
    @Inject
    private MetricsService metricsService;

    public VerwijderEntiteitenRequestReciever() {
        super(VerwijderEntiteitenRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(VerwijderEntiteitenRequest verwijderEntiteitenRequest) {
        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", VerwijderEntiteitenRequestReciever.class);

        LOGGER.debug("Verwijder entiteit met soort {} en id {}", verwijderEntiteitenRequest.getSoortEntiteit(), verwijderEntiteitenRequest.getEntiteitId());
        Identificatie identificatie=     identificatieService.zoek(verwijderEntiteitenRequest.getSoortEntiteit().name(),verwijderEntiteitenRequest.getEntiteitId());

        identificatieService.verwijder(identificatie);

        metricsService.stop(timer);
    }
}
