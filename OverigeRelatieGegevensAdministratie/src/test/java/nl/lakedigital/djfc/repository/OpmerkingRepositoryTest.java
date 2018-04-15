package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.inloggen.Sessie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class OpmerkingRepositoryTest extends AbstractRepositoryTest<Opmerking> {

    @Inject
    private OpmerkingRepository opmerkingRepository;

    @Before
    public void init() {
        Sessie.setIngelogdeGebruiker(46L);
    }

    @Test
    public void opslaan() {
        SoortEntiteit soortEntiteit = SoortEntiteit.SCHADE;
        Long entiteitId = 3L;

        assertEquals(0, opmerkingRepository.alles(soortEntiteit, entiteitId).size());

        Opmerking opmerking = new Opmerking();
        opmerking.setEntiteitId(entiteitId);
        opmerking.setSoortEntiteit(soortEntiteit);
        opmerking.setMedewerker(3L);
        opmerking.setOpmerking("blablabla opmerking");

        opmerkingRepository.opslaan(newArrayList(opmerking));

        assertNotNull(opmerking.getTijd());
        assertEquals(1, opmerkingRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(opmerking, opmerkingRepository.lees(opmerking.getId()));

        opmerking.setOpmerking("Andere opmerking");

        opmerkingRepository.opslaan(newArrayList(opmerking));

        assertEquals(1, opmerkingRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(1, opmerkingRepository.alles().size());
        assertEquals(opmerking, opmerkingRepository.lees(opmerking.getId()));

        opmerkingRepository.verwijder(newArrayList(opmerking));

        assertEquals(0, opmerkingRepository.alles(soortEntiteit, entiteitId).size());
    }

    @Override
    public Opmerking maakEntiteit(String zoekWaarde) {
        Opmerking opmerking = new Opmerking();
        opmerking.setOpmerking(zoekWaarde);

        return opmerking;
    }

    @Override
    public AbstractRepository getRepository() {
        return opmerkingRepository;
    }
}