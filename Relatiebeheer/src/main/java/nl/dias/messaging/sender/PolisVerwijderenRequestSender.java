package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.PolisVerwijderenRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PolisVerwijderenRequestSender extends AbstractSender<PolisVerwijderenRequest, List<Long>> {
    public PolisVerwijderenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public PolisVerwijderenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = PolisVerwijderenRequest.class;
    }

    @Override
    public PolisVerwijderenRequest maakMessage(List<Long> ids) {
        PolisVerwijderenRequest polisVerwijderenRequest = new PolisVerwijderenRequest();
        polisVerwijderenRequest.setIds(ids);

        return polisVerwijderenRequest;
    }
}
