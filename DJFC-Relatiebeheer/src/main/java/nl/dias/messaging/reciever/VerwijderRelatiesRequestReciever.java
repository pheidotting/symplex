package nl.dias.messaging.reciever;

import nl.dias.service.GebruikerService;
import nl.lakedigital.as.messaging.request.VerwijderRelatiesRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class VerwijderRelatiesRequestReciever extends AbstractReciever<VerwijderRelatiesRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderRelatiesRequestReciever.class);

    public VerwijderRelatiesRequestReciever() {
        super(VerwijderRelatiesRequest.class, LOGGER);
    }

    @Inject
    private GebruikerService gebruikerService;

    @Override
    public void verwerkMessage(VerwijderRelatiesRequest verwijderRelatiesRequest) {
        for (Long l : verwijderRelatiesRequest.getIds()) {
            gebruikerService.verwijder(l);
        }
    }
}
