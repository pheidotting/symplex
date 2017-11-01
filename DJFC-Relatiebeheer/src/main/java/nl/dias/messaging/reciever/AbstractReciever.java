package nl.dias.messaging.reciever;

import com.google.gson.Gson;
import inloggen.SessieHolder;
import nl.lakedigital.as.messaging.AbstractMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public abstract class AbstractReciever<T extends AbstractMessage> implements MessageListener {
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractReciever.class);

    protected final Gson gson = new Gson();
    private Class<T> clazz;

    public AbstractReciever(Class<T> clazz, Logger LOGGER) {
        this.clazz = clazz;
        //        this.LOGGER = LOGGER;
    }

    protected MessageProducer replyProducer;
    protected Session session;

    protected Destination replyTo;

    @Override
    public void onMessage(Message message) {
        try {
            LOGGER.info("Ontvangen bericht : {}", ((TextMessage) message).getText());

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            T ontvangenObject = (T) jaxbUnmarshaller.unmarshal(new StringReader(((TextMessage) message).getText()));

            SessieHolder.get().setIngelogdeGebruiker(ontvangenObject.getIngelogdeGebruiker());
            SessieHolder.get().setTrackAndTraceId(ontvangenObject.getTrackAndTraceId());

            replyTo = message.getJMSReplyTo();

            verwerkMessage(ontvangenObject);
        } catch (JMSException | JAXBException e) {
            LOGGER.error("Fout opgetreden bij ontvangen Request {}", e);
        }
    }

    public abstract void verwerkMessage(T t);

    protected void setupMessageQueueConsumer() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //Setup a message producer to respond to messages from clients, we will get the destination
            //to send to from the JMSReplyTo header field from a Message
            this.replyProducer = this.session.createProducer(null);
            this.replyProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (JMSException e) {
            LOGGER.error("{}", e);
        }
    }

}

