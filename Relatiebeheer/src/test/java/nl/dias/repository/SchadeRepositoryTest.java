package nl.dias.repository;

import inloggen.SessieHolder;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.StatusSchade;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.Polis;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class SchadeRepositoryTest {

    @Inject
    private SchadeRepository schadeRepository;

    @Before
    public void init() {
        SessieHolder.get().setIngelogdeGebruiker(4L);
        SessieHolder.get().setTrackAndTraceId("tEnTId");
    }

    @Test
    public void alleOpenSchade() {
        //Kantoor 1
        Kantoor kantoor = new Kantoor();

        Relatie relatie = new Relatie();
        relatie.setKantoor(kantoor);

        StatusSchade statusSchade = new StatusSchade();
        statusSchade.setStatus("status");
        statusSchade.setIngebruik(true);

        schadeRepository.getTransaction();
        schadeRepository.getSession().persist(statusSchade);
        schadeRepository.getSession().persist(kantoor);
        schadeRepository.getSession().persist(relatie);
        schadeRepository.getTransaction().commit();

        Polis polis = new AutoVerzekering();
        polis.setRelatie(relatie.getId());

        schadeRepository.getTransaction();
        schadeRepository.getSession().persist(polis);
        schadeRepository.getTransaction().commit();

        Schade openSchade = new Schade();
        openSchade.setPolis(polis.getId());
        openSchade.setDatumTijdMelding(LocalDateTime.now());
        openSchade.setDatumTijdSchade(LocalDateTime.now());
        openSchade.setSchadeNummerMaatschappij("123");
        openSchade.setStatusSchade(statusSchade);
        Schade afgehandeldeSchade = new Schade();
        afgehandeldeSchade.setPolis(polis.getId());
        afgehandeldeSchade.setDatumTijdMelding(LocalDateTime.now());
        afgehandeldeSchade.setDatumTijdSchade(LocalDateTime.now());
        afgehandeldeSchade.setSchadeNummerMaatschappij("1234");
        afgehandeldeSchade.setStatusSchade(statusSchade);
        afgehandeldeSchade.setDatumAfgehandeld(LocalDate.now());

        schadeRepository.opslaan(openSchade);
        schadeRepository.opslaan(afgehandeldeSchade);

        //Kantoor 2
        Kantoor kantoor2 = new Kantoor();

        Relatie relatie2 = new Relatie();
        relatie2.setKantoor(kantoor2);

        schadeRepository.getTransaction();
        schadeRepository.getSession().persist(kantoor2);
        schadeRepository.getSession().persist(relatie2);
        schadeRepository.getTransaction().commit();

        Polis polis2 = new AutoVerzekering();
        polis2.setRelatie(relatie2.getId());

        schadeRepository.getTransaction();
        schadeRepository.getSession().persist(polis2);
        schadeRepository.getTransaction().commit();

        Schade openSchade2 = new Schade();
        openSchade2.setPolis(polis2.getId());
        openSchade2.setDatumTijdMelding(LocalDateTime.now());
        openSchade2.setDatumTijdSchade(LocalDateTime.now());
        openSchade2.setSchadeNummerMaatschappij("12345");
        openSchade2.setStatusSchade(statusSchade);

        schadeRepository.opslaan(openSchade2);

        //Kantoor 3
        Kantoor kantoor3 = new Kantoor();

        Relatie relatie3 = new Relatie();
        relatie3.setKantoor(kantoor3);

        schadeRepository.getTransaction();
        schadeRepository.getSession().persist(kantoor3);
        schadeRepository.getSession().persist(relatie3);
        schadeRepository.getTransaction().commit();

        Polis polis3 = new AutoVerzekering();
        polis3.setRelatie(relatie3.getId());

        schadeRepository.getTransaction();
        schadeRepository.getSession().persist(polis3);
        schadeRepository.getTransaction().commit();

        Schade afgehandeldeSchade3 = new Schade();
        afgehandeldeSchade3.setPolis(polis3.getId());
        afgehandeldeSchade3.setDatumTijdMelding(LocalDateTime.now());
        afgehandeldeSchade3.setDatumTijdSchade(LocalDateTime.now());
        afgehandeldeSchade3.setSchadeNummerMaatschappij("123456");
        afgehandeldeSchade3.setStatusSchade(statusSchade);
        afgehandeldeSchade3.setDatumAfgehandeld(LocalDate.now());

        schadeRepository.opslaan(afgehandeldeSchade3);

        assertThat(schadeRepository.alleOpenSchade(kantoor).size(), is(1));
        assertThat(schadeRepository.alleOpenSchade(kantoor2).size(), is(1));
        assertThat(schadeRepository.alleOpenSchade(kantoor3).size(), is(0));
    }

}
