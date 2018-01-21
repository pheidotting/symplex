package nl.dias.service;

import nl.dias.domein.Belastingzaken;
import nl.dias.repository.BelastingzakenRepository;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class BelastingzakenService {
    @Inject
    private BelastingzakenRepository belastingzakenRepository;

    public List<Belastingzaken> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        List<Belastingzaken.SoortBelastingzaak> benodigdeSoorten = null;
        if (soortEntiteit == SoortEntiteit.RELATIE) {
            benodigdeSoorten = newArrayList(Belastingzaken.SoortBelastingzaak.IB, Belastingzaken.SoortBelastingzaak.OVERIG);
        }

        List<Belastingzaken> belastingzaken = belastingzakenRepository.alles(soortEntiteit, entiteitId);

        for (Belastingzaken belastingzaak : belastingzaken) {
            benodigdeSoorten.remove(belastingzaak.getSoort());
        }
        if (benodigdeSoorten.size() > 0) {
            for (Belastingzaken.SoortBelastingzaak soort : benodigdeSoorten) {
                Belastingzaken belastingzakenNieuw = new Belastingzaken();
                belastingzakenNieuw.setSoort(soort);
                belastingzakenNieuw.setJaar(LocalDate.now().getYear());
                belastingzakenNieuw.setEntiteitId(entiteitId);
                belastingzakenNieuw.setSoortEntiteit(soortEntiteit);

                belastingzakenRepository.opslaan(belastingzakenNieuw);

                belastingzaken.add(belastingzakenNieuw);
            }
        }

        return belastingzaken;
    }
}
