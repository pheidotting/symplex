package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.TelefonieBestand;
import nl.lakedigital.djfc.inloggen.Sessie;
import org.junit.Before;
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
public class TelefonieBestandRepositoryTest {
    @Inject
    private TelefonieBestandRepository telefonieBestandRepository;

    @Before
    public void init() {
        Sessie.setIngelogdeGebruiker(46L);
    }

    @Test
    public void testAlles() {
        String bestandsnaam1 = "out-0591377338-2912-20170102-185025-1483379425.187.wav";
        String bestandsnaam2 = "rg-8001-0614165929-20170102-115841-1483354721.74.wav";

        TelefonieBestand telefonieBestand1 = new TelefonieBestand(bestandsnaam1);
        TelefonieBestand telefonieBestand2 = new TelefonieBestand(bestandsnaam2);

        telefonieBestandRepository.opslaan(newArrayList(telefonieBestand1, telefonieBestand2));

        assertThat(telefonieBestandRepository.alles().size(), is(2));

        telefonieBestandRepository.getTransaction().begin();
        telefonieBestandRepository.getSession().delete(telefonieBestand1);
        telefonieBestandRepository.getSession().delete(telefonieBestand2);
        telefonieBestandRepository.getTransaction().commit();

        assertThat(telefonieBestandRepository.alles().size(), is(0));
    }

    @Test
    public void testAllesMetTelefoonnummer() {
        String bestandsnaam1 = "out-0591377338-2912-20170102-185025-1483379425.187.wav";
        String bestandsnaam2 = "rg-8001-0614165929-20170102-115841-1483354721.74.wav";

        TelefonieBestand telefonieBestand1 = new TelefonieBestand(bestandsnaam1);
        TelefonieBestand telefonieBestand2 = new TelefonieBestand(bestandsnaam2);

        telefonieBestandRepository.opslaan(newArrayList(telefonieBestand1, telefonieBestand2));

        assertThat(telefonieBestandRepository.allesMetTelefoonnummer("0591377338").size(), is(1));
        assertThat(telefonieBestandRepository.allesMetTelefoonnummer("0614165929").size(), is(1));

        assertThat(telefonieBestandRepository.alles().size(), is(2));

        telefonieBestandRepository.getTransaction().begin();
        telefonieBestandRepository.getSession().delete(telefonieBestand1);
        telefonieBestandRepository.getSession().delete(telefonieBestand2);
        telefonieBestandRepository.getTransaction().commit();

        assertThat(telefonieBestandRepository.alles().size(), is(0));

    }
}