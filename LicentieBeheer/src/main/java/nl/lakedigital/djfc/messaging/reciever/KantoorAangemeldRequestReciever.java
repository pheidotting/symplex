package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.KantoorAangemeldRequest;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.LicentieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class KantoorAangemeldRequestReciever extends AbstractReciever<KantoorAangemeldRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(KantoorAangemeldRequestReciever.class);

    @Inject
    private LicentieService licentieService;

    public KantoorAangemeldRequestReciever() {
        super(KantoorAangemeldRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(KantoorAangemeldRequest kantoorAangemeldRequest) {
        LOGGER.info("Verwerken KantoorAangemeldRequest voor Kantoor {}", ReflectionToStringBuilder.toString(kantoorAangemeldRequest.getKantoor()));
        licentieService.maakTrialAan(kantoorAangemeldRequest.getKantoor());
    }
}
