package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.opdracht.opdracht.VerwijderPolisOpdracht;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class VerwijderPolisOpdrachtSender extends AbstractSender<VerwijderPolisOpdracht, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderPolisOpdrachtSender.class);

    public VerwijderPolisOpdrachtSender() {
        this.jmsTemplates = new ArrayList<>();
    }

    public VerwijderPolisOpdrachtSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = VerwijderPolisOpdracht.class;
    }

    @Override
    public VerwijderPolisOpdracht maakMessage(Long id) {
        return new VerwijderPolisOpdracht(id);
    }

    public void send(AbstractMessage abstractMessage) {
        super.send(abstractMessage, LOGGER);
    }

    public void send(Long aLong) {
        super.send(aLong, LOGGER);
    }
}
