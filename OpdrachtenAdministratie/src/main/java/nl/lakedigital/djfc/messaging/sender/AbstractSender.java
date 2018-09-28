package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSender<M extends AbstractMessage> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractSender.class);
    protected List<JmsTemplate> jmsTemplates = new ArrayList<>();
    protected Class<M> clazz;

    public AbstractSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public AbstractSender(final JmsTemplate jmsTemplate, Class<M> clazz) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = clazz;
    }

    protected abstract Destination getReplyTo();

    public abstract List<SoortEntiteit> getSoortEntiteiten();

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
    }

    public void send(String abstractMessage) {
        for (JmsTemplate jmsTemplate : jmsTemplates) {
            jmsTemplate.send(session -> {
                TextMessage message = session.createTextMessage(abstractMessage);

                if (getReplyTo() != null) {
                    message.setJMSReplyTo(getReplyTo());
                    }

                LOGGER.debug("Verzenden message {}", message.getText());
                LOGGER.debug("Naar {}", jmsTemplate.getDefaultDestination());

                    return message;
            });
        }
    }
}
