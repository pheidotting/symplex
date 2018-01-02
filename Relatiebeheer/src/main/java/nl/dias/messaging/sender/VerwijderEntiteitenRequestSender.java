package nl.dias.messaging.sender;

import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VerwijderEntiteitenRequestSender extends AbstractSender<VerwijderEntiteitenRequest, SoortEntiteitEnEntiteitId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitenRequestSender.class);

    public VerwijderEntiteitenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public VerwijderEntiteitenRequestSender(final JmsTemplate jmsTemplateId, final JmsTemplate jmsTemplateOga, final JmsTemplate jmsTemplatePa) {
        this.jmsTemplates.add(jmsTemplateId);
        this.jmsTemplates.add(jmsTemplateOga);
        this.jmsTemplates.add(jmsTemplatePa);
        this.clazz = VerwijderEntiteitenRequest.class;
    }

    @Override
    public VerwijderEntiteitenRequest maakMessage(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();

        verwijderEntiteitenRequest.setSoortEntiteit(soortEntiteitEnEntiteitId.getSoortEntiteit());
        verwijderEntiteitenRequest.setEntiteitId(soortEntiteitEnEntiteitId.getEntiteitId());

        return verwijderEntiteitenRequest;
    }

    public void send(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        super.send(soortEntiteitEnEntiteitId, LOGGER);
    }
}
