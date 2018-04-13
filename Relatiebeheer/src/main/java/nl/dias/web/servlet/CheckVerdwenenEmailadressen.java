package nl.dias.web.servlet;

import nl.dias.service.EmailCheckService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
@PropertySource("file:djfc.app.properties")
public class CheckVerdwenenEmailadressen {
    @Inject
    private EmailCheckService emailCheckService;
    @Value("${slack.channel}")
    private String channel;

    @Scheduled(cron = "0 10 13 * * ?")
    public void run() {
        emailCheckService.checkEmailAdressen(channel);
    }
}
