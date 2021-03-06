package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.Taak;
import nl.lakedigital.djfc.commons.domain.TaakStatus;
import nl.lakedigital.djfc.commons.domain.WijzigingTaak;
import nl.lakedigital.djfc.repository.TaakRepository;
import nl.lakedigital.djfc.repository.WijzigingTaakRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class TaakService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakService.class);

    @Inject
    private TaakRepository taakRepository;
    @Inject
    private WijzigingTaakRepository wijzigingTaakRepository;

    public Long wijzig(Taak taak, TaakStatus taakStatus, Long toegewezenAan) {
        WijzigingTaak wijzigingTaak = new WijzigingTaak(taak, taakStatus, toegewezenAan);
        if (taakStatus == TaakStatus.AFGEROND) {
            taak.setTijdstipAfgehandeld(LocalDateTime.now());
            taakRepository.opslaan(taak);
        }

        wijzigingTaakRepository.opslaan(wijzigingTaak);

        return wijzigingTaak.getId();
    }

    public Long nieuweTaak(LocalDate tijdstip, String titel, String omschrijving, Long entiteitId, SoortEntiteit soortEntiteit, Long toegewezenAan) {
        Taak taak = new Taak();
        taak.setTijdstipCreatie(LocalDateTime.now());
        taak.setDeadline(tijdstip);
        taak.setTitel(titel);
        taak.setOmschrijving(omschrijving);
        taak.setEntiteitId(entiteitId);
        taak.setSoortEntiteit(soortEntiteit);

        taakRepository.opslaan(taak);

        wijzig(taak, TaakStatus.OPEN, toegewezenAan);

        return taak.getId();
    }

    public Taak lees(Long id) {
        Taak taak = taakRepository.lees(id);

        taak.setWijzigingTaak(wijzigingTaakRepository.allesBijTaak(taak));

        return taak;
    }

    public List<Taak> alleTaken(SoortEntiteit soortEntiteit, Long entiteitId) {
        List<Taak> result = taakRepository.alleTaken(soortEntiteit, entiteitId);

        result.stream().forEach(taak -> taak.setWijzigingTaak(wijzigingTaakRepository.allesBijTaak(taak)));

        return result;
    }

    public List<Taak> allesOpenstaand() {
        List<Taak> result = taakRepository.allesOpenstaand();

        result.stream().forEach(taak -> taak.setWijzigingTaak(wijzigingTaakRepository.allesBijTaak(taak)));

        return result;
    }
}
