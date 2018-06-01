package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.KantoorAangemeldRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class KantoorAangemeldRequestReciever extends AbstractReciever<KantoorAangemeldRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldRequestReciever.class);

    @Inject
    private TaakService licentieService;
    @Inject
    private MetricsService metricsService;

    public KantoorAangemeldRequestReciever() {
        super(KantoorAangemeldRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(KantoorAangemeldRequest kantoorAangemeldRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", KantoorAangemeldRequestReciever.class);

        LOGGER.info("Verwerken KantoorAangemeldRequest voor Kantoor met id {}", kantoorAangemeldRequest.getKantoor().getId());
        //        licentieService.maakTrialAan(kantoorAangemeldRequest.getKantoor());

        metricsService.stop(context);
    }
}
