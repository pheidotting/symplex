package nl.dias.service;

import com.google.common.util.concurrent.RateLimiter;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SlackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);


    public enum Soort {NIEUW, GEWIJZIGD, VERWIJDERD}

    public void stuurBericht(String mailadres, Long id, SoortEntiteit soortEntiteit, Soort soort, SlackSession session, String channelName, RateLimiter rateLimiter) {
        rateLimiter.acquire();

        LOGGER.info("Slack bericht sturen, mailadres {}, id {}, soortEntiteit {}, soort {}", mailadres, id, soortEntiteit, soort);

        SlackChannel channel = session.findChannelByName(channelName); //make sure bot is a member of the channel.

        String tekst = null;

        switch (soort) {
            case NIEUW:
                tekst = "Nieuw e-mailadres toegevoegd : ";
                break;
            case GEWIJZIGD:
                tekst = "E-mailadres gewijzigd : ";
                break;
            case VERWIJDERD:
                tekst = "E-mailadres verwijderd : ";
                break;
        }

        if (tekst != null) {
            session.sendMessage(channel, tekst + mailadres + " bij " + soortEntiteit + " met id :" + id);
        }
    }
}
