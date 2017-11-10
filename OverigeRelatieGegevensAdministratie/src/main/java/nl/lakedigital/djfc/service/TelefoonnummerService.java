package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.TelefoonnummerRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;


@Service
public class TelefoonnummerService extends AbstractService<Telefoonnummer> {
    public TelefoonnummerService() {
        super(nl.lakedigital.as.messaging.domain.SoortEntiteit.TELEFOONNUMMER);
    }

    @Inject
    private TelefoonnummerRepository telefoonnummerRepository;

    @Override
    public AbstractRepository getRepository() {
        return telefoonnummerRepository;
    }

    public List<Telefoonnummer> alles() {
        return telefoonnummerRepository.alles();
    }
}
