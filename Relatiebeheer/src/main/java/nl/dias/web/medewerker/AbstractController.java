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

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    @Inject
    protected AuthorisatieService authorisatieService;
    @Inject
    protected GebruikerService gebruikerService;

    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
        SessieHolder.get().setIngelogdeGebruiker(getIngelogdeGebruiker(httpServletRequest).getId());
        SessieHolder.get().setTrackAndTraceId(getTrackAndTraceId(httpServletRequest));
    }

    protected Gebruiker getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        Long ingelogdeGebruiker = null;
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
        DecodedJWT decodedJWT=JWT.decode(token);

        try {
            return gebruikerService.zoekOpIdentificatie(decodedJWT.getSubject());
        }catch (NietGevondenException nge){
            LOGGER.error("Net gevonden : {}",decodedJWT.getSubject());
            Gebruiker gebruiker=new Relatie();
            gebruiker.setId(0L);
            return gebruiker;
        }
    }

    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        String tati = httpServletRequest.getHeader("trackAndTraceId");
        LOGGER.debug("DJFC Track And Trace Id : {}", tati);

        return tati;
    }
}
