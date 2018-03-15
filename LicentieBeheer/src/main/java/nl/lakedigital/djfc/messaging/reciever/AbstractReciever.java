package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.AbstractMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

public abstract class AbstractReciever<T extends AbstractMessage> implements MessageListener {
    private Logger logger = null;
    private Class<T> clazz;
    protected MessageProducer replyProducer;
    protected Session session;
    protected Destination replyTo;

    public AbstractReciever(Class<T> clazz, Logger logger) {
        this.clazz = clazz;
        this.logger = logger;
    }

    @Override
    public void onMessage(Message message) {
        try {
            logger.info("Ontvangen bericht : {}", ((TextMessage) message).getText());

            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            T ontvangenObject = (T) jaxbUnmarshaller.unmarshal(new StringReader(((TextMessage) message).getText()));

            MDC.put("trackAndTraceId", ontvangenObject.getTrackAndTraceId());
            if (ontvangenObject.getIngelogdeGebruiker() != null) {
                MDC.put("ingelogdeGebruiker", String.valueOf(ontvangenObject.getIngelogdeGebruiker()));
                MDC.put("ingelogdeGebruikerOpgemaakt", ontvangenObject.getIngelogdeGebruikerOpgemaakt());
            }
            MDC.put("url", ontvangenObject.getUrl());

            replyTo = message.getJMSReplyTo();

            verwerkMessage(ontvangenObject);
        } catch (JMSException | JAXBException e) {
            logger.error("Fout opgetreden bij ontvangen event {}", e);
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
            logger.error("Fout opgetreden bij ontvangen event {}", e);
        }
    }

}

