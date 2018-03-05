package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.KantoorAangemeldRequest;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KantoorAangemeldRequestReciever extends AbstractReciever<KantoorAangemeldRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldRequestReciever.class);

    public KantoorAangemeldRequestReciever() {
        super(KantoorAangemeldRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(KantoorAangemeldRequest kantoorAangemeldRequest) {
        LOGGER.debug(ReflectionToStringBuilder.toString(kantoorAangemeldRequest));
    }
}
