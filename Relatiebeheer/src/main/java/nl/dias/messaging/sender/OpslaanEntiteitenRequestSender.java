package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class OpslaanEntiteitenRequestSender extends AbstractSender<OpslaanEntiteitenRequest, OpslaanEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanEntiteitenRequestSender.class);

    public OpslaanEntiteitenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public OpslaanEntiteitenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = OpslaanEntiteitenRequest.class;
    }

    @Override
    public OpslaanEntiteitenRequest maakMessage(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
        return opslaanEntiteitenRequest;
    }

    public void send(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
        super.send(opslaanEntiteitenRequest, LOGGER);
    }
}
