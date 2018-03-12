package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.communicatie.Geadresseerde;
import nl.lakedigital.as.messaging.request.communicatie.NieuweVersieRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.NieuweVersieMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class NieuweVersieRequestReciever extends AbstractReciever<NieuweVersieRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NieuweVersieRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private NieuweVersieMailService nieuweVersieMailService;

    public NieuweVersieRequestReciever() {
        super(NieuweVersieRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(NieuweVersieRequest nieuweVersieRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(nieuweVersieRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", NieuweVersieRequestReciever.class);

        Geadresseerde geadresseerde = nieuweVersieRequest.getGeadresseerden().get(0);
        nieuweVersieMailService.stuurMail(geadresseerde.getId(), geadresseerde.getEmail(), geadresseerde.getVoornaam(), geadresseerde.getTussenvoegsel(), geadresseerde.getAchternaam(), nieuweVersieRequest.getVersie(), nieuweVersieRequest.getReleasenotes());

        metricsService.stop(timer);
    }
}
