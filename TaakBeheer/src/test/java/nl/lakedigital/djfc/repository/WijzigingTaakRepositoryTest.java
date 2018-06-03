package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Taak;
import nl.lakedigital.djfc.domain.TaakStatus;
import nl.lakedigital.djfc.domain.WijzigingTaak;
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
public class WijzigingTaakRepositoryTest {
    @Inject
    private WijzigingTaakRepository wijzigingTaakRepository;
    @Inject
    private TaakRepository taakRepository;

    @Test
    public void testOpslaanEnLezen() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        Taak taak = new Taak();
        taak.setEntiteitId(entiteitId);
        taak.setSoortEntiteit(soortEntiteit);

        taakRepository.opslaan(taak);

        List<Taak> taken = taakRepository.alleTaken(soortEntiteit, entiteitId);

        assertThat(taken.size(), is(1));

        WijzigingTaak wijzigingTaak = new WijzigingTaak(taak, TaakStatus.AFGEROND, 3L);
        wijzigingTaakRepository.opslaan(wijzigingTaak);

        List<WijzigingTaak> wijzigingTaaks = wijzigingTaakRepository.allesBijTaak(taak);

        assertThat(wijzigingTaaks.size(), is(1));

        wijzigingTaakRepository.verwijder(wijzigingTaak);
        assertThat(wijzigingTaakRepository.allesBijTaak(taak).size(), is(0));

        taakRepository.verwijder(taak);

        taken = taakRepository.alleTaken(soortEntiteit, entiteitId);
        assertThat(taken.size(), is(0));
    }
}