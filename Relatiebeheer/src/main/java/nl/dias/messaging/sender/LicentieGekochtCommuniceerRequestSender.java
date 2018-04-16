package nl.dias.messaging.sender;

import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.repository.GebruikerRepository;
import nl.lakedigital.as.messaging.request.communicatie.LicentieGekochtCommuniceerRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class LicentieGekochtCommuniceerRequestSender extends AbstractSender<LicentieGekochtCommuniceerRequest, LicentieGekochtCommuniceerRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieGekochtCommuniceerRequestSender.class);

    @Inject
    private GebruikerRepository gebruikerRepository;

    public LicentieGekochtCommuniceerRequestSender() {
        //Moet van Spring aanwezig zijn, maar ja
    }

    public LicentieGekochtCommuniceerRequestSender(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = LicentieGekochtCommuniceerRequest.class;
    }

    @Override
    public LicentieGekochtCommuniceerRequest maakMessage(LicentieGekochtCommuniceerRequest licentieGekochtRequest) {
        return licentieGekochtRequest;
    }

    public void send(Kantoor kantoor, String licentieType, Double prijs) {
        //Er is nu nog maar 1 medewerker
        Medewerker medewerker = gebruikerRepository.alleMedewerkers(kantoor).get(0);

        LicentieGekochtCommuniceerRequest licentieGekochtCommuniceerRequest = new LicentieGekochtCommuniceerRequest(medewerker.getId(), medewerker.getEmailadres(), medewerker.getVoornaam(), medewerker.getTussenvoegsel(), medewerker.getAchternaam(), null, null, licentieType, prijs);

        super.send(licentieGekochtCommuniceerRequest, LOGGER);
    }
}
