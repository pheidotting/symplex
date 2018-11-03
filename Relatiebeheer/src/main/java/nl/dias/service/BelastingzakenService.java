package nl.dias.service;

import nl.dias.domein.Belastingzaken;
import nl.dias.repository.BelastingzakenRepository;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class BelastingzakenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BelastingzakenService.class);

    @Inject
    private BelastingzakenRepository belastingzakenRepository;

    public List<Belastingzaken> alles(SoortEntiteit soortEntiteit, Long entiteitId) {
        List<Belastingzaken.SoortBelastingzaak> benodigdeSoorten = null;
        if (soortEntiteit == SoortEntiteit.RELATIE) {
            benodigdeSoorten = newArrayList(Belastingzaken.SoortBelastingzaak.IB, Belastingzaken.SoortBelastingzaak.OVERIG);
        } else if (soortEntiteit == SoortEntiteit.BEDRIJF) {
            benodigdeSoorten = newArrayList(Belastingzaken.SoortBelastingzaak.values());
        }

        List<Belastingzaken> belastingzaken = belastingzakenRepository.alles(soortEntiteit, entiteitId);

        for (Belastingzaken belastingzaak : belastingzaken) {
            benodigdeSoorten.remove(belastingzaak.getSoort());
        }
        if (!benodigdeSoorten.isEmpty()) {
            for (Belastingzaken.SoortBelastingzaak soort : benodigdeSoorten) {
                Belastingzaken belastingzakenNieuw = new Belastingzaken();
                belastingzakenNieuw.setSoort(soort);
                belastingzakenNieuw.setJaar(LocalDate.now().getYear());
                belastingzakenNieuw.setEntiteitId(entiteitId);
                belastingzakenNieuw.setSoortEntiteit(soortEntiteit);

                belastingzakenRepository.opslaan(belastingzakenNieuw);
            }
        }

        belastingzaken = belastingzakenRepository.alles(soortEntiteit, entiteitId);

        for (Belastingzaken bz : belastingzaken) {
            List<Integer> benodigdeJaren = newArrayList(LocalDate.now().getYear() - 1, LocalDate.now().getYear());
            for (Belastingzaken bz2 : belastingzaken) {
                List<Integer> benodigdeJaren2 = newArrayList(LocalDate.now().getYear() - 1, LocalDate.now().getYear());

                for (Integer jaar : benodigdeJaren2) {
                    if (bz2.getJaar().equals(jaar)) {
                        benodigdeJaren.remove(jaar);

                    }
                }
            }

            for (Integer jaar : benodigdeJaren) {
                Belastingzaken belastingzakenNieuw = new Belastingzaken();
                belastingzakenNieuw.setSoort(bz.getSoort());
                belastingzakenNieuw.setJaar(jaar);
                belastingzakenNieuw.setEntiteitId(entiteitId);
                belastingzakenNieuw.setSoortEntiteit(soortEntiteit);

                LOGGER.debug("B {}", ReflectionToStringBuilder.toString(belastingzakenNieuw));
                belastingzakenRepository.opslaan(belastingzakenNieuw);
            }
        }


        return belastingzakenRepository.alles(soortEntiteit, entiteitId);
    }
}
