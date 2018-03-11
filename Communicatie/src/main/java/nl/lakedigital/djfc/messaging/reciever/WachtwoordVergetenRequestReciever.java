package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.communicatie.WachtwoordVergetenRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.WachtwoordVergetenMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class WachtwoordVergetenRequestReciever extends AbstractReciever<WachtwoordVergetenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WachtwoordVergetenRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private WachtwoordVergetenMailService wachtwoordVergetenMailService;

    public WachtwoordVergetenRequestReciever() {
        super(WachtwoordVergetenRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(WachtwoordVergetenRequest wachtwoordVergetenRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(wachtwoordVergetenRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", WachtwoordVergetenRequestReciever.class);

        wachtwoordVergetenMailService.stuurMail(wachtwoordVergetenRequest.getGeadresseerde().getId(), wachtwoordVergetenRequest.getGeadresseerde().getEmail(), wachtwoordVergetenRequest.getGeadresseerde().getVoornaam(), wachtwoordVergetenRequest.getGeadresseerde().getTussenvoegsel(), wachtwoordVergetenRequest.getGeadresseerde().getAchternaam(), wachtwoordVergetenRequest.getNieuwWachtwoord(), wachtwoordVergetenRequest.getAfzender().getNaam(), wachtwoordVergetenRequest.getAfzender().getEmail());

        metricsService.stop(timer);
    }
}
