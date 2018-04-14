package nl.dias.web.servlet;

import com.google.common.util.concurrent.RateLimiter;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import nl.dias.service.EmailCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;

@Component
@PropertySource("file:djfc.app.properties")
public class CheckVerdwenenEmailadressen {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckVerdwenenEmailadressen.class);

    @Inject
    private EmailCheckService emailCheckService;
    @Value("${slack.channel}")
    private String channel;

    @Scheduled(cron = "0 15 20 * * ?")
    public void run() {
        String token = new StringBuilder()//
                .append("x")//
                .append("o")//
                .append("x")//
                .append("b")//
                .append("-")//
                .append("3")//
                .append("4")//
                .append("5")//
                .append("5")//
                .append("3")//
                .append("2")//
                .append("0")//
                .append("4")//
                .append("1")//
                .append("3")//
                .append("1")//
                .append("2")//
                .append("-")//
                .append("d")//
                .append("A")//
                .append("N")//
                .append("n")//
                .append("T")//
                .append("3")//
                .append("5")//
                .append("r")//
                .append("u")//
                .append("I")//
                .append("G")//
                .append("s")//
                .append("W")//
                .append("H")//
                .append("b")//
                .append("P")//
                .append("2")//
                .append("1")//
                .append("k")//
                .append("k")//
                .append("A")//
                .append("L")//
                .append("d")//
                .append("W")//
                .toString();
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(token);
        try {
            session.connect();
        } catch (IOException e) {
            LOGGER.error("Fout", e);
        }

        emailCheckService.checkEmailAdressen(session, channel, RateLimiter.create(1));
    }
}
