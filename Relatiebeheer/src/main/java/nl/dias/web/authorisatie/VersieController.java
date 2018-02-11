package nl.dias.web.authorisatie;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Versie;
import nl.dias.domein.VersieGelezen;
import nl.dias.repository.GebruikerRepository;
import nl.dias.repository.VersieRepository;
import nl.dias.web.medewerker.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestMapping("versies")
@Controller
public class VersieController extends AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(VersieController.class);

    @Inject
    private VersieRepository versieRepository;
    @Inject
    private GebruikerRepository gebruikerRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/nieuweversie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public void nieuweversie(@RequestBody String versieinfo) {
        if (versieinfo.trim().toLowerCase().startsWith("versie")) {
            versieinfo = versieinfo.substring(6).trim();

            int pos = versieinfo.indexOf(" ");
            String versieNummer = versieinfo.substring(0, pos);
            String releasenotes = versieinfo.substring(pos + 1);

            Versie versie = new Versie(versieNummer, releasenotes);
            versieRepository.opslaan(versie);

            List<Long> gebruikerIds = gebruikerRepository.alleGebruikersDieKunnenInloggen().stream().map(new Function<Gebruiker, Long>() {
                @Override
                public Long apply(Gebruiker gebruiker) {
                    return gebruiker.getId();
                }
            }).collect(Collectors.toList());

            for (Long gebruikerId : gebruikerIds) {
                versieRepository.opslaan(new VersieGelezen(versie.getId(), gebruikerId));
            }

            verstuurMail("bene@dejongefinancieelconsult.nl", "Nieuwe versie op de PRODUCTIEomgeving", "Ik heb zojuist een nieuwe versie op de PRODUCTIEomgeving geplaatst, de wijzigingen zijn:\n" + versieNummer + " " + releasenotes);
            verstuurMail("patrick@heidotting.nl", "Nieuwe versie op de PRODUCTIEomgeving", "Ik heb zojuist een nieuwe versie op de PRODUCTIEomgeving geplaatst, de wijzigingen zijn:\n" + versieNummer + " " + releasenotes);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkNieuweversie", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Map<String, String> checkNieuweversie(HttpServletRequest httpServletRequest) {
        Map<String, String> result = new HashMap<>();

        if (getIngelogdeGebruiker(httpServletRequest) != null) {
            Long gebruikerId = getIngelogdeGebruiker(httpServletRequest).getId();

            for (Versie v : versieRepository.getOngelezenVersies(gebruikerId)) {
                result.put(v.getVersie(), v.getReleasenotes());
            }
        }
        return result;
    }

    private void verstuurMail(String naar, String onderwerp, String tekst) {
        String mailHost = "smtp.gmail.com";
        Integer smtpPort = 587;

        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", smtpPort);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.user", "p.heidotting@gmail.com");
        properties.setProperty("mail.smtp.password", "FR0KQwuPmDhwzIc@npqg%Dw!lI6@^5tx3iY");
        properties.setProperty("mail.smtp.auth", "true");
        Authenticator auth = new SMTPAuthenticator();
        Session emailSession = Session.getDefaultInstance(properties, auth);

        Message msg = new MimeMessage(emailSession);

        try {
            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("Symplex <noreply@symplexict.nl>"));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(naar, false));
            msg.setSubject(onderwerp);
            msg.setSentDate(new Date());

            BodyPart messageBodyPart = new MimeBodyPart();

            //evt bijlages bijzoeken

            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            //                 Now set the actual message
            messageBodyPart.setText(tekst);
            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            msg.setContent(multipart);

            Transport transport = emailSession.getTransport("smtp");
            transport.connect(mailHost, smtpPort, null, null);
            //Zeker weten dat de mail niet al verstuurd is door een andere Thread
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "p.heidotting@gmail.com";
            String password = "FR0KQwuPmDhwzIc@npqg%Dw!lI6@^5tx3iY";
            return new PasswordAuthentication(username, password);
        }
    }
}
