package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.TelefoonnummerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;


@Service
public class TelefoonnummerService extends AbstractService<Telefoonnummer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefoonnummerService.class);
    @Inject
    private TelefoonnummerRepository telefoonnummerRepository;

    public TelefoonnummerService() {
        super(nl.lakedigital.as.messaging.domain.SoortEntiteit.TELEFOONNUMMER);
    }

    @Override
    public AbstractRepository getRepository() {
        return telefoonnummerRepository;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    public List<Telefoonnummer> alles() {
        return telefoonnummerRepository.alles();
    }

    @Override
    public boolean isGevuld(Telefoonnummer telefoonnummer) {
        return telefoonnummer != null && telefoonnummer.getTelefoonnummer() != null && !"".equals(telefoonnummer.getTelefoonnummer());
    }
}
