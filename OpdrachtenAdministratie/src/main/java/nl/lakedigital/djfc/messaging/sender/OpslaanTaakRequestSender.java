package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.jms.Destination;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static nl.lakedigital.djfc.commons.domain.SoortEntiteit.TAAK;

@Component
public class OpslaanTaakRequestSender extends AbstractSender<OpslaanTaakRequest> {
    @Inject
    private Destination responseDestination;

    public OpslaanTaakRequestSender() {
        super();
    }

    public OpslaanTaakRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = OpslaanTaakRequest.class;
    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(TAAK);
    }

    @Override
    public SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAAN;
    }

}
