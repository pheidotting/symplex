package nl.dias.messaging.sender;

import nl.dias.domein.Kantoor;
import nl.lakedigital.as.messaging.request.KantoorAangemeldRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class KantoorAangemeldRequestSender extends AbstractSender<KantoorAangemeldRequest, Kantoor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldRequestSender.class);

    @Inject
    private IdentificatieClient identificatieClient;

    public KantoorAangemeldRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = KantoorAangemeldRequest.class;
    }

    @Override

    public KantoorAangemeldRequest maakMessage(Kantoor kantoor) {
        Identificatie identificatie = identificatieClient.zoekIdentificatie("KANTOOR", kantoor.getId());
        return new KantoorAangemeldRequest(kantoor.getId(), identificatie.getIdentificatie(), kantoor.getNaam(), kantoor.getEmailadres(), null, null, null, null, null);
    }

    public void send(Kantoor kantoor) {
        super.send(kantoor, LOGGER);
    }
}
