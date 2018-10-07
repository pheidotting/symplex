package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class EntiteitenOpgeslagenRequestSender extends AbstractSender<EntiteitenOpgeslagenRequest> {
    @Inject
    private Destination responseDestination;

    public EntiteitenOpgeslagenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public EntiteitenOpgeslagenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = EntiteitenOpgeslagenRequest.class;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.ENTITEITOPGESLAGEN);
    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAAN;
    }
}
