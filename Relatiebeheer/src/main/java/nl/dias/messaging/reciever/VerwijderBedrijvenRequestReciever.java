package nl.dias.messaging.reciever;

import nl.dias.service.BedrijfService;
import nl.lakedigital.as.messaging.request.VerwijderBedrijvenRequest;

import javax.inject.Inject;

public class VerwijderBedrijvenRequestReciever extends AbstractReciever<VerwijderBedrijvenRequest> {
    @Inject
    private BedrijfService bedrijfService;

    public VerwijderBedrijvenRequestReciever() {
        super(VerwijderBedrijvenRequest.class);
    }

    @Override
    public void verwerkMessage(VerwijderBedrijvenRequest verwijderBedrijvenRequest) {
        for (Long id : verwijderBedrijvenRequest.getIds()) {
            bedrijfService.verwijder(id);
        }
    }
}
