package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.TaakOpslaan;
import nl.lakedigital.as.messaging.domain.Taak;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class TaakOpslaanSender extends AbstractSender<TaakOpslaan, Taak> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakOpslaanSender.class);

    public TaakOpslaanSender() {
        this.jmsTemplates = new ArrayList<>();
        this.LOGGER_ = LOGGER;
    }

    public TaakOpslaanSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.LOGGER_ = LOGGER;
        this.clazz = TaakOpslaan.class;
    }

    @Override
    public TaakOpslaan maakMessage(Taak taak) {
        TaakOpslaan taakOpslaan = new TaakOpslaan();

        taakOpslaan.setTaak(taak);

        return taakOpslaan;
    }
}
