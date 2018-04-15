package nl.lakedigital.djfc.repository;

import com.google.common.base.Predicate;
import nl.lakedigital.djfc.domain.Betaalfrequentie;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.particulier.AutoVerzekering;
import nl.lakedigital.djfc.domain.zakelijk.GeldVerzekering;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class PolisRepositoryTest {
    @Inject
    private PolisRepository polisRepository;

    @Inject
    private List<Polis> polislijst;

    @Test
    @Ignore
    public void testOpslaan() {
        //Alle polissoorten die er zijn worden opgeslagen, dit garandeerd dat alle polissen correct worden opgeslagen
        // hiermee wordt ook de DiscriminatorValue gecontroleerd. Na opslaan wordt ook de Audit tabel uitgelezen
        SoortEntiteit soortEntiteit=SoortEntiteit.RELATIE;
        Long entiteitId = 34L;

        List<Polis> polissen = new ArrayList<>();
        List<Polis> teControlerenPolissen =new ArrayList<>();

        for (Polis p : polislijst) {
            Polis polis = p.nieuweInstantie(soortEntiteit,entiteitId);
            polissen.add(polis);
            teControlerenPolissen.add(p);
        }

        polisRepository.opslaan(polissen);

        for (final Polis p : polissen) {
            AuditReader reader = AuditReaderFactory.get(polisRepository.getSession());

            polisRepository.getSession().getTransaction().begin();

            Polis auditedPolis = reader.find(Polis.class, p.getId(), 1);

            polisRepository.getSession().getTransaction().commit();

            assertThat(auditedPolis, is(p));

            teControlerenPolissen.remove(getFirst(filter(teControlerenPolissen, new Predicate<Polis>(){
                @Override
                public boolean apply(Polis o) {
                    return o.getClass().isInstance(p);
                }
            }),null));

            Polis opgehaaldePolis = polisRepository.lees(p.getId());
            assertThat(opgehaaldePolis.getRelatie(),is(34L));
        }

        assertThat(teControlerenPolissen.size(),is(0));
        assertThat(polisRepository.alles(soortEntiteit,entiteitId).size(),is(polislijst.size()));

        for(Polis p : polissen){
            polisRepository.verwijder(p);
        }

        assertThat(polisRepository.alles(soortEntiteit,entiteitId).size(),is(0));
    }

    @Test
    public void testWijzigenPolis(){
        SoortEntiteit soortEntiteit=SoortEntiteit.BEDRIJF;
        Long entiteitId = 9L;

        assertThat(polisRepository.alles(soortEntiteit,entiteitId).size(),is(0));

        AutoVerzekering autoVerzekering=new AutoVerzekering(soortEntiteit,entiteitId);

        polisRepository.opslaan(autoVerzekering);
        assertThat(polisRepository.alles(soortEntiteit,entiteitId).size(),is(1));

        autoVerzekering.setBetaalfrequentie(Betaalfrequentie.J);
        autoVerzekering.setKenmerk("kenmerkAutoverzekering");

        polisRepository.opslaan(autoVerzekering);

        assertThat(polisRepository.alles(soortEntiteit,entiteitId).size(),is(1));

        Polis opgehaald = polisRepository.lees(autoVerzekering.getId());
assertThat(opgehaald.getBetaalfrequentie(),is(autoVerzekering.getBetaalfrequentie()));
        assertThat(opgehaald.getKenmerk(),is(autoVerzekering.getKenmerk()));

        polisRepository.verwijder(autoVerzekering);

        assertThat(polisRepository.alles(soortEntiteit,entiteitId).size(),is(0));
    }

    @Test
    public void testZoekOpPolisNummer() {
        Polis autoVerzekering = new AutoVerzekering(SoortEntiteit.RELATIE, 1L);
        autoVerzekering.setPolisNummer("a");
        Polis geldVerzekering = new GeldVerzekering(SoortEntiteit.BEDRIJF, 2L);
        geldVerzekering.setPolisNummer("b");

        List<Polis> polissen = new ArrayList<>();
        polissen.add(autoVerzekering);
        polissen.add(geldVerzekering);

        polisRepository.opslaan(polissen);

        assertThat(polisRepository.zoekOpPolisNummer("a"), is(newArrayList(autoVerzekering)));
        assertThat(polisRepository.zoekOpPolisNummer("b"), is(newArrayList(geldVerzekering)));

        polisRepository.verwijder(polissen);
    }
}