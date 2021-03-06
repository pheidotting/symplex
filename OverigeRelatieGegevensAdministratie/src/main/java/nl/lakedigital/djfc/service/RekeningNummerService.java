package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.RekeningNummerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class RekeningNummerService extends AbstractService<RekeningNummer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RekeningNummerService.class);

    @Inject
    private RekeningNummerRepository rekeningNummerRepository;

    public RekeningNummerService() {
        super(SoortEntiteit.REKENINGNUMMER);
    }

    @Override
    public AbstractRepository getRepository() {
        return rekeningNummerRepository;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    public List<RekeningNummer> alles() {
        return rekeningNummerRepository.alles();
    }

    @Override
    public boolean isGevuld(RekeningNummer rekeningNummer) {
        return rekeningNummer != null && rekeningNummer.getRekeningnummer() != null && !"".equals(rekeningNummer.getRekeningnummer());
    }
}
