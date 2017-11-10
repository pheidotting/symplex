package nl.dias.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.JaarCijfers;
import nl.dias.repository.JaarCijfersRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Iterables.filter;

@Service
public class JaarCijfersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JaarCijfersService.class);

    @Inject
    private JaarCijfersRepository jaarCijfersRepository;
    @Inject
    private BedrijfService bedrijfService;

    public JaarCijfers lees(Long id) {
        return jaarCijfersRepository.lees(id);
    }

    public List<JaarCijfers> alles(Long bedrijfsId) {
        Bedrijf bedrijf = bedrijfService.lees(bedrijfsId);

        List<JaarCijfers> jaarCijfers = jaarCijfersRepository.allesBijBedrijf(bedrijf);

        Iterable<JaarCijfers> cijfersMetHuidigJaar = filter(jaarCijfers, new Predicate<JaarCijfers>() {
            @Override
            public boolean apply(JaarCijfers jaarCijfers) {
                return jaarCijfers.getJaar().equals(Long.valueOf(LocalDate.now().getYear()));
            }
        });

        if (Lists.newArrayList(cijfersMetHuidigJaar).size() == 0) {
            JaarCijfers jaarCijfersNw = new JaarCijfers();
            jaarCijfersNw.setJaar(Long.valueOf(LocalDate.now().getYear()));
            jaarCijfersNw.setBedrijf(bedrijf);

            jaarCijfersRepository.opslaan(jaarCijfersNw);

            jaarCijfers.add(jaarCijfersNw);
        }

        Iterable<JaarCijfers> cijfersMetVorigJaar = filter(jaarCijfers, new Predicate<JaarCijfers>() {
            @Override
            public boolean apply(JaarCijfers jaarCijfers) {
                return jaarCijfers.getJaar().equals(Long.valueOf(LocalDate.now().minusYears(1).getYear()));
            }
        });

        if (Lists.newArrayList(cijfersMetVorigJaar).size() == 0) {
            JaarCijfers jaarCijfersNw = new JaarCijfers();
            jaarCijfersNw.setJaar(Long.valueOf(LocalDate.now().minusYears(1).getYear()));
            jaarCijfersNw.setBedrijf(bedrijf);

            jaarCijfersRepository.opslaan(jaarCijfersNw);

            jaarCijfers.add(jaarCijfersNw);
        }

        return jaarCijfers;
    }
}
