package nl.dias.web.authorisatie;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.lakedigital.djfc.commons.json.IngelogdeGebruiker;
import nl.lakedigital.djfc.commons.json.Inloggen;
import nl.lakedigital.djfc.domain.response.InloggenResponse;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@RequestMapping("/authorisatie")
@Controller
public class AuthorisatieController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisatieController.class);

    @Inject
    private AuthorisatieService authorisatieService;
    @Inject
    private GebruikerRepository gebruikerRepository;

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
            LOGGER.trace("gebruiker niet gevonden", e);
            return new InloggenResponse(1L, false);
        } catch (OnjuistWachtwoordException e) {
            LOGGER.trace("Onjuist wachtwoord", e);
            return new InloggenResponse(2L, false);
        }
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
            } else if (gebruiker instanceof Relatie) {
                ingelogdeGebruiker.setKantoor(((Relatie) gebruiker).getKantoor().getNaam());
            }

            return ingelogdeGebruiker;
        }

        //        return Response.status(401).entity(null).build();
        throw new UnauthorizesdAccessException();
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
