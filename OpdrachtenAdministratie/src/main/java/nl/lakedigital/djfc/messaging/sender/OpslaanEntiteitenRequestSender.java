package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component
public class OpslaanEntiteitenRequestSender extends AbstractSender<OpslaanEntiteitenRequest, OpslaanEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanEntiteitenRequestSender.class);

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
    protected Destination getReplyTo() {
        return null;
    }
    //    @Override
    //    public OpslaanEntiteitenRequest maakMessage(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
    //        return opslaanEntiteitenRequest;
    //    }

}
