package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;
import java.util.List;

public class EntiteitenOpgeslagenRequestSender extends AbstractSender<EntiteitenOpgeslagenRequest, List<SoortEntiteitEnEntiteitId>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiteitenOpgeslagenRequestSender.class);

    public EntiteitenOpgeslagenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
        this.clazz = EntiteitenOpgeslagenRequest.class;
    }

    public EntiteitenOpgeslagenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = EntiteitenOpgeslagenRequest.class;
    }

    @Override
    public EntiteitenOpgeslagenRequest maakMessage(List<SoortEntiteitEnEntiteitId> ids) {
        EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();

        entiteitenOpgeslagenRequest.setSoortEntiteitEnEntiteitIds(ids);

        return entiteitenOpgeslagenRequest;
    }

    public void send(List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds) {
        super.send(soortEntiteitEnEntiteitIds, LOGGER);
    }
}
