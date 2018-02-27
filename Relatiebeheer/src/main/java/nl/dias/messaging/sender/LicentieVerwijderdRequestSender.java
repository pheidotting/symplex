package nl.dias.messaging.sender;


import nl.lakedigital.as.messaging.request.licentie.LicentieVerwijderdRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class LicentieVerwijderdRequestSender extends AbstractSender<LicentieVerwijderdRequest, LicentieVerwijderdRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LicentieVerwijderdRequestSender.class);

    public LicentieVerwijderdRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = LicentieVerwijderdRequest.class;
    }

    @Override
    public LicentieVerwijderdRequest maakMessage(LicentieVerwijderdRequest licentieVerwijderdRequest) {
        return licentieVerwijderdRequest;
    }

    public void send(LicentieVerwijderdRequest licentieVerwijderdRequest) {
        super.send(licentieVerwijderdRequest, LOGGER);
    }

}
