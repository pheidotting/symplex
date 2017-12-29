package nl.dias.service;

import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.SchadeRepository;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
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

    public Schade zoekOpSchadeNummerMaatschappij(String schadeNummer) {
        return schadeRepository.zoekOpSchadeNummerMaatschappij(schadeNummer);
    }

    public void verwijder(Long id) {
        Schade schade = lees(id);
        schadeRepository.verwijder(schade);
    }

    public void opslaan(Schade schade) {
        schadeRepository.opslaan(schade);
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
            try {
                Identificatie identificatie = identificatieClient.zoekIdentificatieCode(polisId);

                schade.setPolis(identificatie.getEntiteitId());
            } catch (Exception e) {
                LOGGER.error("Fout bij ophalen Identificatie {}", e);
                throw e;
            }
        }

        LOGGER.debug("Schade opslaan");
        schadeRepository.opslaan(schade);
    }

    public List<Schade> alleSchadesBijRelatie(Long relatie) {
        List<Schade> schades = new ArrayList<>();

        LOGGER.debug("Schades zoeken bij relatie met id {}", relatie);
        List<Polis> polissen = polisService.allePolissenBijRelatie(relatie);

        LOGGER.debug("Gevonden Polissen : ");
        for (Polis polis : polissen) {
            LOGGER.debug("Polis : {} - {} - {}", polis.getId(), polis.getPolisNummer(), polis.getKenmerk());
            List<Schade> s = schadeRepository.allesBijPolis(polis.getId());
            schades.addAll(s);
            s.stream().forEach(ss -> LOGGER.debug("Schade : {} - {} - {}", ss.getId(), ss.getSchadeNummerMaatschappij(), ss.getOmschrijving()));
        }

        return schades;
    }

    public List<Schade> alleSchadesBijPolis(Long polis) {
        return schadeRepository.alleSchadesBijPolis(polis);
    }

    public List<Schade> alleSchadesBijBedrijf(Long bedrijf) {
        List<Schade> schades = new ArrayList<>();

        List<Polis> polissen = polisService.allePolissenBijBedrijf(bedrijf);

        for (Polis polis : polissen) {
            schades.addAll(schadeRepository.allesBijPolis(polis.getId()));
        }

        return schades;
    }

    public void verwijder(List<Schade> schades) {
        schadeRepository.verwijder(schades);
    }

    public Schade lees(Long id) {
        LOGGER.debug("{}", id);
        Schade schade = schadeRepository.lees(id);
        LOGGER.debug(ReflectionToStringBuilder.toString(schade));
        return schade;
    }
}
