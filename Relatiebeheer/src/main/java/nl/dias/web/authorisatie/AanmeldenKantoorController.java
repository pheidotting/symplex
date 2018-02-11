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
import nl.dias.service.MetricsService;
import nl.dias.web.medewerker.AbstractController;
import nl.lakedigital.djfc.domain.response.AanmeldenKantoor;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.function.Consumer;

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
    @Inject
    private LoginService loginService;

    @RequestMapping(method = RequestMethod.POST, value = "/aanmeldenKantoor")
    @ResponseBody
    public boolean opslaan(@RequestBody AanmeldenKantoor aanmeldenKantoor, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        LOGGER.info("Aanmelden nieuw kantoor");

        metricsService.addMetric("kantoorAanmelden", AanmeldenKantoorController.class, null, null);

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
            medewerker.setHashWachtwoord(aanmeldenKantoor.getNieuwWachtwoord());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("{}", e);
            throw e;
        }
        gebruikerService.opslaan(medewerker);

        // Return the token on the response
        httpServletResponse.setHeader(AUTHORIZATION, "Bearer " + issueToken(medewerker, httpServletRequest));

        return true;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/komtAfkortingAlVoor/{afkorting}", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public boolean komtAfkortingAlVoor(@PathVariable("afkorting") String afkorting, HttpServletRequest httpServletRequest) {
        zetSessieWaarden(httpServletRequest);

        kantoorRepository.zoekOpAfkorting(afkorting).stream().forEach(new Consumer<Kantoor>() {
            @Override
            public void accept(Kantoor kantoor) {
                LOGGER.debug("{} {}", kantoor.getId(), kantoor.getNaam());
            }
        });

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
