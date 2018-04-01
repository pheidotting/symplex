package nl.dias.messaging.sender;

import nl.dias.domein.Kantoor;
import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class LicentieGekochtRequestSender extends AbstractSender<LicentieGekochtRequest, LicentieGekochtRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieGekochtRequestSender.class);

    public LicentieGekochtRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = LicentieGekochtRequest.class;
    }

    @Override
    public LicentieGekochtRequest maakMessage(LicentieGekochtRequest licentieGekochtRequest) {
        return licentieGekochtRequest;
    }

    public void send(Kantoor kantoor, String licentieType) {
        LicentieGekochtRequest licentieGekochtRequest = new LicentieGekochtRequest();
        licentieGekochtRequest.setKantoor(kantoor.getId());
        licentieGekochtRequest.setLicentieType(licentieType);
        super.send(licentieGekochtRequest, LOGGER);
    }
}
