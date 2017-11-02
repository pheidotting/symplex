package nl.lakedigital.it;

import nl.lakedigital.as.messaging.AbstractMessage;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSender<M extends AbstractMessage, T extends Object> {
    protected static Logger LOGGER= LoggerFactory.getLogger(AbstractSender.class);
    protected List<JmsTemplate> jmsTemplates = new ArrayList<>();
    protected Class<M> clazz;
    private Destination replyTo;

    public AbstractSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public AbstractSender(final JmsTemplate jmsTemplate, Class<M> clazz) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = clazz;
    }

    public abstract M maakMessage(T t);

    public void send(T t) {
        M m = maakMessage(t);

        send(m);
    }

    public void setReplyTo(Destination replyTo) {
        this.replyTo = replyTo;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
    }

    public void setClazz(Class<M> clazz) {
        this.clazz = clazz;
    }

    public void send(final AbstractMessage abstractMessage) {
        for (JmsTemplate jmsTemplate : jmsTemplates) {
            jmsTemplate.send(session -> {
                try {
                    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    StringWriter sw = new StringWriter();

                    jaxbMarshaller.marshal(abstractMessage, sw);

                    TextMessage message = session.createTextMessage(sw.toString());

                    if (replyTo != null) {
                        message.setJMSReplyTo(replyTo);
                    }

                    LOGGER.debug("Verzenden message {}", message.getText());
                    LOGGER.debug("Naar {}", jmsTemplate.getDefaultDestination());

                    return message;
                } catch (PropertyException e) {
                    LOGGER.error("{}", e);
                } catch (JAXBException e) {
                    LOGGER.error("{}", e);
                }
                return null;
            });
        }
    }
}
