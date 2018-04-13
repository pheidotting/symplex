package nl.dias.service;

import com.google.common.util.concurrent.RateLimiter;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SlackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);


    public enum Soort {NIEUW, GEWIJZIGD, VERWIJDERD}

    public void stuurBericht(String mailadres, Long gebruikerId, Soort soort, SlackSession session, String channelName, RateLimiter rateLimiter) {
        rateLimiter.acquire();

        LOGGER.info("Slack bericht sturen, mailadres {}, gebruikerId {}, soort {}", mailadres, gebruikerId, soort);

        SlackChannel channel = session.findChannelByName(channelName); //make sure bot is a member of the channel.

        String tekst = null;

        switch (soort) {
            case NIEUW:
                tekst = "Nieuw e-mailadres toegevoegd : " + mailadres + " bij Relatie met id :" + gebruikerId;
                break;
            case GEWIJZIGD:
                tekst = "E-mailadres gewijzigd : " + mailadres + " bij Relatie met id :" + gebruikerId;
                break;
            case VERWIJDERD:
                tekst = "E-mailadres verwijderd : " + mailadres + " bij Relatie met id :" + gebruikerId;
                break;
        }

        if (tekst != null) {
            session.sendMessage(channel, tekst);
        }
    }
}
