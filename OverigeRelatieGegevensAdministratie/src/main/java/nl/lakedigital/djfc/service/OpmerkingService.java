package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.OpmerkingRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class OpmerkingService extends AbstractService<Opmerking> {
    @Inject
    private OpmerkingRepository opmerkingRepository;

    public OpmerkingService() {
        super(SoortEntiteit.OPMERKING);
    }

    @Override
    public AbstractRepository getRepository() {
        return opmerkingRepository;
    }

    public List<Opmerking> alles() {
        return opmerkingRepository.alles();
    }

    public void verwijder(Long id) {
        opmerkingRepository.verwijder(opmerkingRepository.lees(id));
    }
}