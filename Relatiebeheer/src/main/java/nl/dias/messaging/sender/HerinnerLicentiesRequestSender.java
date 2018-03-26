package nl.dias.messaging.sender;

import nl.dias.domein.Gebruiker;
import nl.lakedigital.as.messaging.request.communicatie.HerinnerLicentiesRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;

public class HerinnerLicentiesRequestSender extends AbstractSender<HerinnerLicentiesRequest, HerinnerLicentiesRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HerinnerLicentiesRequestSender.class);

    public HerinnerLicentiesRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public HerinnerLicentiesRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = HerinnerLicentiesRequest.class;
    }

    @Override
    public HerinnerLicentiesRequest maakMessage(HerinnerLicentiesRequest herinnerLicentiesRequest) {
        return herinnerLicentiesRequest;
    }

    public void send(Gebruiker gebruiker, String huidigeLicentie, int aantalDagenNog) {
        super.send(new HerinnerLicentiesRequest(gebruiker.getId(), gebruiker.getEmailadres(), gebruiker.getVoornaam(), gebruiker.getTussenvoegsel(), gebruiker.getAchternaam(), null, null, huidigeLicentie, aantalDagenNog), LOGGER);
    }
}
