package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class EntiteitenOpgeslagenRequestSender extends AbstractSender<EntiteitenOpgeslagenRequest, List<SoortEntiteitEnEntiteitId>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiteitenOpgeslagenRequestSender.class);

    public EntiteitenOpgeslagenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
        this.LOGGER_ = LOGGER;
    }

    public EntiteitenOpgeslagenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.LOGGER_ = LOGGER;
        this.clazz = EntiteitenOpgeslagenRequest.class;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.ADRES, SoortEntiteit.OPMERKING, SoortEntiteit.REKENINGNUMMER, SoortEntiteit.TELEFOONNUMMER);
    }

    @Override
    public EntiteitenOpgeslagenRequest maakMessage(List<SoortEntiteitEnEntiteitId> ids) {
        EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();

        entiteitenOpgeslagenRequest.setSoortEntiteitEnEntiteitIds(ids);

        return entiteitenOpgeslagenRequest;
    }
}
