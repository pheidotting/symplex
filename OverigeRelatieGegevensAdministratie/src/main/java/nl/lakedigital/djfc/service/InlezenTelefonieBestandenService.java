package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.TelefonieBestand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class InlezenTelefonieBestandenService implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(InlezenTelefonieBestandenService.class);
    private TelefonieBestandService telefonieBestandService;

    public InlezenTelefonieBestandenService(TelefonieBestandService telefonieBestandService) {
        this.telefonieBestandService = telefonieBestandService;
    }

    public InlezenTelefonieBestandenService() {
        //Omdat SonarQube anders zeurt
    }

    @Override
    public void run() {
        List<String> bestanden = telefonieBestandService.inlezenBestanden();

        final List<TelefonieBestand> telefonieBestanden = telefonieBestandService.alleTelefonieBestanden();

        List<TelefonieBestand> nieuweBestanden = bestanden.stream().filter(file -> {
            TelefonieBestand telefonieBestand = telefonieBestandService.maakTelefonieBestand(file);

            if (telefonieBestand == null) {
                return false;
            }

            return !telefonieBestanden.contains(telefonieBestand);
        }).map(file -> telefonieBestandService.maakTelefonieBestand(file)).collect(Collectors.toList());

        if (!nieuweBestanden.isEmpty()) {
            LOGGER.info("Opslaan {} nieuwe telefoniebestand(en)", nieuweBestanden.size());
            for (TelefonieBestand telefonieBestand : nieuweBestanden) {
                LOGGER.debug(telefonieBestand.getBestandsnaam());
            }

            telefonieBestandService.opslaan(nieuweBestanden);
        }
    }
}
