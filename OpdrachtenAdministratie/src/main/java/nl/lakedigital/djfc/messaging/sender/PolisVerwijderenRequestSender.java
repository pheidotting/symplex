package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.PolisVerwijderenRequest;
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
public class PolisVerwijderenRequestSender extends AbstractSender<PolisVerwijderenRequest> {
    @Inject
    private Destination responseDestination;

    public PolisVerwijderenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public PolisVerwijderenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = PolisVerwijderenRequest.class;

    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.PAKKET, SoortEntiteit.POLIS);
    }

    @Override
    public SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.VERWIJDEREN;
    }
}
