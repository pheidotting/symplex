package nl.dias.web.medewerker;

import inloggen.SessieHolder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Sessie;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractController {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    @Inject
    protected AuthorisatieService authorisatieService;
    @Inject
    protected GebruikerService gebruikerService;

    protected void zetSessieWaarden(HttpServletRequest httpServletRequest) {
        SessieHolder.get().setIngelogdeGebruiker(getIngelogdeGebruiker(httpServletRequest));
        SessieHolder.get().setTrackAndTraceId(getTrackAndTraceId(httpServletRequest));
    }

    protected Long getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        String sessie = null;
        String id = null;
        if (httpServletRequest.getSession().getAttribute("sessie") != null && !"".equals(httpServletRequest.getSession().getAttribute("sessie"))) {
            sessie = httpServletRequest.getSession().getAttribute("sessie").toString();
        }
        if (httpServletRequest.getSession().getAttribute("id") != null && !"".equals(httpServletRequest.getSession().getAttribute("id"))) {
            id = httpServletRequest.getSession().getAttribute("id").toString();
        }

        LOGGER.debug("sessie {}", sessie);

        Long ingelogdeGebruiker = null;
        if (sessie != null) {
            ingelogdeGebruiker = authorisatieService.getIngelogdeGebruiker(httpServletRequest, sessie, httpServletRequest.getRemoteAddr()).getId();

            LOGGER.debug("DJFC Ingelogde Gebruiker opgehaald : {}", ingelogdeGebruiker);
        } else if (id != null) {
            //igv ITest
            ingelogdeGebruiker = Long.valueOf(id);
            Gebruiker gebruikerUitDatabase = gebruikerService.lees(ingelogdeGebruiker);
            // Gebruiker dus gevonden en wachtwoord dus juist..
            LOGGER.debug("Aanmaken nieuwe sessie");
            Sessie sessie1 = new Sessie();
            sessie1.setBrowser(httpServletRequest.getHeader("user-agent"));
            sessie1.setIpadres(httpServletRequest.getRemoteAddr());
            sessie1.setDatumLaatstGebruikt(new Date());
            sessie1.setGebruiker(gebruikerUitDatabase);
            sessie1.setSessie(UUID.randomUUID().toString());

            gebruikerService.opslaan(sessie1);

            gebruikerUitDatabase.getSessies().add(sessie1);

            gebruikerService.opslaan(gebruikerUitDatabase);

            LOGGER.debug("sessie id " + sessie1.getSessie() + " in de request plaatsen");
            httpServletRequest.getSession().setAttribute("sessie", sessie1.getSessie());
        }


        return ingelogdeGebruiker;

    }

    protected String getTrackAndTraceId(HttpServletRequest httpServletRequest) {
        String tati = httpServletRequest.getHeader("trackAndTraceId");
        LOGGER.debug("DJFC Track And Trace Id : {}", tati);

        return tati;
    }
}
