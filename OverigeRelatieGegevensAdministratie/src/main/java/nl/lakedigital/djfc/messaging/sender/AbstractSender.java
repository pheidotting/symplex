package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSender<M extends AbstractMessage, T extends Object> {
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

    public void send(T t, Logger logger) {
        M m = maakMessage(t);

        send(m, logger);
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

    public void send(final AbstractMessage abstractMessage, Logger logger) {
        for (JmsTemplate jmsTemplate : jmsTemplates) {
            jmsTemplate.send(session -> {
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

                    TextMessage message = session.createTextMessage(sw.toString());

                    if (replyTo != null) {
                        message.setJMSReplyTo(replyTo);
                    }

                    logger.info("Verzenden naar {}, message {} naar", jmsTemplate.getDefaultDestination(), message.getText());

                    return message;
                } catch (JAXBException e) {
                    logger.error("{}", e);
                }
                return null;
            });
        }
    }
}
