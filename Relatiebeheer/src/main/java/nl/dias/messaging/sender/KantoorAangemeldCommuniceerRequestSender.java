package nl.dias.messaging.sender;

import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.repository.GebruikerRepository;
import nl.lakedigital.as.messaging.request.communicatie.KantoorAangemeldCommuniceerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class KantoorAangemeldCommuniceerRequestSender extends AbstractSender<KantoorAangemeldCommuniceerRequest, KantoorAangemeldCommuniceerRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldCommuniceerRequestSender.class);

    @Inject
    private GebruikerRepository gebruikerRepository;

    public KantoorAangemeldCommuniceerRequestSender() {
    }

    public KantoorAangemeldCommuniceerRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = KantoorAangemeldCommuniceerRequest.class;
    }

    @Override

    public KantoorAangemeldCommuniceerRequest maakMessage(KantoorAangemeldCommuniceerRequest kantoorAangemeldCommuniceerRequest) {
        return kantoorAangemeldCommuniceerRequest;
    }

    public void send(Kantoor kantoor, String wachtwoord) {
        //Er is nu nog maar 1 medewerker
        Medewerker medewerker = gebruikerRepository.alleMedewerkers(kantoor).get(0);

        KantoorAangemeldCommuniceerRequest kantoorAangemeldCommuniceerRequest = new KantoorAangemeldCommuniceerRequest(medewerker.getId(), medewerker.getEmailadres(), medewerker.getVoornaam(), medewerker.getTussenvoegsel(), medewerker.getAchternaam(), null, null, wachtwoord);

        super.send(kantoorAangemeldCommuniceerRequest, LOGGER);
    }
}
