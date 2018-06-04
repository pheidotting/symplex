package nl.lakedigital.djfc.repository;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Taak;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        taak.setDeadline(LocalDateTime.now());
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
}