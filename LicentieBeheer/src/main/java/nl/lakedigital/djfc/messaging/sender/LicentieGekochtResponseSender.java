package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtResponse;
import nl.lakedigital.djfc.service.LicentieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class LicentieGekochtResponseSender extends nl.lakedigital.djfc.messaging.sender.AbstractSender<LicentieGekochtResponse, LicentieGekochtResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ControleerLicentieResponseSender.class);

    private LicentieService licentieService;

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
