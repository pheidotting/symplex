package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Trial;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class LicentieRepositoryTest {
    @Inject
    private LicentieRepository licentieRepository;

    @Test
    public void opslaan() {
        Trial trial = new Trial();
        trial.setKantoor(1L);
        trial.setAantalDagen(10);

        licentieRepository.opslaan(trial);
    }

    @Test
    public void alleLicenties1Licentie() {
        Long kantoorId = 6L;
        Trial trial = new Trial();
        trial.setKantoor(kantoorId);
        trial.setAantalDagen(10);

        licentieRepository.opslaan(trial);

        assertThat(licentieRepository.alleLicenties(kantoorId), is(newArrayList(trial)));

        licentieRepository.verwijder(trial);
    }

    @Test
    public void alleLicenties1VerlopenLicentie() {
        Long kantoorId = 6L;
        Trial trial = new Trial();
        trial.setKantoor(kantoorId);
        trial.setStartDatum(LocalDate.now().minusDays(5));
        trial.setAantalDagen(4);

        licentieRepository.opslaan(trial);

        assertThat(licentieRepository.alleLicenties(kantoorId), is(newArrayList(trial)));

        licentieRepository.verwijder(trial);
    }

    @Test
    public void alleLicenties2Licenties1Toekomstig() {
        Long kantoorId = 6L;
        Trial trial1 = new Trial();
        trial1.setKantoor(kantoorId);
        trial1.setStartDatum(LocalDate.now().minusDays(5));
        trial1.setAantalDagen(6);
        Trial trial2 = new Trial();
        trial2.setKantoor(kantoorId);
        trial2.setStartDatum(LocalDate.now().plusDays(2));
        trial2.setAantalDagen(4);

        licentieRepository.opslaan(trial1);
        licentieRepository.opslaan(trial2);

        assertThat(licentieRepository.alleLicenties(kantoorId), is(newArrayList(trial2, trial1)));

        licentieRepository.verwijder(trial1);
        licentieRepository.verwijder(trial2);
    }

    @Test
    public void alleLicenties2Licenties() {
        Long kantoorId = 6L;
        Trial trial1 = new Trial();
        trial1.setKantoor(kantoorId);
        trial1.setStartDatum(LocalDate.now().minusDays(5));
        trial1.setAantalDagen(2);
        Trial trial2 = new Trial();
        trial2.setKantoor(kantoorId);
        trial2.setStartDatum(LocalDate.now().minusDays(3));
        trial2.setAantalDagen(4);

        licentieRepository.opslaan(trial1);
        licentieRepository.opslaan(trial2);

        assertThat(licentieRepository.alleLicenties(kantoorId), is(newArrayList(trial2, trial1)));

        licentieRepository.verwijder(trial1);
        licentieRepository.verwijder(trial2);
    }
}