package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class VerwijderEntiteitService {
    @Inject
    private OpmerkingService opmerkingService;

    public void verwijderen(SoortEntiteit soortEntiteit, Long entiteitId) {
        if (soortEntiteit == SoortEntiteit.OPMERKING) {
            opmerkingService.verwijder(entiteitId);
        }
    }
}
