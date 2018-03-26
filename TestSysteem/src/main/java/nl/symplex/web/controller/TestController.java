package nl.symplex.web.controller;

import nl.symplex.web.dto.Mail;
import nl.symplex.web.dto.MailLijst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;

@Controller
public class TestController {
    @Inject
    private Wiser smtp;

    //    @PostConstruct
    //    public void init(){
    //        smtp = new Wiser(2347);
    //        smtp.start();
    //    }
    //    @PreDestroy
    //    public void destroy(){
    //        smtp.stop();
    //    }
    @RequestMapping(method = RequestMethod.GET, value = "/mails")
    @ResponseBody
    public MailLijst mails() throws MessagingException, IOException {
        MailLijst mailLijst = new MailLijst();
        for (WiserMessage wiserMessage : smtp.getMessages()) {

            Multipart mp = (Multipart) wiserMessage.getMimeMessage().getContent();
            BodyPart bp = mp.getBodyPart(0);

            mailLijst.addMail(new Mail(wiserMessage.getEnvelopeSender(), wiserMessage.getEnvelopeReceiver(), bp.getContent().toString()));
        }
        smtp.getMessages().clear();

        return mailLijst;
    }
}
