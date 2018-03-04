package nl.dias.messaging.sender;

import nl.dias.domein.Kantoor;
import nl.lakedigital.as.messaging.request.KantoorAangemeldRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KantoorAangemeldRequestSender extends AbstractSender<KantoorAangemeldRequest, Kantoor> {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldRequestSender.class);

    @Override
    public KantoorAangemeldRequest maakMessage(Kantoor kantoor) {
        return new KantoorAangemeldRequest(kantoor.getNaam(), null, null, null, null, null);
    }

    public void send(Kantoor kantoor) {
        super.send(kantoor, LOGGER);
    }
}
