package nl.dias.web.authorisatie;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Medewerker;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.GebruikerService;
import nl.dias.service.LoginService;
import nl.dias.web.medewerker.AbstractController;
import nl.lakedigital.djfc.domain.response.AanmeldenKantoor;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

@Controller
public class AanmeldenKantoorController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AanmeldenKantoorController.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST, value = "/aanmeldenKantoor")
    @ResponseBody
    public void opslaan(@RequestBody AanmeldenKantoor aanmeldenKantoor, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException, MessagingException {
        LOGGER.info("Aanmelden nieuw kantoor");

        metricsService.addMetric("kantoorAanmelden", AanmeldenKantoorController.class, null, null);

        zetSessieWaarden(httpServletRequest);

        Kantoor kantoor = new Kantoor();
        kantoor.setAfkorting(aanmeldenKantoor.getAfkorting());
        kantoor.setNaam(aanmeldenKantoor.getBedrijfsnaam());
        kantoor.setIpAdres(httpServletRequest.getRemoteAddr());

        try {
            kantoorRepository.opslaanKantoor(kantoor);
        } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
            LOGGER.trace("Fout bij opslaan kantoor {}", e);
        }

        Medewerker medewerker = new Medewerker();
        medewerker.setKantoor(kantoor);
        medewerker.setAchternaam(aanmeldenKantoor.getAchternaam());
        medewerker.setVoornaam(aanmeldenKantoor.getVoornaam());
        medewerker.setEmailadres(aanmeldenKantoor.getEmailadres());
        try {
            medewerker.setIdentificatie(aanmeldenKantoor.getInlognaam());
            String nieuwWachtwoord = UUID.randomUUID().toString().replace("-", "");
            String tekst = "Je nieuwe wachtwoord is : " + nieuwWachtwoord;

            medewerker.setHashWachtwoord(nieuwWachtwoord);
            medewerker.setMoetWachtwoordUpdaten(true);

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

            // -- Set the FROM and TO fields --
            msg.setFrom(new InternetAddress("Symplex <noreply@symplexict.nl>"));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(medewerker.getEmailadres(), false));
            msg.setSubject("Wachtwoord reset");
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
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("{}", e);
            throw e;
        }
        gebruikerService.opslaan(medewerker);
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "p.heidotting@gmail.com";
            String password = "FR0KQwuPmDhwzIc@npqg%Dw!lI6@^5tx3iY";
            return new PasswordAuthentication(username, password);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/komtAfkortingAlVoor/{afkorting}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public boolean komtAfkortingAlVoor(@PathVariable("afkorting") String afkorting, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        kantoorRepository.zoekOpAfkorting(afkorting).stream().forEach(kantoor -> LOGGER.debug("{} {}", kantoor.getId(), kantoor.getNaam()));

        return !kantoorRepository.zoekOpAfkorting(afkorting).isEmpty();
    }

    private String issueToken(Gebruiker gebruiker, HttpServletRequest httpServletRequest) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(gebruiker.getSalt());

            LocalDateTime expireTime = LocalDateTime.now().plusHours(2);
            token = JWT.create().withSubject(gebruiker.getIdentificatie()).withIssuer(httpServletRequest.getRemoteAddr()).withIssuedAt(new Date()).withExpiresAt(expireTime.toDate()).sign(algorithm);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.error("Fout bij aanmaken JWT", e);
        }

        loginService.nieuwToken(gebruiker.getId(), token);

        return token;
    }

}
