package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class VerwijderEntiteitenService {
    @Inject
    private List<AbstractService> abstractServices;

    public void verwijderen(SoortEntiteit soortEntiteit, Long entiteitId) {
        for (AbstractService abstractService : abstractServices) {
            abstractService.verwijderen(soortEntiteit, entiteitId);
        }
    }
}
