package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.response.taak.NieuweTaakResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NieuweTaakResponseSender extends AbstractSender<NieuweTaakResponse, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NieuweTaakResponseSender.class);

    @Override
    public NieuweTaakResponse maakMessage(Long taakId) {
        return new NieuweTaakResponse(taakId);
    }

    @Override
    public void send(Long taakId) {
        send(taakId, LOGGER);
    }
}
