package nl.dias.service;

import nl.dias.domein.Gebruiker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Service
public class WachtwoordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WachtwoordService.class);

    public String hashWachtwoord(String wachtwoord, Gebruiker gebruiker) {
        String ww = null;
        try {
            ww = wachtwoord + gebruiker.getSalt();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            LOGGER.error("{}", e);
        }

        return ww;
    }
}
