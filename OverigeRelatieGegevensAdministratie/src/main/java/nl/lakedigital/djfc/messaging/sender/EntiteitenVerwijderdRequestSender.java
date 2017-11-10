package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;

public class EntiteitenVerwijderdRequestSender extends AbstractSender<VerwijderEntiteitenRequest, SoortEntiteitEnEntiteitId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiteitenVerwijderdRequestSender.class);


    public EntiteitenVerwijderdRequestSender() {
        this.jmsTemplates = new ArrayList<>();
        this.LOGGER_ = LOGGER;
        this.clazz = VerwijderEntiteitenRequest.class;
    }

    public EntiteitenVerwijderdRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.LOGGER_ = LOGGER;
        this.clazz = VerwijderEntiteitenRequest.class;
    }

    @Override
    public VerwijderEntiteitenRequest maakMessage(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();

        verwijderEntiteitenRequest.setEntiteitId(soortEntiteitEnEntiteitId.getEntiteitId());
        verwijderEntiteitenRequest.setSoortEntiteit(soortEntiteitEnEntiteitId.getSoortEntiteit());

        return verwijderEntiteitenRequest;
    }
}
