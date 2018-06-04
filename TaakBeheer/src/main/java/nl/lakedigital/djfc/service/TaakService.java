package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Taak;
import nl.lakedigital.djfc.domain.TaakStatus;
import nl.lakedigital.djfc.domain.WijzigingTaak;
import nl.lakedigital.djfc.repository.TaakRepository;
import nl.lakedigital.djfc.repository.WijzigingTaakRepository;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class TaakService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakService.class);

    @Inject
    private TaakRepository taakRepository;
    @Inject
    private WijzigingTaakRepository wijzigingTaakRepository;

    public void wijzig(Taak taak, TaakStatus taakStatus, Long toegewezenAan) {
        wijzigingTaakRepository.opslaan(new WijzigingTaak(taak, taakStatus, toegewezenAan));
    }

    public Long nieuweTaak(LocalDateTime tijdstip, String titel, String omschrijving, Long entiteitId, SoortEntiteit soortEntiteit, Long toegewezenAan) {
        Taak taak = new Taak();
        taak.setTijdstipCreatie(LocalDateTime.now());
        taak.setTijdstip(tijdstip);
        taak.setTitel(titel);
        taak.setOmschrijving(omschrijving);
        taak.setEntiteitId(entiteitId);
        taak.setSoortEntiteit(soortEntiteit);

        taakRepository.opslaan(taak);

        wijzig(taak, TaakStatus.OPEN, toegewezenAan);

        return taak.getId();
    }

}
