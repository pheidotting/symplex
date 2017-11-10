package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.service.envers.SessieHolder;
import org.slf4j.Logger;
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
    protected static Logger LOGGER_;
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
                    abstractMessage.setTrackAndTraceId(SessieHolder.get().getTrackAndTraceId());
                    abstractMessage.setIngelogdeGebruiker(SessieHolder.get().getIngelogdeGebruiker());

                    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    StringWriter sw = new StringWriter();

                    jaxbMarshaller.marshal(abstractMessage, sw);

                    TextMessage message = session.createTextMessage(sw.toString());

                    if (replyTo != null) {
                        message.setJMSReplyTo(replyTo);
                    }

                    LOGGER_.debug("Verzenden message {}", message.getText());
                    LOGGER_.debug("Naar {}", jmsTemplate.getDefaultDestination());

                    return message;
                } catch (PropertyException e) {
                    LOGGER_.error("{}", e);
                } catch (JAXBException e) {
                    LOGGER_.error("{}", e);
                }
                return null;
            });
        }
    }
}
