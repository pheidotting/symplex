package nl.lakedigital.djfc.logging;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.MDC;

import javax.jms.*;

/**
 * JMSQueue appender is a log4j appender that writes LoggingEvent to a queue.
 *
 * @author faheem
 */
public class JMSQueueAppender extends AppenderSkeleton implements Appender {

    private String brokerUri;
    private String queueName;
    private String applicatie = "";
    private String omgeving;

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    protected synchronized void append(LoggingEvent event) {

        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.brokerUri);

            // Create a Connection
            javax.jms.Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(this.queueName);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);


            String ingelogdeGebruiker = MDC.get("ingelogdeGebruiker");
            Long ig = null;
            if (ingelogdeGebruiker != null && !"null".equals(ingelogdeGebruiker)) {
                ig = Long.valueOf(ingelogdeGebruiker);
            }

            String m = event.getMessage() != null ? event.getMessage().toString() : "";
            String trackAndTraceId = event.getMDC("trackAndTraceId") != null ? event.getMDC("trackAndTraceId").toString() : "";
            String ingelogdeGebruikerOpgemaakt = event.getMDC("ingelogdeGebruikerOpgemaakt") != null ? event.getMDC("ingelogdeGebruikerOpgemaakt").toString() : "";
            String url = event.getMDC("url") != null ? event.getMDC("url").toString() : "";
            int hash = event.getMessage().hashCode();

            ObjectMessage message = session.createObjectMessage(new KibanaEvent(hash, m, event, ig, trackAndTraceId, ingelogdeGebruikerOpgemaakt, url, applicatie, omgeving));

            // Tell the producer to send the message
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBrokerUri(String brokerUri) {
        this.brokerUri = brokerUri;
    }

    public String getBrokerUri() {
        return brokerUri;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public String getApplicatie() {
        return applicatie;
    }

    public void setApplicatie(String applicatie) {
        this.applicatie = applicatie;
    }

    public String getOmgeving() {
        return omgeving;
    }

    public void setOmgeving(String omgeving) {
        this.omgeving = omgeving;
    }
}