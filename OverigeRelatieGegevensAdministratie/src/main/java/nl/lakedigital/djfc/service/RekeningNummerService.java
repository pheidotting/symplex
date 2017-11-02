package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.RekeningNummerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

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
}
