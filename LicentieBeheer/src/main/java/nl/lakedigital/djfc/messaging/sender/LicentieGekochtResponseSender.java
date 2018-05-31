package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class LicentieGekochtResponseSender extends nl.lakedigital.djfc.messaging.sender.AbstractSender<LicentieGekochtResponse, LicentieGekochtResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControleerLicentieResponseSender.class);

    public LicentieGekochtResponseSender(JmsTemplate jmsTemplate) {
        super(jmsTemplate, LicentieGekochtResponse.class);
    }

    @Override
    public LicentieGekochtResponse maakMessage(LicentieGekochtResponse licentieGekochtResponse) {
        return licentieGekochtResponse;
    }

    public void send(LicentieGekochtResponse licentieGekochtResponse) {
        super.send(licentieGekochtResponse, LOGGER);
    }
}
