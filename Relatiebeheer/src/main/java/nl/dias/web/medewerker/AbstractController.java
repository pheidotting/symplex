package nl.dias.web.medewerker;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import inloggen.SessieHolder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

public abstract class AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    @Inject
    protected AuthorisatieService authorisatieService;
    @Inject
    protected GebruikerService gebruikerService;

    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
        String trackAndTraceId = getTrackAndTraceId(httpServletRequest);
        MDC.put("ingelogdeGebruiker", getIngelogdeGebruiker(httpServletRequest).getId() + "");
        if (trackAndTraceId != null) {
            MDC.put("trackAndTraceId", trackAndTraceId);
        }

        SessieHolder.get().setIngelogdeGebruiker(getIngelogdeGebruiker(httpServletRequest).getId());
        SessieHolder.get().setTrackAndTraceId(getTrackAndTraceId(httpServletRequest));
    }

    protected Gebruiker getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = null;
        if (authorizationHeader != null) {
            try {
                token = authorizationHeader.substring("Bearer".length()).trim();
            } catch (StringIndexOutOfBoundsException e) {
                LOGGER.trace("Niks aan de hand", e);
            }
        }
        if (token != null) {
            DecodedJWT decodedJWT = JWT.decode(token);

            try {
                return gebruikerService.zoekOpIdentificatie(decodedJWT.getSubject());
            } catch (NietGevondenException nge) {
                LOGGER.error("Net gevonden : {}", decodedJWT.getSubject());
                LOGGER.trace("Bijbehorende error {}", nge);
                Gebruiker gebruiker = new Relatie();
                gebruiker.setId(0L);
                return gebruiker;
            }
        }
        return null;
    }

    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        String tati = httpServletRequest.getHeader("trackAndTraceId");
        LOGGER.debug("DJFC Track And Trace Id : {}", tati);

        return tati;
    }
}
