package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Trial;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class LicentieRepositoryTest {
    @Inject
    private LicentieRepository licentieRepository;

    @Test
    public void opslaan() {
        Trial trial = new Trial();
        trial.setKantoor(1L);

        licentieRepository.opslaan(trial);
    }
}