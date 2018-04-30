package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtRequest;
import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtResponse;
import nl.lakedigital.djfc.exception.LicentieSoortNietGevondenException;
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
        try {
            licentieService.nieuweLicentie(licentieToegevoegd.getLicentieType(), licentieToegevoegd.getKantoor());

            LicentieGekochtResponse response = new LicentieGekochtResponse();
            response.setKantoor(licentieToegevoegd.getKantoor());
            response.setLicentieType(licentieToegevoegd.getLicentieType());
            response.setPrijs(licentieService.bepaalPrijs(licentieToegevoegd.getLicentieType()));

            licentieGekochtResponseSender.send(response);

        } catch (LicentieSoortNietGevondenException e) {
            LOGGER.error("Licentie soort {} niet gevonden, kantoor id {}", licentieToegevoegd.getLicentieType(), licentieToegevoegd.getKantoor());
            LOGGER.trace("sonar wil dat ik dit doe {}", e);
        }
    }
}
