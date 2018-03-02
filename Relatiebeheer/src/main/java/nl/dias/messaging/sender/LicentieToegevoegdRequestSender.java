package nl.dias.messaging.sender;


import nl.lakedigital.as.messaging.request.licentie.LicentieToegevoegdRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class LicentieToegevoegdRequestSender extends AbstractSender<LicentieToegevoegdRequest, LicentieToegevoegdRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LicentieToegevoegdRequestSender.class);

    public LicentieToegevoegdRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = LicentieToegevoegdRequest.class;
    }

    @Override
    public LicentieToegevoegdRequest maakMessage(LicentieToegevoegdRequest licentieToegevoegd) {
        return licentieToegevoegd;
    }

    public void send(LicentieToegevoegdRequest licentieToegevoegd) {
        super.send(licentieToegevoegd, LOGGER);
    }

}
