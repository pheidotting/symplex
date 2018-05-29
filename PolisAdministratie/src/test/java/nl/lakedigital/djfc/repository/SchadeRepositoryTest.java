package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.domain.SoortSchade;
import nl.lakedigital.djfc.domain.StatusSchade;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class SchadeRepositoryTest {
    @Inject
    private SchadeRepository repository;

    @Test
    public void testSoortenSchade() {
        SoortSchade soortSchade1 = new SoortSchade();
        soortSchade1.setIngebruik(true);
        soortSchade1.setOmschrijving("bab");

        SoortSchade soortSchade2 = new SoortSchade();
        soortSchade2.setIngebruik(false);
        soortSchade2.setOmschrijving("ccc");

        SoortSchade soortSchade3 = new SoortSchade();
        soortSchade3.setIngebruik(true);
        soortSchade3.setOmschrijving("deb");

        repository.opslaan(soortSchade1);
        repository.opslaan(soortSchade2);
        repository.opslaan(soortSchade3);

        assertEquals(2, repository.soortenSchade().size());
        assertEquals(2, repository.soortenSchade("b").size());
        assertEquals(1, repository.soortenSchade("a").size());
        assertEquals(1, repository.soortenSchade("e").size());
        assertEquals(0, repository.soortenSchade("c").size());

        List<SoortSchade> soortenSchade = newArrayList(soortSchade1, soortSchade2, soortSchade3);

        repository.verwijderSoortenSchade(soortenSchade);

        AuditReader reader = AuditReaderFactory.get(repository.getSession());

        repository.getSession().getTransaction().begin();

        List<Number> revisions = reader.getRevisions(SoortSchade.class, soortSchade1.getId());

        assertThat(revisions.size(), is(2));
    }

    @Test
    public void getStatussen() {
        StatusSchade statusSchade1 = new StatusSchade();
        statusSchade1.setStatus("status1");
        statusSchade1.setIngebruik(true);
        StatusSchade statusSchade2 = new StatusSchade();
        statusSchade2.setStatus("status2");
        statusSchade2.setIngebruik(true);
        StatusSchade statusSchade3 = new StatusSchade();
        statusSchade3.setStatus("status3");
        statusSchade3.setIngebruik(true);

        repository.opslaan(statusSchade1);
        repository.opslaan(statusSchade2);
        repository.opslaan(statusSchade3);

        assertEquals(3, repository.getStatussen().size());
        assertEquals(statusSchade1, repository.getStatussen("status1"));
        assertEquals(statusSchade2, repository.getStatussen("status2"));
        assertEquals(statusSchade3, repository.getStatussen("status3"));

        repository.verwijderStatusSchade(newArrayList(statusSchade1, statusSchade2, statusSchade3));
    }

    @Test
    public void zoekOpSchadeNummerMaatschappij() {
        StatusSchade statusSchade = maakStatusSchade("status");

        repository.opslaan(statusSchade);

        Schade schade1 = maakSchade("schadeNummerMaatschappij1", statusSchade, 1L);
        Schade schade2 = maakSchade("schadeNummerMaatschappij2", statusSchade, 1L);

        repository.opslaan(newArrayList(schade1, schade2));

        assertEquals(schade1.getId(), repository.zoekOpSchadeNummerMaatschappij("schadeNummerMaatschappij1").get(0).getId());
        assertEquals(schade2.getId(), repository.zoekOpSchadeNummerMaatschappij("schadeNummerMaatschappij2").get(0).getId());

        repository.verwijder(newArrayList(schade1, schade2));
    }

    @Test
    @Ignore
    public void opslaanEnVerwijderen() {
        assertThat(repository.alleSchades(1L).size(), is(0));

        StatusSchade statusSchade = maakStatusSchade("a");

        repository.opslaan(statusSchade);

        Schade schade1 = maakSchade("1", statusSchade, 1L);
        Schade schade2 = maakSchade("2", statusSchade, 1L);

        repository.opslaan(schade1);

        assertThat(repository.alleSchades(1L).size(), is(1));

        repository.opslaan(schade2);

        assertThat(repository.alleSchades(1L).size(), is(2));

        schade2.setOmschrijving("nwOmschrijving");
        repository.opslaan(schade2);

        assertThat(repository.alleSchades(1L).size(), is(2));
        assertThat(repository.lees(schade1.getId()), is(schade1));
        assertThat(repository.lees(schade2.getId()), is(schade2));

        repository.verwijder(schade1);

        assertThat(repository.alleSchades(1L).size(), is(1));

        repository.verwijder(schade2);

        assertThat(repository.alleSchades(1L).size(), is(0));
        repository.verwijderStatusSchade(newArrayList(statusSchade));
    }

    @Test
    public void zoekOpPolisNummer() {
        assertThat(repository.alleSchades(1L).size(), is(0));

        StatusSchade statusSchade = maakStatusSchade("a");

        repository.opslaan(statusSchade);
        statusSchade.setStatus("nwStatus");
        repository.opslaan(statusSchade);

        Schade schade1 = maakSchade("1", statusSchade, 1L);
        Schade schade2 = maakSchade("2", statusSchade, 2L);

        repository.opslaan(newArrayList(schade1, schade2));

        assertThat(repository.alleSchades(1L).size(), is(1));
        assertThat(repository.alleSchades(2L).size(), is(1));

        repository.verwijder(newArrayList(schade1, schade2));
        repository.verwijderStatusSchade(newArrayList(statusSchade));
    }

    @Test
    public void testOpslaanEnVerwijderenSoortSchade() {
        SoortSchade soortSchade = maakSoortSchade("omschr");

        repository.opslaan(soortSchade);

        assertThat(repository.soortenSchade().size(), is(1));
        Long id = repository.soortenSchade().get(0).getId();

        soortSchade.setOmschrijving("blabla");

        repository.opslaan(soortSchade);

        assertThat(repository.soortenSchade().size(), is(1));
        assertThat(repository.soortenSchade().get(0).getId(), is(id));

        repository.verwijder(soortSchade);

        assertThat(repository.soortenSchade().size(), is(0));
    }

    private Schade maakSchade(String schadeNummer, StatusSchade statusSchade, Long polis) {
        Schade schade1 = new Schade();
        schade1.setSchadeNummerMaatschappij(schadeNummer);
        schade1.setStatusSchade(statusSchade);
        schade1.setDatumSchade(new LocalDateTime());
        schade1.setDatumMelding(new LocalDateTime());
        schade1.setPolis(polis);

        return schade1;
    }

    private StatusSchade maakStatusSchade(String status) {
        StatusSchade statusSchade = new StatusSchade();
        statusSchade.setStatus(status);
        statusSchade.setIngebruik(true);

        return statusSchade;
    }

    private SoortSchade maakSoortSchade(String omschrijving) {
        SoortSchade soortSchade = new SoortSchade();
        soortSchade.setIngebruik(true);
        soortSchade.setOmschrijving(omschrijving);

        return soortSchade;
    }
}
