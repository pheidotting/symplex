package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.licentie.LicentieToegevoegdRequest;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicentieToegevoegdRequestReciever extends AbstractReciever<LicentieToegevoegdRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LicentieToegevoegdRequestReciever.class);

    public LicentieToegevoegdRequestReciever() {
        super(LicentieToegevoegdRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(LicentieToegevoegdRequest licentieToegevoegd) {
        LOGGER.debug(ReflectionToStringBuilder.toString(licentieToegevoegd));
    }
}
