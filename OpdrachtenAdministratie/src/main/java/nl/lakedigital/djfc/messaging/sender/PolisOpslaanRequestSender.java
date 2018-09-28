package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class PolisOpslaanRequestSender extends AbstractSender<PolisOpslaanRequest> {
    @Inject
    private Destination responseDestination;

    public PolisOpslaanRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public PolisOpslaanRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = PolisOpslaanRequest.class;

    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.PAKKET, SoortEntiteit.POLIS);
    }

}
