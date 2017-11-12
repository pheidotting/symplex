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
import nl.lakedigital.djfc.commons.json.JsonFoutmelding;
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
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
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
    public Long inloggen(@RequestBody Inloggen inloggen, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        try {
            LOGGER.debug("Inloggen");
            authorisatieService.inloggen(inloggen.getIdentificatie().trim(), inloggen.getWachtwoord(), httpServletRequest, httpServletResponse);

            String token = issueToken(inloggen.getIdentificatie(), httpServletRequest);

            // Return the token on the response
            httpServletResponse.setHeader(AUTHORIZATION, "Bearer " + token);

        } catch (NietGevondenException e) {
            LOGGER.trace("gebruiker niet gevonden", e);
            return 1L;
        } catch (OnjuistWachtwoordException e) {
            LOGGER.trace("Onjuist wachtwoord", e);
            return 2L;
        }
        return 0L;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ingelogdeGebruiker", produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public IngelogdeGebruiker getIngelogdeGebruiker(@RequestParam String userid) {
        LOGGER.debug("Ophalen ingelogde gebruiker '{}'",userid);

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

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public class GebruikerNietGevondenOfWachtwoordOnjuisException extends RuntimeException {
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/isIngelogd", produces = MediaType.APPLICATION_JSON)
//    @ResponseBody
//    public Response isIngelogd(HttpServletRequest httpServletRequest) {
//        LOGGER.debug("is gebruiker ingelogd");
//
//        Gebruiker gebruiker = getGebruiker(httpServletRequest);
//
//        if (gebruiker == null) {
//            return Response.status(401).entity(false).build();
//        } else {
//            return Response.status(200).entity(true).build();
//        }
//    }

    private Gebruiker getGebruiker(String userid) {
        Gebruiker gebruiker=null;
//        String sessie = null;
//        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
//            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
//        }
//
//        Gebruiker gebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr());
//
//        if (gebruiker == null) {
//            String sessieHeader = httpServletRequest.getHeader("sessieCode");

            try {
             gebruiker=authorisatieService.zoekOpIdentificatie(userid);
//                gebruiker = gebruikerRepository.zoekOpSessieEnIpadres(sessieHeader, "0:0:0:0:0:0:0:1");
            } catch (NietGevondenException nge) {
                LOGGER.trace("Gebruiker dus niet gevonden", nge);
            }
//        }

        return gebruiker;
    }

    private String issueToken(String login, HttpServletRequest httpServletRequest) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512("secret");

            token = JWT.create()
                    .withSubject(login)
                    .withIssuer(httpServletRequest.getContextPath())
                    .withIssuedAt(new Date())
                    .withExpiresAt(LocalDateTime.now().plusHours(1).toDate())
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Fout bij aanmaken JWT",e);
        }

        return token;
    }
}
