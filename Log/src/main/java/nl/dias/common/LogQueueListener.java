package nl.dias.common;

import nl.lakedigital.djfc.logging.KibanaEvent;
import nl.lakedigital.djfc.logging.KibanaEventsBuffer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Component
public class LogQueueListener implements MessageListener {
    public static Logger logger = Logger.getLogger(LogQueueListener.class);


    public void onMessage(final Message message) {
        if (message instanceof ObjectMessage) {
            try {
                final KibanaEvent kibanaEvent = (KibanaEvent) ((ObjectMessage) message).getObject();

                KibanaEventsBuffer.log(kibanaEvent);
            } catch (final JMSException e) {
                logger.error(e.getMessage(), e);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}