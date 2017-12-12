package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.RekeningNummerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RekeningNummerService extends AbstractService<RekeningNummer> {
    @Inject
    private RekeningNummerRepository rekeningNummerRepository;

    public RekeningNummerService() {
        super(SoortEntiteit.REKENINGNUMMER);
    }

    @Override
    public AbstractRepository getRepository() {
        return rekeningNummerRepository;
    }

    public List<RekeningNummer> alles() {
        return rekeningNummerRepository.alles();
    }

    @Override
    public void opslaan(RekeningNummer rekeningNummer) {
        if (rekeningNummer != null && rekeningNummer.getRekeningnummer() != null && !"".equals(rekeningNummer.getRekeningnummer())) {
            super.opslaan(rekeningNummer);
        }
    }

    @Override
    public void opslaan(List<RekeningNummer> rekeningNummers) {
        super.opslaan(rekeningNummers.stream().filter(rekeningNummer -> rekeningNummer != null && rekeningNummer.getRekeningnummer() != null && !"".equals(rekeningNummer.getRekeningnummer())).collect(Collectors.toList()));
    }


    @Override
    public void opslaan(final List<RekeningNummer> rekeningNummers, nl.lakedigital.djfc.domain.SoortEntiteit soortEntiteit, Long entiteitId) {
        super.opslaan(rekeningNummers.stream().filter(rekeningNummer -> rekeningNummer != null && rekeningNummer.getRekeningnummer() != null && !"".equals(rekeningNummer.getRekeningnummer())).collect(Collectors.toList()), soortEntiteit, entiteitId);
    }
}
