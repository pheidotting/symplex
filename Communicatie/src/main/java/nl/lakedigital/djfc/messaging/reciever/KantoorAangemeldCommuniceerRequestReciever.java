package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.communicatie.KantoorAangemeldCommuniceerRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.KantoorAangemeldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class KantoorAangemeldCommuniceerRequestReciever extends AbstractReciever<KantoorAangemeldCommuniceerRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldCommuniceerRequestReciever.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private KantoorAangemeldService kantoorAangemeldService;

    public KantoorAangemeldCommuniceerRequestReciever() {
        super(KantoorAangemeldCommuniceerRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(KantoorAangemeldCommuniceerRequest kantoorAangemeldCommuniceerRequest) {
        LOGGER.info("Verwerken {}", ReflectionToStringBuilder.toString(kantoorAangemeldCommuniceerRequest));

        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", KantoorAangemeldCommuniceerRequestReciever.class);

        String naam = "Symplex";
        String email = "noreply@symplexict.nl";
        if (kantoorAangemeldCommuniceerRequest.getAfzender() != null) {
            naam = kantoorAangemeldCommuniceerRequest.getAfzender().getNaam();
            email = kantoorAangemeldCommuniceerRequest.getAfzender().getEmail();
        }

        kantoorAangemeldService.stuurMail(kantoorAangemeldCommuniceerRequest.getGeadresseerde().getId(), kantoorAangemeldCommuniceerRequest.getGeadresseerde().getEmail(), kantoorAangemeldCommuniceerRequest.getGeadresseerde().getVoornaam(), kantoorAangemeldCommuniceerRequest.getGeadresseerde().getTussenvoegsel(), kantoorAangemeldCommuniceerRequest.getGeadresseerde().getAchternaam(), kantoorAangemeldCommuniceerRequest.getWachtwoord(), naam, email);

        metricsService.stop(timer);
    }
}
