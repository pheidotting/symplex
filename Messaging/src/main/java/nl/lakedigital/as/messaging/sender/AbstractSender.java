package nl.lakedigital.as.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.MDC;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public abstract class AbstractSender<M extends AbstractMessage, T extends Object> {
    protected Class<M> clazz;
    private String channelName;
    private Jedis subscriberJedis;

    public AbstractSender(JedisPool jedisPool, String channelName, Class<M> clazz) {
        this.subscriberJedis = jedisPool.getResource();
        this.channelName = channelName;
        this.clazz = clazz;
    }

    public abstract M maakMessage(T t);

    public void send(T t, Logger logger) {
        M m = maakMessage(t);

        send(m, logger);
    }


    public void setClazz(Class<M> clazz) {
        this.clazz = clazz;
    }

    public void send(final AbstractMessage abstractMessage, Logger logger) {
        try {
            abstractMessage.setTrackAndTraceId(MDC.get("trackAndTraceId"));
            abstractMessage.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
            abstractMessage.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
            abstractMessage.setUrl(MDC.get("url"));

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();

            jaxbMarshaller.marshal(abstractMessage, sw);

            String message = sw.toString();

            logger.debug("Verzenden message {} naar", message);

            subscriberJedis.publish(channelName, message);
        } catch (JAXBException e) {
            logger.error("{}", e);
        }
    }
}

