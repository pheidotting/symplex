package nl.dias.messaging.sender;

import nl.lakedigital.as.messaging.request.communicatie.NieuweVersieRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class NieuweVersieRequestSender extends AbstractSender<NieuweVersieRequest, NieuweVersieRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(NieuweVersieRequestSender.class);

    public NieuweVersieRequestSender(JmsTemplate jmsTemplate) {
        super(jmsTemplate, NieuweVersieRequest.class);
    }

    @Override
    public NieuweVersieRequest maakMessage(NieuweVersieRequest nieuweVersieRequest) {
        return nieuweVersieRequest;
    }

    public void send(NieuweVersieRequest nieuweVersieRequest) {
        super.send(nieuweVersieRequest, LOGGER);
    }
}
