package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.Kantoor;
import nl.lakedigital.djfc.commons.domain.*;
import nl.lakedigital.djfc.repository.LicentieRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class LicentieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieService.class);

    private static final int AANTAL_DAGEN_TRIAL = 30;

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
        LOGGER.debug("ID {}", trial.getId());
    }

    public void nieuweLicentie(String soort, Long kantoor) {
        Licentie licentie;
        switch (soort) {
            case "brons":
                licentie = new Brons();
                break;
            case "zilver":
                licentie = new Zilver();
                break;
            case "goud":
                licentie = new Goud();
                break;
            case "administratiekantoor":
            default:
                licentie = new AdministratieKantoor();
                break;
        }

        licentie.setKantoor(kantoor);
        licentieRepository.opslaan(licentie);
    }

    public Licentie actieveLicentie(Long kantoorId) {
        List<Licentie> licenties = licentieRepository.alleLicenties(kantoorId);

        licenties.sort((o1, o2) -> o2.getStartDatum().compareTo(o1.getStartDatum()));
        if (licenties.isEmpty()) {
            return null;
        }
        return licenties.get(0);
    }

    public LocalDate actieveLicentie(Licentie licentie) {
        if (licentie instanceof LifetimeLicense) {
            return new LocalDate(2999, 12, 31);
        }
        return licentie == null ? null : licentie.getStartDatum().plusDays(licentie.getAantalDagen());
    }

    public Double bepaalPrijs(String licentieType) {
        Double prijs;
        switch (licentieType) {
            case "brons":
                prijs = 5.00;
                break;
            case "zilver":
                prijs = 10.00;
                break;
            case "goud":
                prijs = 20.00;
                break;
            case "administratiekantoor":
            default:
                prijs = 15.00;
                break;
        }

        return prijs;
    }
}
