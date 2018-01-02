package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.BeindigenPolisRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BeindigenPolisRequestSender extends AbstractSender<BeindigenPolisRequest, List<Long>> {
    public BeindigenPolisRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public BeindigenPolisRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = BeindigenPolisRequest.class;
    }

    @Override
    public BeindigenPolisRequest maakMessage(List<Long> ids) {
        BeindigenPolisRequest beindigenPolisRequest = new BeindigenPolisRequest();
        beindigenPolisRequest.setIds(ids);

        return beindigenPolisRequest;
    }
}
