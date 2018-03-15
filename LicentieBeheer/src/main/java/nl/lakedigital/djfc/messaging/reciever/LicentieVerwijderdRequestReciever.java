package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.licentie.LicentieVerwijderdRequest;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicentieVerwijderdRequestReciever extends AbstractReciever<LicentieVerwijderdRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LicentieVerwijderdRequest.class);

    public LicentieVerwijderdRequestReciever() {
        super(LicentieVerwijderdRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(LicentieVerwijderdRequest licentieVerwijderdRequest) {
        LOGGER.debug(ReflectionToStringBuilder.toString(licentieVerwijderdRequest));
    }
}
