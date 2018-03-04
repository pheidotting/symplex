package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Licentie;
import nl.lakedigital.djfc.repository.IdentificatieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class IdentificatieService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificatieService.class);

    @Inject
    private IdentificatieRepository identificatieRepository;

    public void verwijder(Licentie identificatie) {
        identificatieRepository.verwijder(identificatie);
    }

    public void verwijder(List<Licentie> identificaties) {
        identificatieRepository.verwijder(identificaties);
    }

    public void opslaan(Licentie identificatie) {
        LOGGER.debug("{}", identificatie);
        if (zoek(identificatie.getSoortEntiteit(), identificatie.getEntiteitId()) == null) {
            identificatieRepository.opslaan(identificatie);
        }
    }

    public void opslaan(List<Licentie> identificaties) {
        identificatieRepository.opslaan(identificaties);
    }

    public Licentie zoek(String soortEntiteit, Long entiteitId) {
        Licentie identificatie = identificatieRepository.zoek(soortEntiteit, entiteitId);
        if (identificatie == null || (identificatie != null && identificatie.getIdentificatie() == null)) {
            identificatie = new Licentie(soortEntiteit, entiteitId);
            identificatieRepository.opslaan(identificatie);
        }
        return identificatie;
    }

    public Licentie zoekOpIdentificatieCode(String identificatieCode) {
        return identificatieRepository.zoekOpIdentificatieCode(identificatieCode);
    }
}
