package nl.dias.messaging.reciever;

import nl.dias.service.BedrijfService;
import nl.lakedigital.as.messaging.request.VerwijderBedrijvenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class VerwijderBedrijvenRequestReciever extends AbstractReciever<VerwijderBedrijvenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderBedrijvenRequestReciever.class);

    public VerwijderBedrijvenRequestReciever() {
        super(VerwijderBedrijvenRequest.class, LOGGER);
    }

    @Inject
    private BedrijfService bedrijfService;

    @Override
    public void verwerkMessage(VerwijderBedrijvenRequest verwijderBedrijvenRequest) {
        for (Long id : verwijderBedrijvenRequest.getIds()) {
            bedrijfService.verwijder(id);
        }
    }
}
