package nl.dias.service;

import nl.dias.domein.Beheerder;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import nl.lakedigital.loginsystem.exception.OnjuistWachtwoordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service
public class AuthorisatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorisatieService.class);

    public final static String COOKIE_DOMEIN_CODE = "lakedigitaladministratie";

    @Inject
    private GebruikerService gebruikerService;

    public Gebruiker inloggen(String identificatie, String wachtwoord, HttpServletRequest request, HttpServletResponse response) throws OnjuistWachtwoordException, NietGevondenException {
        boolean uitZabbix = "true".equals(request.getHeader("Zabbix"));

        if (!uitZabbix) {
            LOGGER.debug("Inloggen met {}", identificatie);
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
            LOGGER.debug("Ingevoerd wachtwoord    {}", inloggendeGebruiker.getWachtwoord());
            LOGGER.debug("Wachtwoord uit database {}", gebruikerUitDatabase.getWachtwoord());
        }

        if (!gebruikerUitDatabase.getWachtwoord().equals(inloggendeGebruiker.getWachtwoord())) {
            throw new OnjuistWachtwoordException();
        }

        return gebruikerUitDatabase;
    }

    public Gebruiker zoekOpIdentificatie(String identificatie) throws NietGevondenException {
        return gebruikerService.zoekOpIdentificatie(identificatie);
    }
}
