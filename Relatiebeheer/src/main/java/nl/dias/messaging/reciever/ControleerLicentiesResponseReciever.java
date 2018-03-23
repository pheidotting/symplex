package nl.dias.messaging.reciever;

import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.messaging.sender.HerinnerLicentiesRequestSender;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.GebruikerService;
import nl.lakedigital.as.messaging.request.ControleerLicentieResponse;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ControleerLicentiesResponseReciever extends AbstractReciever<ControleerLicentieResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ControleerLicentiesResponseReciever.class);

    @Inject
    private HerinnerLicentiesRequestSender herinnerLicentiesRequestSender;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private GebruikerService gebruikerService;

    public ControleerLicentiesResponseReciever() {
        super(ControleerLicentieResponse.class);
    }

    @Override
    public void verwerkMessage(ControleerLicentieResponse controleerLicentieResponse) {
        LOGGER.debug("Ontvangen {}", ReflectionToStringBuilder.toString(controleerLicentieResponse));

        Kantoor kantoor = kantoorRepository.lees(controleerLicentieResponse.getKantoorId());
        Medewerker medewerker = gebruikerService.alleMedewerkers(kantoor).get(0);

        herinnerLicentiesRequestSender.send(medewerker, controleerLicentieResponse.getSoortLicentie(), controleerLicentieResponse.getAantalDagenNog());
    }
}
