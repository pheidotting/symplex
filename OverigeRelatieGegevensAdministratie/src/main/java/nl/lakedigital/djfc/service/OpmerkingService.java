package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.OpmerkingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class OpmerkingService extends AbstractService<Opmerking> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpmerkingService.class);
    @Inject
    private OpmerkingRepository opmerkingRepository;

    public OpmerkingService() {
        super(SoortEntiteit.OPMERKING);
    }

    @Override
    public AbstractRepository getRepository() {
        return opmerkingRepository;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    public List<Opmerking> alles() {
        return opmerkingRepository.alles();
    }

    public void verwijder(Long id) {
        opmerkingRepository.verwijder(opmerkingRepository.lees(id));
    }

    @Override
    public boolean isGevuld(Opmerking opmerking) {
        return opmerking != null && opmerking.getOpmerking() != null && !"".equals(opmerking.getOpmerking());
    }
}