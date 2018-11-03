package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.*;
import nl.lakedigital.djfc.repository.SchadeRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchadeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeService.class);

    @Inject
    private SchadeRepository schadeRepository;
    @Inject
    private PolisService polisService;
    @Inject
    private IdentificatieClient identificatieClient;

    public List<SoortSchade> soortenSchade() {
        return schadeRepository.soortenSchade();
    }

    public List<SoortSchade> soortenSchade(String omschrijving) {
        return schadeRepository.soortenSchade(omschrijving);
    }

    public StatusSchade getStatussen(String status) {
        return schadeRepository.getStatussen(status);
    }

    public List<StatusSchade> getStatussen() {
        return schadeRepository.getStatussen();
    }

    public List<Schade> zoekOpSchadeNummerMaatschappij(String schadeNummer) {
        return schadeRepository.zoekOpSchadeNummerMaatschappij(schadeNummer);
    }

    public void verwijder(Long id) {
        Schade schade = lees(id);
        schadeRepository.verwijder(schade);
    }

    public void opslaan(Schade schade) {
        schadeRepository.opslaan(schade);
    }

    public void opslaan(List<Schade> schades) {
        schadeRepository.opslaan(schades);
    }

    public void opslaan(Schade schadeIn, String soortSchade, String polisId, String statusSchade) {
        LOGGER.debug("Opslaan schade");
        LOGGER.debug("{}", schadeIn);

        Schade schade = schadeIn;

        LOGGER.debug("Soort schade opzoeken " + soortSchade);
        List<SoortSchade> soorten = schadeRepository.soortenSchade(soortSchade);

        if (StringUtils.isNotEmpty(statusSchade) && !"Kies een status uit de lijst..".equals(statusSchade)) {
            LOGGER.debug("Status schade opzoeken " + statusSchade);
            StatusSchade status = schadeRepository.getStatussen(statusSchade);

            LOGGER.debug("Status schade gevonden: {}", ReflectionToStringBuilder.toString(status));

            schade.setStatusSchade(status);
        }

        if (!soorten.isEmpty()) {
            LOGGER.debug("Soort schade gevonden in database (" + soorten.size() + ")");
            schade.setSoortSchade(soorten.get(0));
        } else {
            LOGGER.debug("Geen soort schade gevonden in database, tekst dus opslaan");
            schade.setSoortSchadeOngedefinieerd(soortSchade);
        }

        if (polisId != null && !"Kies een polis uit de lijst..".equals(polisId)) {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polisId);

            schade.setPolis(Long.valueOf(identificatie.getEntiteitId()));
        }

        LOGGER.debug("Schade opslaan");
        schadeRepository.opslaan(schade);
    }

    public List<Schade> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        List<Pakket> pakketten = polisService.alles(soortEntiteit, entiteitId);
        List<Schade> result = new ArrayList<>();

        for (Pakket pakket : pakketten) {
            for (Polis polis : pakket.getPolissen()) {
                result.addAll(schadeRepository.alleSchades(polis.getId()));
            }
        }

        return result;
    }

    public List<Schade> allesBijPolis(Polis polis) {
        return schadeRepository.alleSchades(polis.getId());
    }

    public Schade lees(Long id) {
        LOGGER.debug("Schade zoeken met id {}", id);
        Schade schade = schadeRepository.lees(id);
        LOGGER.debug(ReflectionToStringBuilder.toString(schade));
        return schade;
    }
}
