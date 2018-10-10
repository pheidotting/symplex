package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
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
public class OpslaanEntiteitenRequestSender extends AbstractSender<OpslaanEntiteitenRequest> {
    @Inject
    private Destination responseDestination;

    public OpslaanEntiteitenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public OpslaanEntiteitenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = OpslaanEntiteitenRequest.class;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.ADRES, SoortEntiteit.OPMERKING, SoortEntiteit.REKENINGNUMMER, SoortEntiteit.TELEFOONNUMMER);
    }

    @Override
    public SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAAN;
    }

    @Override
    protected Destination getReplyTo() {
        return responseDestination;
    }
}
