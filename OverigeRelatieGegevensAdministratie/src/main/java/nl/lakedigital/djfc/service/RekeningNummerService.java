package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
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
    private final static Logger LOGGER = LoggerFactory.getLogger(RekeningNummerService.class);

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
}
