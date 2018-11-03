package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.response.taak.OpslaanTaakResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class OpslaanTaakResponseSender extends AbstractSender<OpslaanTaakResponse, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanTaakResponseSender.class);

    public OpslaanTaakResponseSender(JmsTemplate jmsTemplate) {
        super(jmsTemplate, OpslaanTaakResponse.class);
    }

    @Override
    public OpslaanTaakResponse maakMessage(Long taakId) {
        return new OpslaanTaakResponse(taakId);
    }

    @Override
    public void send(Long taakId) {
        send(taakId, LOGGER);
    }
}
