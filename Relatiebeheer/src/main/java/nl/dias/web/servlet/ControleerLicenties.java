package nl.dias.web.servlet;

import nl.dias.domein.Kantoor;
import nl.dias.messaging.sender.ControleerLicentieRequestSender;
import nl.dias.repository.KantoorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ControleerLicenties {
    private final static Logger LOGGER = LoggerFactory.getLogger(ControleerLicenties.class);

    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private ControleerLicentieRequestSender controleerLicentieRequestSender;

    @Scheduled(cron = "0 0 1 * * ?")
    public void run() {
        for (Kantoor kantoor : kantoorRepository.alles()) {
            LOGGER.debug("KantoorID {}", kantoor.getId());

            controleerLicentieRequestSender.send(kantoor.getId());
        }
    }
}
