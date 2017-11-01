package nl.dias.web.filter;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Sessie;
import nl.dias.repository.GebruikerRepository;
import nl.dias.service.AuthorisatieService;
import nl.dias.service.GebruikerService;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Date;


@Provider
public class AuthorisatieFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisatieFilter.class);

    private GebruikerService gebruikerService = null;
    private GebruikerRepository gebruikerRepository = null;
    private AuthorisatieService authorisatieService = null;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("In AuthorisatieFilter");
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getSession().getAttribute("sessie") != null || req.getHeader("sessie") != null) {
            LOGGER.debug("Blijkbaar heeft een vorig filter het al goed bevonden, doorgaan");

            if (req.getSession().getAttribute("sessie") == null) {
                req.getSession().setAttribute("sessie", req.getHeader("sessie"));
            }

            opruimen();
            chain.doFilter(request, response);
        } else {
            init();

            Gebruiker gebruiker = null;
            Sessie sessie;

            if (gebruiker == null) {
                final String sessieId = (String) req.getSession().getAttribute("sessie");
                final String ipAdres = req.getRemoteAddr();

                if (sessieId != null && sessieId.length() > 0) {
                    LOGGER.debug("Sessie met id " + sessieId + " gevonden in het request");
                    try {
                        init();
                        gebruiker = gebruikerRepository.zoekOpSessieEnIpadres(sessieId, ipAdres);
                        LOGGER.debug("Gebruiker met id " + gebruiker.getId() + " opgehaald.");
                    } catch (NietGevondenException e) {
                        LOGGER.debug("Niet gevonden", e);
                    }
                } else {
                    LOGGER.debug("Geen sessieId gevonden in het request");
                }

                if (gebruiker != null) {
                    LOGGER.debug("Sessie ophalen van de ingelogde gebruiker");
                    init();
                    sessie = gebruikerService.zoekSessieOp(sessieId, ipAdres, gebruiker.getSessies());
                    sessie.setDatumLaatstGebruikt(new Date());
                    LOGGER.debug("Sessie weer opslaan met bijgewerkte datum");
                    init();
                    gebruikerRepository.opslaan(sessie);

                    LOGGER.debug("Verder filteren");

                    opruimen();

                    chain.doFilter(request, response);
                } else {
                    opruimen();

                    LOGGER.debug("Stuur een UNAUTHORIZED");
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
    }

    private void init() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        gebruikerRepository = (GebruikerRepository) applicationContext.getBean("gebruikerRepository");
        gebruikerService = (GebruikerService) applicationContext.getBean("gebruikerService");
        authorisatieService = (AuthorisatieService) applicationContext.getBean("authorisatieService");
    }

    private void opruimen() {
        gebruikerService = null;
        gebruikerRepository = null;
        authorisatieService = null;
    }

    @Override
    public void destroy() {
        LOGGER.debug("destroy filter");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        LOGGER.debug("init filter");
    }

    public void setGebruikerService(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    public void setGebruikerRepository(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    public void setAuthorisatieService(AuthorisatieService authorisatieService) {
        this.authorisatieService = authorisatieService;
    }
}
