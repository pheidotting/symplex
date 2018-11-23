package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.taak.WijzigingTaakOpslaanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import java.util.ArrayList;

public class WijzigingTaakOpslaanResponseSender extends AbstractSender<WijzigingTaakOpslaanResponse, WijzigingTaakOpslaanResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WijzigingTaakOpslaanResponseSender.class);

    public WijzigingTaakOpslaanResponseSender() {
        this.jmsTemplates = new ArrayList<>();
        this.clazz = WijzigingTaakOpslaanResponse.class;
    }

    public WijzigingTaakOpslaanResponseSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplates.add(jmsTemplate);
        this.clazz = WijzigingTaakOpslaanResponse.class;
    }

    @Override
    public WijzigingTaakOpslaanResponse maakMessage(WijzigingTaakOpslaanResponse wijzigingTaakOpslaanResponse) {
        return wijzigingTaakOpslaanResponse;
    }

    public void send(WijzigingTaakOpslaanResponse wijzigingTaakOpslaanResponse) {
        super.send(wijzigingTaakOpslaanResponse, LOGGER);
    }
}
