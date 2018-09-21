package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.commons.domain.Identificatie;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

public class EntiteitenOpgeslagenRequestReciever extends AbstractReciever<EntiteitenOpgeslagenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiteitenOpgeslagenRequestReciever.class);

    @Inject
    private IdentificatieService identificatieService;
    @Inject
    private MetricsService metricsService;

    public EntiteitenOpgeslagenRequestReciever() {
        super(EntiteitenOpgeslagenRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest) {
        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", EntiteitenOpgeslagenRequestReciever.class);

        for (SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId : entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds()) {
            Identificatie identificatie = new Identificatie(soortEntiteitEnEntiteitId.getSoortEntiteit().name(), soortEntiteitEnEntiteitId.getEntiteitId());
            LOGGER.debug("{}", identificatie);
            identificatieService.opslaan(identificatie);
        }

        metricsService.stop(timer);
    }
}
