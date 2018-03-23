package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.ControleerLicentieRequest;
import nl.lakedigital.djfc.domain.Licentie;
import nl.lakedigital.djfc.messaging.sender.ControleerLicentieResponseSender;
import nl.lakedigital.djfc.service.LicentieService;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ControleerLicentieRequestReciever extends AbstractReciever<ControleerLicentieRequest> {
    private final static Logger LOGGER = LoggerFactory.getLogger(ControleerLicentieRequestReciever.class);

    @Inject
    private LicentieService licentieService;
    @Inject
    private ControleerLicentieResponseSender controleerLicentieResponseSender;

    public ControleerLicentieRequestReciever() {
        super(ControleerLicentieRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(ControleerLicentieRequest controleerLicentieRequest) {
        LOGGER.info("Controleer licentie voor Kantoor met id {}", controleerLicentieRequest.getKantoorId());

        controleerLicentieResponseSender.setLicentieService(licentieService);

        Licentie licentie = licentieService.eindDatumLicentie(controleerLicentieRequest.getKantoorId());

        if (licentieService.eindDatumLicentie(licentie).isBefore(LocalDate.now().plusDays(7))) {
            controleerLicentieResponseSender.send(licentie);
        }
    }
}
