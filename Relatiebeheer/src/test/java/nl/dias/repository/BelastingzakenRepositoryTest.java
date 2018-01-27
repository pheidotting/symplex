package nl.dias.repository;

import nl.dias.domein.Belastingzaken;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class BelastingzakenRepositoryTest {
    @Inject
    private BelastingzakenRepository belastingzakenRepository;

    @Test
    public void test() {
        Belastingzaken belastingzaken = new Belastingzaken();
        belastingzaken.setEntiteitId(46L);
        belastingzaken.setSoortEntiteit(SoortEntiteit.RELATIE);
        belastingzaken.setSoort(Belastingzaken.SoortBelastingzaak.BTW);
        belastingzaken.setJaar(2017);

        belastingzakenRepository.opslaan(belastingzaken);

        assertThat(belastingzakenRepository.alles(SoortEntiteit.RELATIE, 46L).size(), is(1));
    }
}