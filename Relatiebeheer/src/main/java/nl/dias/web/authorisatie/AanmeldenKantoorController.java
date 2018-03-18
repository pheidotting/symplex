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
import nl.dias.messaging.sender.KantoorAangemeldCommuniceerRequestSender;
import nl.dias.repository.KantoorRepository;
import nl.dias.service.GebruikerService;
import nl.dias.service.KantoorService;
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
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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
    private KantoorService kantoorService;
    @Inject
    private LoginService loginService;
    @Inject
    private KantoorAangemeldCommuniceerRequestSender kantoorAangemeldCommuniceerRequestSender;

    @RequestMapping(method = RequestMethod.POST, value = "/aanmeldenKantoor")
    @ResponseBody
    public boolean opslaan(@RequestBody AanmeldenKantoor aanmeldenKantoor, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException, MessagingException {
        LOGGER.info("Aanmelden nieuw kantoor");

        metricsService.addMetric("kantoorAanmelden", AanmeldenKantoorController.class, null, null);

        zetSessieWaarden(httpServletRequest);

        Kantoor kantoor = new Kantoor();
        kantoor.setAfkorting(aanmeldenKantoor.getAfkorting());
        kantoor.setNaam(aanmeldenKantoor.getBedrijfsnaam());
        kantoor.setIpAdres(httpServletRequest.getRemoteAddr());
        kantoor.setEmailadres(aanmeldenKantoor.getEmailadres());

        try {
            kantoorService.aanmelden(kantoor);
        } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
            LOGGER.trace("Fout bij opslaan kantoor {}", e);
        }

        Medewerker medewerker = new Medewerker();
        medewerker.setKantoor(kantoor);
        medewerker.setAchternaam(aanmeldenKantoor.getAchternaam());
        medewerker.setVoornaam(aanmeldenKantoor.getVoornaam());
        medewerker.setEmailadres(aanmeldenKantoor.getEmailadres());
            medewerker.setIdentificatie(aanmeldenKantoor.getInlognaam());
            String nieuwWachtwoord = UUID.randomUUID().toString().replace("-", "");

        try {
            medewerker.setHashWachtwoord(nieuwWachtwoord);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("{}", e);
            throw e;
        }
        medewerker.setMoetWachtwoordUpdaten(true);

        gebruikerService.opslaan(medewerker);

        kantoorAangemeldCommuniceerRequestSender.send(kantoor, nieuwWachtwoord);

        return true;
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
