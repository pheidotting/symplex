package nl.dias.messaging.sender;

import nl.dias.domein.Kantoor;
import nl.lakedigital.as.messaging.request.KantoorAangemeldRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class KantoorAangemeldRequestSender extends AbstractSender<KantoorAangemeldRequest, Kantoor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldRequestSender.class);


    public KantoorAangemeldRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = KantoorAangemeldRequest.class;
    }

    @Override

    public KantoorAangemeldRequest maakMessage(Kantoor kantoor) {
        return new KantoorAangemeldRequest(kantoor.getId(), kantoor.getNaam(), kantoor.getEmailadres(), null, null, null, null, null);
    }

    public void send(Kantoor kantoor) {
        super.send(kantoor, LOGGER);
    }
}
