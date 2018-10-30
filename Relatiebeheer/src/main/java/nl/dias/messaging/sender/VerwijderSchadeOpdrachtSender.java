package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.opdracht.opdracht.VerwijderSchadeOpdracht;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VerwijderSchadeOpdrachtSender extends AbstractSender<VerwijderSchadeOpdracht, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderSchadeOpdrachtSender.class);

    public VerwijderSchadeOpdrachtSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public VerwijderSchadeOpdrachtSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = VerwijderSchadeOpdracht.class;
    }

    @Override
    public VerwijderSchadeOpdracht maakMessage(Long id) {
        return new VerwijderSchadeOpdracht(id);
    }

    public void send(AbstractMessage abstractMessage) {
        super.send(abstractMessage, LOGGER);
    }

    public void send(Long aLong) {
        super.send(aLong, LOGGER);
    }
}
