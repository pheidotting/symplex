package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
import nl.lakedigital.djfc.repository.VerzekeringsMaatschappijRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Service
public class VerzekeringsMaatschappijService {
    @Inject
    private VerzekeringsMaatschappijRepository verzekeringsMaatschappijRepository;

    public VerzekeringsMaatschappij zoekOpNaam(String naam) {
        return verzekeringsMaatschappijRepository.zoekOpNaam(naam);
    }

    public List<VerzekeringsMaatschappij> alles() {
        List<VerzekeringsMaatschappij> lijst = verzekeringsMaatschappijRepository.alles();
        Collections.sort(lijst);

        return lijst;
    }
}
