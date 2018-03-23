package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.ControleerLicentieRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ControleerLicentieRequestSender extends AbstractSender<ControleerLicentieRequest, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControleerLicentieRequestSender.class);

    public ControleerLicentieRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public ControleerLicentieRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = ControleerLicentieRequest.class;
    }

    @Override
    public ControleerLicentieRequest maakMessage(Long kantoorId) {
        return new ControleerLicentieRequest(kantoorId);
    }

    public void send(Long kantoorId) {
        super.send(kantoorId, LOGGER);
    }
}
