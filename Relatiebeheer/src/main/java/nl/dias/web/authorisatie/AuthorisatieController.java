package nl.dias.web.authorisatie;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.MetricsService;
import nl.lakedigital.djfc.commons.json.IngelogdeGebruiker;
import nl.lakedigital.djfc.commons.json.Inloggen;
import nl.lakedigital.djfc.domain.response.InloggenResponse;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
import nl.lakedigital.loginsystem.exception.TeveelFouteInlogPogingenException;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@RequestMapping("/authorisatie")
@Controller
public class AuthorisatieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisatieController.class);

    @Inject
    private AuthorisatieService authorisatieService;
    @Inject
    private GebruikerRepository gebruikerRepository;
    @Inject
    private MetricsService metricsService;

    @RequestMapping(method = RequestMethod.POST, value = "/inloggen", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public InloggenResponse inloggen(@RequestBody Inloggen inloggen, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        Gebruiker gebruiker;
        try {
            LOGGER.debug("Inloggen");
            gebruiker = authorisatieService.inloggen(inloggen.getIdentificatie().trim(), inloggen.getWachtwoord(), httpServletRequest, httpServletResponse);

            String token = issueToken(gebruiker, httpServletRequest);

            // Return the token on the response
            httpServletResponse.setHeader(AUTHORIZATION, "Bearer " + token);

        } catch (NietGevondenException e) {
            metricsService.addMetric(MetricsService.SoortMetric.INLOGGEN_ONBEKENDE_GEBRUIKER, null, null);
            LOGGER.trace("gebruiker niet gevonden", e);
            return new InloggenResponse(1L, false);
        } catch (OnjuistWachtwoordException e) {
            metricsService.addMetric(MetricsService.SoortMetric.INLOGGEN_ONJUIST_WACHTWOORD, null, null);
            LOGGER.trace("Onjuist wachtwoord", e);
            return new InloggenResponse(2L, false);
        } catch (TeveelFouteInlogPogingenException e) {
            metricsService.addMetric(MetricsService.SoortMetric.INLOGGEN_TEVEEL_FOUTIEVE_POGINGEN, null, null);
            LOGGER.trace("Onjuist wachtwoord", e);
            return new InloggenResponse(3L, false);
        }
        metricsService.addMetric(MetricsService.SoortMetric.INLOGGEN, null, null);
        LOGGER.debug(ReflectionToStringBuilder.toString(new InloggenResponse(0L, gebruiker.isMoetWachtwoordUpdaten())));
        return new InloggenResponse(0L, gebruiker.isMoetWachtwoordUpdaten());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ingelogdeGebruiker", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public IngelogdeGebruiker getIngelogdeGebruiker(@RequestParam String userid) {
        LOGGER.debug("Ophalen ingelogde gebruiker '{}'", userid);

        Gebruiker gebruiker = getGebruiker(userid);

        IngelogdeGebruiker ingelogdeGebruiker = new IngelogdeGebruiker();
        if (gebruiker != null) {
            ingelogdeGebruiker.setId(gebruiker.getId().toString());
            ingelogdeGebruiker.setGebruikersnaam(gebruiker.getNaam());
            if (gebruiker instanceof Beheerder) {
                // Nog te doen :)
            } else if (gebruiker instanceof Medewerker) {
                ingelogdeGebruiker.setKantoor(((Medewerker) gebruiker).getKantoor().getNaam());
                ingelogdeGebruiker.setKantoorId(((Medewerker) gebruiker).getKantoor().getId());
                ingelogdeGebruiker.setKantoorAfkorting(((Medewerker) gebruiker).getKantoor().getAfkorting());
            } else if (gebruiker instanceof Relatie) {
                ingelogdeGebruiker.setKantoor(((Relatie) gebruiker).getKantoor().getNaam());
                ingelogdeGebruiker.setKantoorId(((Relatie) gebruiker).getKantoor().getId());
                ingelogdeGebruiker.setKantoorAfkorting(((Relatie) gebruiker).getKantoor().getAfkorting());
            }

            return ingelogdeGebruiker;
        }

        throw new UnauthorizesdAccessException();
    }

    @RequestMapping(method = RequestMethod.POST, value = "wachtwoordvergeten")
    @ResponseBody
    public void wachtwoordvergeten(@RequestBody String identificatie) {
        metricsService.addMetric(MetricsService.SoortMetric.WACHTWOORD_VERGETEN, null, null);
        LOGGER.info("Wachtwoord vergeten");
        try {
            Gebruiker gebruiker = gebruikerRepository.zoekOpIdentificatie(identificatie);

            LOGGER.info("Nieuw wachtwoord voor {}", identificatie);
            if (gebruiker != null) {
                LOGGER.info("Gebruikerid hierbij gevonden {}, met mailadres {}", gebruiker.getId(), gebruiker.getEmailadres());
            }

            String nieuwWachtwoord = UUID.randomUUID().toString().replace("-", "");
            String tekst = "Je nieuwe wachtwoord is : " + nieuwWachtwoord;

            if (gebruiker != null && gebruiker.getEmailadres() != null && !"".equals(gebruiker.getEmailadres())) {
                gebruiker.setHashWachtwoord(nieuwWachtwoord);
                gebruiker.setMoetWachtwoordUpdaten(true);
                gebruikerRepository.opslaan(gebruiker);

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

                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(gebruiker.getEmailadres(), false));
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
            }

        } catch (MessagingException | NietGevondenException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("Fout opgetreden bij het aanmaken van een nieuw wachtwoord voor {} : {}", identificatie, e);
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = "p.heidotting@gmail.com";
            String password = "FR0KQwuPmDhwzIc@npqg%Dw!lI6@^5tx3iY";
            return new PasswordAuthentication(username, password);
        }
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizesdAccessException extends RuntimeException {

    }

    private Gebruiker getGebruiker(String userid) {
        Gebruiker gebruiker = null;

        try {
            gebruiker = authorisatieService.zoekOpIdentificatie(userid);
        } catch (NietGevondenException nge) {
            LOGGER.trace("Gebruiker dus niet gevonden", nge);
        }

        return gebruiker;
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

        return token;
    }
}
