package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.SchadeOpslaanRequest;
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
public class SchadeOpslaanRequestSender extends AbstractSender<SchadeOpslaanRequest> {
    @Inject
    private Destination responseDestination;

    public SchadeOpslaanRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public SchadeOpslaanRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = SchadeOpslaanRequest.class;
    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.SCHADE);
    }

    @Override
    public SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAAN;
    }

}
