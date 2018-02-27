package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.licentie.LicentieToegevoegdRequest;
import nl.lakedigital.as.messaging.sender.AbstractSender;
import redis.clients.jedis.JedisPool;

public class LicentieToegevoegdSender extends AbstractSender<LicentieToegevoegdRequest, LicentieToegevoegdRequest> {
    public LicentieToegevoegdSender(JedisPool jedisPool, String channelName) {
        super(jedisPool, channelName, LicentieToegevoegdRequest.class);
    }

    @Override
    public LicentieToegevoegdRequest maakMessage(LicentieToegevoegdRequest licentieToegevoegd) {
        return null;
    }
}
