package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class LicentieGekochtRequestReciever extends AbstractReciever<LicentieGekochtRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieGekochtRequestReciever.class);

    @Inject
    private TaakService licentieService;
    //    @Inject
    //    private LicentieGekochtResponseSender licentieGekochtResponseSender;
    @Inject
    private MetricsService metricsService;

    public LicentieGekochtRequestReciever() {
        super(LicentieGekochtRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(LicentieGekochtRequest licentieToegevoegd) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", LicentieGekochtRequestReciever.class);

        //        licentieService.nieuweLicentie(licentieToegevoegd.getLicentieType(), licentieToegevoegd.getKantoor());
        //
        //        LicentieGekochtResponse response = new LicentieGekochtResponse();
        //        response.setKantoor(licentieToegevoegd.getKantoor());
        //        response.setLicentieType(licentieToegevoegd.getLicentieType());
        //        response.setPrijs(licentieService.bepaalPrijs(licentieToegevoegd.getLicentieType()));
        //
        //        licentieGekochtResponseSender.send(response);

        metricsService.stop(context);
    }
}
