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

    public LicentieGekochtRequestReciever() {
        super(LicentieGekochtRequest.class, LOGGER);
    }

    @Inject
    private LicentieService licentieService;
    @Inject
    private LicentieGekochtResponseSender licentieGekochtResponseSender;

    @Override
    public void verwerkMessage(LicentieGekochtRequest licentieToegevoegd) {
        try {
            licentieService.nieuweLicentie(licentieToegevoegd.getLicentieType(), licentieToegevoegd.getKantoor());

            LicentieGekochtResponse response = new LicentieGekochtResponse();
            response.setKantoor(licentieToegevoegd.getKantoor());
            response.setLicentieType(licentieToegevoegd.getLicentieType());

            Double prijs = null;
            switch (licentieToegevoegd.getLicentieType()) {
                case "brons":
                    prijs = 5.00;
                    break;
                case "zilver":
                    prijs = 10.00;
                    break;
                case "goud":
                    prijs = 20.00;
                    break;
                case "administratiekantoor":
                    prijs = 15.00;
                    break;
            }
            response.setPrijs(prijs);

            licentieGekochtResponseSender.send(response);

        } catch (LicentieSoortNietGevondenException e) {
            LOGGER.error("Licentie soort {} niet gevonden, kantoor id {}", licentieToegevoegd.getLicentieType(), licentieToegevoegd.getKantoor());
            LOGGER.trace("sonar wil dat ik dit doe {}", e);
        }
    }
}
