package nl.dias.service;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.dias.messaging.sender.VerwijderEntiteitenRequestSender;
import nl.dias.repository.BedrijfRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.client.polisadministratie.SchadeClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class BedrijfService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfService.class);

    @Inject
    private BedrijfRepository bedrijfRepository;
    @Inject
    private VerwijderEntiteitenRequestSender verwijderEntiteitenRequestSender;
    @Inject
    private SchadeService schadeService;
    @Inject
    private PolisRepository polisRepository;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private PolisClient polisClient;
    @Inject
    private AdresClient adresClient;
    @Inject
    private SchadeClient schadeClient;

    public void opslaan(Bedrijf bedrijf) {
        bedrijfRepository.opslaan(bedrijf);
    }

    public Bedrijf lees(Long id) {
        return bedrijfRepository.lees(id);
    }

    public Bedrijf zoekBedrijf(String identificatieCode) {
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(identificatieCode);

        Long bedrijfId = null;

        switch (identificatie.getSoortEntiteit()) {
            case "BEDRIJF":
                bedrijfId = identificatie.getEntiteitId();
                break;
            case "POLIS":
                bedrijfId = pakBedrijfBijPolis(identificatie.getEntiteitId());
                break;
            case "ADRES":
                bedrijfId = pakBedrijfBijAdres(identificatie.getEntiteitId());
                break;
            case "SCHADE":
                bedrijfId = pakBedrijfBijSchade(identificatie.getEntiteitId());
                break;
        }

        return (Bedrijf) bedrijfRepository.lees(bedrijfId);
    }

    private Long pakBedrijfBijPolis(Long polisId) {
        JsonPolis polis = polisClient.lees(String.valueOf(polisId));

        LOGGER.debug("Polis ({}) gevonden : {}", polisId, ReflectionToStringBuilder.toString(polis));

        return polis.getEntiteitId();
    }

    private Long pakBedrijfBijAdres(Long adresId) {
        JsonAdres adres = adresClient.lees(adresId);

        return adres.getEntiteitId();
    }

    private Long pakBedrijfBijSchade(Long schadeId) {
        JsonSchade schade = schadeClient.lees(String.valueOf(schadeId));

        LOGGER.debug("Schade ({}) gevonden : {}", schadeId, ReflectionToStringBuilder.toString(schade));

        return pakBedrijfBijPolis(Long.valueOf(schade.getPolis()));
    }

    public void verwijder(Long id) {
        LOGGER.debug("Verwijderen Bedrijf met id {}", id);

        Bedrijf bedrijf = lees(id);

        if (bedrijf != null) {
            LOGGER.debug("Verwijderen : {}", ReflectionToStringBuilder.toString(bedrijf, ToStringStyle.SHORT_PREFIX_STYLE));
            List<Schade> schades = schadeService.alleSchadesBijBedrijf(bedrijf.getId());
            schadeService.verwijder(schades);

            List<Polis> polises = polisRepository.allePolissenBijBedrijf(bedrijf.getId());
            polisRepository.verwijder(polises);

            bedrijfRepository.verwijder(bedrijf);

            LOGGER.debug("Bericht naar OGA");
            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
            soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.BEDRIJF);
            soortEntiteitEnEntiteitId.setEntiteitId(id);

            verwijderEntiteitenRequestSender.send(soortEntiteitEnEntiteitId);
        }
    }

    public List<Bedrijf> alleBedrijvenBijRelatie(Relatie relatie) {
        return bedrijfRepository.alleBedrijvenBijRelatie(relatie);
    }

    public List<Bedrijf> alles() {
        return bedrijfRepository.alles();
    }

    public List<Bedrijf> zoekOpNaam(String zoekTerm) {
        return bedrijfRepository.zoekOpNaam(zoekTerm);
    }

    public void setBedrijfRepository(BedrijfRepository bedrijfRepository) {
        this.bedrijfRepository = bedrijfRepository;
    }
}
