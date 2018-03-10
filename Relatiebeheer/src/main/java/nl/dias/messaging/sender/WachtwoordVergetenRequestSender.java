package nl.dias.messaging.sender;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.lakedigital.as.messaging.request.WachtwoordVergetenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;

public class WachtwoordVergetenRequestSender extends AbstractSender<WachtwoordVergetenRequest, WachtwoordVergetenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WachtwoordVergetenRequestSender.class);

    public WachtwoordVergetenRequestSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public WachtwoordVergetenRequestSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = WachtwoordVergetenRequest.class;
    }

    @Override
    public WachtwoordVergetenRequest maakMessage(WachtwoordVergetenRequest wachtwoordVergetenRequest) {
        return wachtwoordVergetenRequest;
    }

    public void send(Gebruiker gebruiker, String wachtwoord) {
        super.send(new WachtwoordVergetenRequest(gebruiker.getId(), gebruiker.getEmailadres(), gebruiker.getVoornaam(), gebruiker.getTussenvoegsel(), gebruiker.getAchternaam(), wachtwoord, ((Medewerker) gebruiker).getKantoor().getNaam(), ((Medewerker) gebruiker).getKantoor().getEmailadres()), LOGGER);
    }
}
