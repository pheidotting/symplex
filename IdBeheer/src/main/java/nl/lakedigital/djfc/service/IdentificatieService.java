package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Identificatie;
import nl.lakedigital.djfc.repository.IdentificatieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class IdentificatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificatieService.class);

    @Inject
    private IdentificatieRepository identificatieRepository;

    public void verwijder(Identificatie identificatie) {
        identificatieRepository.verwijder(identificatie);
    }


    public void opslaan(Identificatie identificatie) {
        LOGGER.debug("{}", identificatie);
        if (zoek(identificatie.getSoortEntiteit(), identificatie.getEntiteitId()) == null) {
            identificatieRepository.opslaan(identificatie);
        }
    }

    public Identificatie zoek(String soortEntiteit, Long entiteitId) {
        Identificatie identificatie = identificatieRepository.zoek(soortEntiteit, entiteitId);
        if (identificatie == null) {
            identificatie = new Identificatie(soortEntiteit, entiteitId);
            identificatieRepository.opslaan(identificatie);
        }
        return identificatie;
    }

    public Identificatie zoekOpIdentificatieCode(String identificatieCode) {
        return identificatieRepository.zoekOpIdentificatieCode(identificatieCode);
    }
}
