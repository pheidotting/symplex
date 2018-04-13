package nl.dias.service;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SlackService {
    public enum Soort {NIEUW, GEWIJZIGD, VERWIJDERD}

    public void stuurBericht(String mailadres, Long gebruikerId, Soort soort) {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession("slack-bot-auth-token");
        try {
            session.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SlackChannel channel = session.findChannelByName("general"); //make sure bot is a member of the channel.
        session.sendMessage(channel, "hi im a bot");

    }
}
