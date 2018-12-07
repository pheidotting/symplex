package nl.dias.web.authorisatie;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.messaging.sender.WachtwoordVergetenRequestSender;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.LoginService;
import nl.lakedigital.djfc.commons.domain.response.InloggenResponse;
import nl.lakedigital.djfc.commons.json.IngelogdeGebruiker;
import nl.lakedigital.djfc.commons.json.Inloggen;
import nl.lakedigital.djfc.metrics.MetricsService;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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
    @Inject
    private LoginService loginService;
    @Inject
    private WachtwoordVergetenRequestSender wachtwoordVergetenRequestSender;

    @RequestMapping(method = RequestMethod.POST, value = "/inloggen", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public InloggenResponse inloggen(@RequestBody Inloggen inloggen, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        Gebruiker gebruiker;
        String token;
        try {
            LOGGER.debug("Inloggen");
            gebruiker = authorisatieService.inloggen(inloggen.getIdentificatie().trim(), inloggen.getWachtwoord(), httpServletRequest, httpServletResponse);

            token = issueToken(gebruiker, httpServletRequest);

            // Return the token on the response
            httpServletResponse.setHeader(AUTHORIZATION, "Bearer " + token);

        } catch (NietGevondenException e) {
            metricsService.addMetric("inloggenOnbekendeGebruiker", AuthorisatieController.class, null, null);
            LOGGER.trace("gebruiker niet gevonden", e);
            return new InloggenResponse(1L, false);
        } catch (OnjuistWachtwoordException e) {
            metricsService.addMetric("inloggenOnjuistWachtwoord", AuthorisatieController.class, null, null);
            LOGGER.trace("Onjuist wachtwoord", e);
            return new InloggenResponse(2L, false);
        } catch (TeveelFouteInlogPogingenException e) {
            metricsService.addMetric("inloggenTeveelFoutieveInlogPogingen", AuthorisatieController.class, null, null);
            LOGGER.trace("Onjuist wachtwoord", e);
            return new InloggenResponse(3L, false);
        }

        loginService.nieuwToken(gebruiker.getId(), token);

        metricsService.addMetric("inloggen", AuthorisatieController.class, null, null);
        LOGGER.debug(ReflectionToStringBuilder.toString(new InloggenResponse(0L, gebruiker.isMoetWachtwoordUpdaten())));
        return new InloggenResponse(0L, gebruiker.isMoetWachtwoordUpdaten());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ingelogdeGebruiker", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public IngelogdeGebruiker getIngelogdeGebruiker(@RequestParam String userid, HttpServletRequest httpServletRequest) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && !"".equals(token)) {
            token = token.substring("Bearer".length()).trim();
        }

        LOGGER.info("Ophalen ingelogde gebruiker '{}', token : {}", userid, token);

        IngelogdeGebruiker ingelogdeGebruiker = new IngelogdeGebruiker();

        Gebruiker gebruiker;
        try {
            gebruiker = getGebruiker(userid);
        } catch (NietGevondenException e) {

            return ingelogdeGebruiker;
        }

        if (gebruiker != null) {
            Algorithm algorithm = Algorithm.HMAC512(gebruiker.getSalt());
            JWTVerifier verifier = JWT.require(algorithm).withIssuer((httpServletRequest).getRemoteUser()).build();
            try {
                LOGGER.trace("Verify token");
                verifier.verify(token);
            } catch (JWTVerificationException e) {
                LOGGER.debug("Token niet meer valide");

                return ingelogdeGebruiker;
            }

            LOGGER.debug("Token is valide");
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
        metricsService.addMetric("wachtwoordvergeten", AuthorisatieController.class, null, null);
        LOGGER.info("Wachtwoord vergeten");
        try {
            Gebruiker gebruiker = gebruikerRepository.zoekOpIdentificatie(identificatie);

            LOGGER.info("Nieuw wachtwoord voor {}", identificatie);
            if (gebruiker != null) {
                LOGGER.info("Gebruikerid hierbij gevonden {}, met mailadres {}", gebruiker.getId(), gebruiker.getEmailadres());
            }

            String nieuwWachtwoord = UUID.randomUUID().toString().replace("-", "");

            if (gebruiker != null && gebruiker.getEmailadres() != null && !"".equals(gebruiker.getEmailadres())) {
                gebruiker.setHashWachtwoord(nieuwWachtwoord);
                gebruiker.setMoetWachtwoordUpdaten(true);
                gebruikerRepository.opslaan(gebruiker);

                wachtwoordVergetenRequestSender.send(gebruiker, nieuwWachtwoord);
            }

        } catch (NietGevondenException | UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("Fout opgetreden bij het aanmaken van een nieuw wachtwoord voor {} : {}", identificatie, e);
        }
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public class UnauthorizesdAccessException extends RuntimeException {

    }

    private Gebruiker getGebruiker(String userid) throws NietGevondenException {
        return authorisatieService.zoekOpIdentificatie(userid);
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
