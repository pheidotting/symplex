package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.BeindigenPolisRequest;
import nl.lakedigital.djfc.service.PolisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class BeindigenPolisRequestReciever extends AbstractReciever<BeindigenPolisRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeindigenPolisRequestReciever.class);

    @Inject
    private PolisService polisService;

    public BeindigenPolisRequestReciever() {
        super(BeindigenPolisRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(BeindigenPolisRequest beindigenPolisRequest) {
        for(Long id : beindigenPolisRequest.getIds()) {
            //            polisService.beeindigen(id);
        }
    }
}
