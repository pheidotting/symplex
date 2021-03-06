package nl.lakedigital.djfc.repository;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.Taak;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class TaakRepositoryTest {
    @Inject
    private TaakRepository taakRepository;

    private Lorem lorem = LoremIpsum.getInstance();

    @Test
    public void testOpslaanEnLezen() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        Taak taak = new Taak();
        taak.setEntiteitId(entiteitId);
        taak.setSoortEntiteit(soortEntiteit);
        taak.setOmschrijving(lorem.getParagraphs(10, 10).substring(0, 2500));
        taak.setDeadline(LocalDate.now());
        taak.setTijdstipAfgehandeld(LocalDateTime.now());
        taak.setTijdstipCreatie(LocalDateTime.now());
        taak.setTitel(lorem.getParagraphs(1, 1).substring(0, 50));

        taakRepository.opslaan(taak);

        List<Taak> taken = taakRepository.alleTaken(soortEntiteit, entiteitId);

        assertThat(taken.size(), is(1));

        taakRepository.verwijder(taak);

        taken = taakRepository.alleTaken(soortEntiteit, entiteitId);
        assertThat(taken.size(), is(0));
    }

    @Test
    public void allesOpenstaand() {
        LocalDateTime tijdstip = LocalDateTime.now();

        Taak taakNietAfgehandeld = new Taak();
        Taak taakAfgehandeld = new Taak();
        taakAfgehandeld.setTijdstipAfgehandeld(tijdstip);

        taakRepository.opslaan(taakAfgehandeld);
        taakRepository.opslaan(taakNietAfgehandeld);

        List<Taak> result = taakRepository.allesOpenstaand();
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getTijdstipAfgehandeld(), is(nullValue()));
    }
}