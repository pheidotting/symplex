package nl.lakedigital.as.messaging.reciever;


import inloggen.SessieHolder;
import nl.lakedigital.as.messaging.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public abstract class AbstractReciever<T extends AbstractMessage> extends JedisPubSub {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractReciever.class);

    private Class<T> clazz;
    private JedisPool jedisPool;
    private Jedis subscriberJedis;

    public AbstractReciever(JedisPool jedisPool, String channelName, Class<T> clazz) {
        LOGGER.debug("A");
        this.jedisPool = jedisPool;
        LOGGER.debug("B");
        this.subscriberJedis = jedisPool.getResource();
        LOGGER.debug("C");
        subscriberJedis.subscribe(this, channelName);
        LOGGER.debug("D");
        this.clazz = clazz;
        LOGGER.debug("E");
    }

    @Override
    public void onMessage(String channel, String message) {
        try {
            LOGGER.info("Ontvangen bericht : {}", message);

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            T ontvangenObject = (T) jaxbUnmarshaller.unmarshal(new StringReader(message));

            SessieHolder.get().setIngelogdeGebruiker(ontvangenObject.getIngelogdeGebruiker());
            SessieHolder.get().setTrackAndTraceId(ontvangenObject.getTrackAndTraceId());

            MDC.put("trackAndTraceId", ontvangenObject.getTrackAndTraceId());
            MDC.put("ingelogdeGebruiker", String.valueOf(ontvangenObject.getIngelogdeGebruiker()));
            MDC.put("ingelogdeGebruikerOpgemaakt", ontvangenObject.getIngelogdeGebruikerOpgemaakt());
            MDC.put("url", ontvangenObject.getUrl());


            verwerkMessage(ontvangenObject);
        } catch (JAXBException e) {
            LOGGER.error("Fout opgetreden bij ontvangen Request {}", e);
        }
    }

    public abstract void verwerkMessage(T t);

    @Override
    public void unsubscribe() {
        //        super.unsubscribe();
        LOGGER.debug("UNSUBSCRIBE");
        this.jedisPool.returnResource(this.subscriberJedis);
    }
}

