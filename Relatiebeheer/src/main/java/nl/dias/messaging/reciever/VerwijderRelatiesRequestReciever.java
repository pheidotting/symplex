package nl.dias.messaging.reciever;

import nl.dias.service.GebruikerService;
import nl.lakedigital.as.messaging.request.VerwijderRelatiesRequest;

import javax.inject.Inject;

public class VerwijderRelatiesRequestReciever extends AbstractReciever<VerwijderRelatiesRequest> {
    @Inject
    private GebruikerService gebruikerService;

    public VerwijderRelatiesRequestReciever() {
        super(VerwijderRelatiesRequest.class);
    }

    @Override
    public void verwerkMessage(VerwijderRelatiesRequest verwijderRelatiesRequest) {
        for (Long l : verwijderRelatiesRequest.getIds()) {
            gebruikerService.verwijder(l);
        }
    }
}
