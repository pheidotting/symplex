package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.repository.TaakRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class TaakService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaakService.class);

    private static final int AANTAL_DAGEN_TRIAL = 30;

    @Inject
    private TaakRepository taakRepository;

}
