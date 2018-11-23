package nl.lakedigital.djfc.messaging.sender;

import nl.lakedigital.as.messaging.request.ControleerLicentieResponse;
import nl.lakedigital.djfc.commons.domain.Licentie;
import nl.lakedigital.djfc.service.LicentieService;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class ControleerLicentieResponseSender extends AbstractSender<ControleerLicentieResponse, Licentie> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControleerLicentieResponseSender.class);

    private LicentieService licentieService;

    public ControleerLicentieResponseSender(JmsTemplate jmsTemplate) {
        super(jmsTemplate, ControleerLicentieResponse.class);
    }

    public void setLicentieService(LicentieService licentieService) {
        this.licentieService = licentieService;
    }

    @Override
    public ControleerLicentieResponse maakMessage(Licentie licentie) {
        ControleerLicentieResponse controleerLicentieResponse = new ControleerLicentieResponse();
        controleerLicentieResponse.setAantalDagenNog(Days.daysBetween(licentieService.actieveLicentie(licentie), LocalDate.now()).getDays());
        controleerLicentieResponse.setKantoorId(licentie.getKantoor());
        controleerLicentieResponse.setSoortLicentie(licentie.getClass().getSimpleName());

        return controleerLicentieResponse;
    }

    public void send(Licentie licentie) {
        super.send(licentie, LOGGER);
    }
}
