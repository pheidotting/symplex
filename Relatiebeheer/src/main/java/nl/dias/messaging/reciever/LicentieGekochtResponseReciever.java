package nl.dias.messaging.reciever;

import nl.dias.domein.Kantoor;
import nl.dias.messaging.sender.LicentieGekochtCommuniceerRequestSender;
import nl.dias.repository.KantoorRepository;
import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtResponse;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class LicentieGekochtResponseReciever extends AbstractReciever<LicentieGekochtResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LicentieGekochtResponseReciever.class);

    @Inject
    private LicentieGekochtCommuniceerRequestSender licentieGekochtCommuniceerRequestSender;
    @Inject
    private KantoorRepository kantoorRepository;

    public LicentieGekochtResponseReciever() {
        super(LicentieGekochtResponse.class);
    }

    @Override
    public void verwerkMessage(LicentieGekochtResponse licentieGekochtResponse) {
        LOGGER.info("Ontvangen {}", ReflectionToStringBuilder.toString(licentieGekochtResponse));

        Kantoor kantoor = kantoorRepository.lees(licentieGekochtResponse.getKantoor());

        licentieGekochtCommuniceerRequestSender.send(kantoor, licentieGekochtResponse.getLicentieType(), licentieGekochtResponse.getPrijs());
    }
}
