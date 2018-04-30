package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtRequest;
import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtResponse;
import nl.lakedigital.djfc.messaging.sender.LicentieGekochtResponseSender;
import nl.lakedigital.djfc.service.LicentieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class LicentieGekochtRequestReciever extends AbstractReciever<LicentieGekochtRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieGekochtRequestReciever.class);

    @Inject
    private LicentieService licentieService;
    @Inject
    private LicentieGekochtResponseSender licentieGekochtResponseSender;

    public LicentieGekochtRequestReciever() {
        super(LicentieGekochtRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(LicentieGekochtRequest licentieToegevoegd) {
            licentieService.nieuweLicentie(licentieToegevoegd.getLicentieType(), licentieToegevoegd.getKantoor());

            LicentieGekochtResponse response = new LicentieGekochtResponse();
            response.setKantoor(licentieToegevoegd.getKantoor());
            response.setLicentieType(licentieToegevoegd.getLicentieType());
            response.setPrijs(licentieService.bepaalPrijs(licentieToegevoegd.getLicentieType()));

            licentieGekochtResponseSender.send(response);
    }
}
