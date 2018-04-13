package nl.dias.service;

import com.google.common.util.concurrent.RateLimiter;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class SlackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);

    private SlackSession session;

    public enum Soort {NIEUW, GEWIJZIGD, VERWIJDERD}

    @PostConstruct
    public void init() {
        session = SlackSessionFactory.createWebSocketSlackSession("xoxb-345532041312-MCQfLsoJhlGO8zFtTWg1SvMv");
        try {
            session.connect();
        } catch (IOException e) {
            LOGGER.error("Fout", e);
        }

    }

    public void stuurBericht(String mailadres, Long gebruikerId, Soort soort, String channelName, RateLimiter rateLimiter) {
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
