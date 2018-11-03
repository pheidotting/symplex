package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;

public class EntiteitenVerwijderdRequestSender extends AbstractSender<VerwijderEntiteitenRequest, SoortEntiteitEnEntiteitId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiteitenVerwijderdRequestSender.class);

    public EntiteitenVerwijderdRequestSender() {
        this.jmsTemplates = new ArrayList<>();
        this.clazz = VerwijderEntiteitenRequest.class;
    }

    public EntiteitenVerwijderdRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = VerwijderEntiteitenRequest.class;
    }

    @Override
    public VerwijderEntiteitenRequest maakMessage(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();

        verwijderEntiteitenRequest.setEntiteitId(soortEntiteitEnEntiteitId.getEntiteitId());
        verwijderEntiteitenRequest.setSoortEntiteit(soortEntiteitEnEntiteitId.getSoortEntiteit());

        return verwijderEntiteitenRequest;
    }

    public void send(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        super.send(soortEntiteitEnEntiteitId, LOGGER);
    }
}
