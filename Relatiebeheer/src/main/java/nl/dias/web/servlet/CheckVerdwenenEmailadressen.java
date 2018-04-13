package nl.dias.web.servlet;

import nl.dias.service.EmailCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@PropertySource("file:djfc.app.properties")
public class CheckVerdwenenEmailadressen {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckVerdwenenEmailadressen.class);

    @Inject
    private EmailCheckService emailCheckService;
    @Value("${slack.channel}")
    private String channel;

    @Scheduled(cron = "0 40 11 * * ?")
    public void run() {
        emailCheckService.checkEmailAdressen(channel);
    }
}
