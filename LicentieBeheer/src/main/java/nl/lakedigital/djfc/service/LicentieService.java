package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.Kantoor;
import nl.lakedigital.djfc.domain.Licentie;
import nl.lakedigital.djfc.domain.LicentieStatus;
import nl.lakedigital.djfc.domain.Trial;
import nl.lakedigital.djfc.repository.LicentieRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class LicentieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieService.class);

    private final static int AANTAL_DAGEN_TRIAL = 30;

    @Inject
    private LicentieRepository licentieRepository;

    public static LicentieStatus bepaalLicentieStatus(Licentie licentie) {
        if (licentie instanceof Trial && licentie.getStartDatum().plusDays(licentie.getAantalDagen()).isAfter(LocalDate.now())) {
            return LicentieStatus.TRIAL;
        } else if (licentie instanceof Trial && licentie.getStartDatum().plusDays(licentie.getAantalDagen()).isBefore(LocalDate.now())) {
            return LicentieStatus.GEBLOKKEERD;
        }
        LOGGER.error("Licentiestatus kon niet bepaald worden voor licentie met id {}", licentie.getId());
        return null;
    }


    public void maakTrialAan(Kantoor kantoor) {
        Trial trial = new Trial();
        trial.setKantoor(kantoor.getId());
        trial.setStartDatum(LocalDate.now());
        trial.setAantalDagen(AANTAL_DAGEN_TRIAL);

        licentieRepository.opslaan(trial);
    }

}
