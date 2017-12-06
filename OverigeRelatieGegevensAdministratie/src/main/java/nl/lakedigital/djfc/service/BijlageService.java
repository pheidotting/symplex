package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.BijlageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class BijlageService extends AbstractService<Bijlage> {
    private final static Logger LOGGER = LoggerFactory.getLogger(BijlageService.class);

    @Inject
    private BijlageRepository bijlageRepository;

    public BijlageService() {
        super(nl.lakedigital.as.messaging.domain.SoortEntiteit.BIJLAGE);
    }

    @Override
    public AbstractRepository getRepository() {
        return bijlageRepository;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    public void verwijder(Long id) {
        bijlageRepository.verwijder(bijlageRepository.lees(id));
    }

    public GroepBijlages leesGroepBijlages(Long id) {
        return bijlageRepository.leesGroepBijlages(id);
    }

    public List<GroepBijlages> alleGroepenBijlages(SoortEntiteit soortEntiteit, Long entiteitId) {
        return bijlageRepository.alleGroepenBijlages(soortEntiteit, entiteitId);
    }

    public List<GroepBijlages> alleGroepenBijlages() {
        return bijlageRepository.alleGroepenBijlages();
    }

    public void opslaanGroepBijlages(GroepBijlages groepBijlages) {
        bijlageRepository.opslaanGroepBijlages(groepBijlages);

        bijlageRepository.opslaan(groepBijlages.getBijlages());
    }

    public void wijzigOmschrijvingBijlage(Long id, String nieuweNaam) {
        Bijlage bijlage = bijlageRepository.lees(id);

        bijlage.setOmschrijving(nieuweNaam);

        bijlageRepository.opslaan(newArrayList(bijlage));
    }

    public List<Bijlage> alles() {
        return bijlageRepository.alles();
    }

}