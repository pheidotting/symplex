package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.ControleerLicentieRequest;
import nl.lakedigital.djfc.commons.domain.Licentie;
import nl.lakedigital.djfc.messaging.sender.ControleerLicentieResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.LicentieService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ControleerLicentieRequestReciever extends AbstractReciever<ControleerLicentieRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControleerLicentieRequestReciever.class);

    @Inject
    private LicentieService licentieService;
    @Inject
    private ControleerLicentieResponseSender controleerLicentieResponseSender;
    @Inject
    private MetricsService metricsService;

    public ControleerLicentieRequestReciever() {
        super(ControleerLicentieRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(ControleerLicentieRequest controleerLicentieRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", ControleerLicentieRequestReciever.class);

        LOGGER.info("Controleer licentie voor Kantoor met id {}", controleerLicentieRequest.getKantoorId());

        controleerLicentieResponseSender.setLicentieService(licentieService);

        Licentie licentie = licentieService.actieveLicentie(controleerLicentieRequest.getKantoorId());

        if (licentieService.actieveLicentie(licentie).isBefore(LocalDate.now().plusDays(7)) && licentieService.actieveLicentie(licentie).isAfter(LocalDate.now())) {
            controleerLicentieResponseSender.send(licentie);
        }

        metricsService.stop(context);
    }
}
