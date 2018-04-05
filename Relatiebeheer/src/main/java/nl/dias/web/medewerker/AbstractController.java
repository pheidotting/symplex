package nl.dias.web.medewerker;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import inloggen.SessieHolder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    @Inject
    protected AuthorisatieService authorisatieService;
    @Inject
    protected GebruikerService gebruikerService;

    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
        String trackAndTraceId = getTrackAndTraceId(httpServletRequest);
        Gebruiker gebruiker = getIngelogdeGebruiker(httpServletRequest);
        if (gebruiker != null) {
            MDC.put("ingelogdeGebruiker", getIngelogdeGebruiker(httpServletRequest).getId() + "");
            MDC.put("ingelogdeGebruikerOpgemaakt", maakOp(getIngelogdeGebruiker(httpServletRequest)));
        }
        String url = getUrl(httpServletRequest);
        if (url != null) {
            MDC.put("url", url);
        }
        if (trackAndTraceId != null) {
            MDC.put("trackAndTraceId", trackAndTraceId);
        }

        if (gebruiker != null) {
            SessieHolder.get().setIngelogdeGebruiker(gebruiker.getId());
        }
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

    private String maakOp(Gebruiker gebruiker) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(gebruiker.getVoornaam());
        stringBuffer.append(" ");
        if (gebruiker.getTussenvoegsel() != null && !"".equals(gebruiker.getTussenvoegsel())) {
            stringBuffer.append(gebruiker.getTussenvoegsel());
            stringBuffer.append(" ");
        }
        stringBuffer.append(gebruiker.getAchternaam());
        stringBuffer.append(" (");
        stringBuffer.append(gebruiker.getId());
        stringBuffer.append(")");

        if (gebruiker instanceof Medewerker) {
            stringBuffer.append(", ");
            stringBuffer.append(((Medewerker) gebruiker).getKantoor().getNaam());
            stringBuffer.append(" (");
            stringBuffer.append(((Medewerker) gebruiker).getKantoor().getId());
            stringBuffer.append(")");
        }

        return stringBuffer.toString();
    }

    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("trackAndTraceId");
    }

    protected String getUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("url");
    }
}
