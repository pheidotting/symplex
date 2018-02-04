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
import nl.dias.service.MetricsService;
import nl.dias.web.medewerker.AbstractController;
import nl.lakedigital.djfc.domain.response.AanmeldenKantoor;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

//@RequestMapping("/aanmeldenKantoor")
@Controller
public class AanmeldenKantoorController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AanmeldenKantoorController.class);

    @Inject
    private MetricsService metricsService;
    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private KantoorRepository kantoorRepository;

    @RequestMapping(method = RequestMethod.POST, value = "/aanmeldenKantoor")
    @ResponseBody
    public boolean opslaan(@RequestBody AanmeldenKantoor aanmeldenKantoor, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        LOGGER.info("Aanmelden nieuw kantoor");

        metricsService.addMetric(MetricsService.SoortMetric.KANTOOR_AANMELDEN, null, null);

        zetSessieWaarden(httpServletRequest);

        Kantoor kantoor = new Kantoor();
        kantoor.setAfkorting(aanmeldenKantoor.getAfkorting());
        kantoor.setNaam(aanmeldenKantoor.getBedrijfsnaam());

        try {
            kantoorRepository.opslaanKantoor(kantoor);
        } catch (PostcodeNietGoedException e) {
            e.printStackTrace();
        } catch (TelefoonnummerNietGoedException e) {
            e.printStackTrace();
        } catch (BsnNietGoedException e) {
            e.printStackTrace();
        } catch (IbanNietGoedException e) {
            e.printStackTrace();
        }

        Medewerker medewerker = new Medewerker();
        medewerker.setKantoor(kantoor);
        medewerker.setAchternaam(aanmeldenKantoor.getAchternaam());
        medewerker.setVoornaam(aanmeldenKantoor.getVoornaam());
        medewerker.setEmailadres(aanmeldenKantoor.getEmailadres());
        try {
            medewerker.setIdentificatie(aanmeldenKantoor.getInlognaam());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            medewerker.setHashWachtwoord(aanmeldenKantoor.getNieuwWachtwoord());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        gebruikerService.opslaan(medewerker);

        String token = issueToken(medewerker, httpServletRequest);

        // Return the token on the response
        httpServletResponse.setHeader(AUTHORIZATION, "Bearer " + token);

        return true;
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
