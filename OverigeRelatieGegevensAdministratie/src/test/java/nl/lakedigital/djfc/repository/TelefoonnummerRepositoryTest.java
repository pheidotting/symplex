package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.domain.TelefoonnummerSoort;
import nl.lakedigital.djfc.inloggen.Sessie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class TelefoonnummerRepositoryTest extends AbstractRepositoryTest<Telefoonnummer> {

    @Inject
    private TelefoonnummerRepository telefoonnummerRepository;

    @Before
    public void init() {
        Sessie.setIngelogdeGebruiker(46L);
    }

    @Test
    public void opslaan() {
        SoortEntiteit soortEntiteit = SoortEntiteit.SCHADE;
        Long entiteitId = 3L;

        assertEquals(0, telefoonnummerRepository.alles(soortEntiteit, entiteitId).size());

        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer("0612345678");
        telefoonnummer.setEntiteitId(entiteitId);
        telefoonnummer.setSoortEntiteit(soortEntiteit);

        telefoonnummerRepository.opslaan(newArrayList(telefoonnummer));

        assertEquals(1, telefoonnummerRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(telefoonnummer, telefoonnummerRepository.lees(telefoonnummer.getId()));

        telefoonnummer.setOmschrijving("jadajada omschrijving");
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);

        telefoonnummerRepository.opslaan(newArrayList(telefoonnummer));

        assertEquals(1, telefoonnummerRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(1, telefoonnummerRepository.alles().size());
        assertEquals(telefoonnummer, telefoonnummerRepository.lees(telefoonnummer.getId()));

        telefoonnummerRepository.verwijder(newArrayList(telefoonnummer));

        assertEquals(0, telefoonnummerRepository.alles(soortEntiteit, entiteitId).size());
    }

    @Override
    public Telefoonnummer maakEntiteit(String zoekWaarde) {
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setTelefoonnummer(zoekWaarde);

        return telefoonnummer;
    }

    @Override
    public AbstractRepository getRepository() {
        return telefoonnummerRepository;
    }
}