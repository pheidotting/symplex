package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.VerwijderEntiteitRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VerwijderEntiteitRequestSender extends AbstractSender<VerwijderEntiteitRequest, SoortEntiteitEnEntiteitId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitRequestSender.class);

    public VerwijderEntiteitRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public VerwijderEntiteitRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = VerwijderEntiteitRequest.class;
    }

    @Override
    public VerwijderEntiteitRequest maakMessage(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        return new VerwijderEntiteitRequest(soortEntiteitEnEntiteitId.getSoortEntiteit(), soortEntiteitEnEntiteitId.getEntiteitId());
    }

    public void send(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        super.send(soortEntiteitEnEntiteitId, LOGGER);
    }
}
