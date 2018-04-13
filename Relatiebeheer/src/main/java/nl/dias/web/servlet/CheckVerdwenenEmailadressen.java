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
    private final static Logger LOGGER = LoggerFactory.getLogger(CheckVerdwenenEmailadressen.class);

    @Inject
    private EmailCheckService emailCheckService;
    @Value("${slack.channel}")
    private String channel;

    @Scheduled(cron = "0 0 2 * * ?")
    public void run() {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession("xoxb-345532041312-MCQfLsoJhlGO8zFtTWg1SvMv");
        try {
            session.connect();
        } catch (IOException e) {
            LOGGER.error("Fout", e);
        }

        emailCheckService.checkEmailAdressen(session, channel, RateLimiter.create(1));
    }
}
