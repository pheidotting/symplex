package nl.dias.service;

import nl.dias.domein.*;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorisatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisatieService.class);

    public final static String COOKIE_DOMEIN_CODE = "lakedigitaladministratie";

    @Inject
    private GebruikerService gebruikerService;

    public void inloggen(String identificatie, String wachtwoord, HttpServletRequest request, HttpServletResponse response) throws OnjuistWachtwoordException, NietGevondenException {
        boolean uitZabbix = "true".equals(request.getHeader("Zabbix"));

        if (!uitZabbix) {
            LOGGER.debug("Inloggen met {}" , identificatie);
        }
        Gebruiker gebruikerUitDatabase = gebruikerService.zoekOpIdentificatie(identificatie);
        Gebruiker inloggendeGebruiker = null;
        if (gebruikerUitDatabase instanceof Medewerker) {
            if (!uitZabbix) {
                LOGGER.debug("Gebruiker is een Medewerker");
            }
            inloggendeGebruiker = new Medewerker();
        } else if (gebruikerUitDatabase instanceof Relatie) {
            LOGGER.debug("Gebruiker is een Relatie");
            inloggendeGebruiker = new Relatie();
        } else if (gebruikerUitDatabase instanceof Beheerder) {
            LOGGER.debug("Gebruiker is een Beheerder");
            inloggendeGebruiker = new Beheerder();
        }

        // Eigenlijk is dit alleen om Sonar tevreden te houden
        if (inloggendeGebruiker == null) {
            throw new IllegalArgumentException();
        }

        try {
            inloggendeGebruiker.setIdentificatie(identificatie);
            inloggendeGebruiker.setHashWachtwoord(wachtwoord);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("Fout opgetreden", e);
        }

        if (!uitZabbix) {
            LOGGER.debug("Ingevoerd wachtwoord    {}",  inloggendeGebruiker.getWachtwoord());
            LOGGER.debug("Wachtwoord uit database {}" , gebruikerUitDatabase.getWachtwoord());
        }

        if (!gebruikerUitDatabase.getWachtwoord().equals(inloggendeGebruiker.getWachtwoord())) {
            throw new OnjuistWachtwoordException();
        }

        // Gebruiker dus gevonden en wachtwoord dus juist..
//        if (!uitZabbix) {
//            LOGGER.debug("Aanmaken nieuwe sessie");
//        }
//        String nieuweSessie = UUID.randomUUID().toString();
//        Sessie sessie = new Sessie();
//        sessie.setBrowser(request.getHeader("user-agent"));
//        sessie.setIpadres(request.getRemoteAddr());
//        sessie.setDatumLaatstGebruikt(new Date());
//        sessie.setGebruiker(gebruikerUitDatabase);
//        sessie.setSessie(nieuweSessie);
//
//        gebruikerService.opslaan(sessie);
//
//        gebruikerUitDatabase.getSessies().add(sessie);
//
//        gebruikerService.opslaan(gebruikerUitDatabase);
//
//        if (!uitZabbix) {
//            LOGGER.debug("sessie id {} in de request en response plaatsen", nieuweSessie);
//        }
//        request.getSession().setAttribute("sessie", nieuweSessie);
//
//        response.setHeader("sessie", nieuweSessie);
    }

    public Gebruiker zoekOpIdentificatie(String identificatie) throws NietGevondenException {
        return gebruikerService.zoekOpIdentificatie(identificatie);
    }

    public Gebruiker getIngelogdeGebruiker(HttpServletRequest request, String sessieId, String ipadres) {

        Gebruiker gebruiker = null;
        try {
            gebruiker = gebruikerService.zoekOpSessieEnIpAdres(sessieId, ipadres);

            if (gebruiker != null) {
                LOGGER.debug("Opzoeken Sessie met id " + sessieId + " en ipadres " + ipadres);
                Sessie sessie = gebruikerService.zoekSessieOp(sessieId, ipadres, gebruiker.getSessies());
                if (sessie != null) {
                    sessie.setDatumLaatstGebruikt(new Date());

                    gebruikerService.opslaan(gebruiker);

                    gebruikerService.verwijderVerlopenSessies(gebruiker);
                }
            }
        } catch (NietGevondenException e) {
            LOGGER.trace("Geen ingelogde gebruiker gevonden", e);
        }

        return gebruiker;
    }
}
