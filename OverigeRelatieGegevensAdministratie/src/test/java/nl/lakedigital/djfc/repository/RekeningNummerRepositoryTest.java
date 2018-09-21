package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.RekeningNummer;
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
public class RekeningNummerRepositoryTest extends AbstractRepositoryTest<RekeningNummer> {

    @Inject
    private RekeningNummerRepository rekeningNummerRepository;

    @Before
    public void init() {
        Sessie.setIngelogdeGebruiker(46L);
    }

    @Test
    public void opslaan() {
        SoortEntiteit soortEntiteit = SoortEntiteit.SCHADE;
        Long entiteitId = 3L;

        assertEquals(0, rekeningNummerRepository.alles(soortEntiteit, entiteitId).size());

        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer("NL12ABCD0123456789");
        rekeningNummer.setEntiteitId(entiteitId);
        rekeningNummer.setSoortEntiteit(soortEntiteit);

        rekeningNummerRepository.opslaan(newArrayList(rekeningNummer));

        assertEquals(1, rekeningNummerRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(rekeningNummer, rekeningNummerRepository.lees(rekeningNummer.getId()));

        rekeningNummer.setBic("bic");

        rekeningNummerRepository.opslaan(newArrayList(rekeningNummer));

        assertEquals(1, rekeningNummerRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(1, rekeningNummerRepository.alles().size());
        assertEquals(rekeningNummer, rekeningNummerRepository.lees(rekeningNummer.getId()));

        rekeningNummerRepository.verwijder(newArrayList(rekeningNummer));

        assertEquals(0, rekeningNummerRepository.alles(soortEntiteit, entiteitId).size());
    }

    @Override
    public RekeningNummer maakEntiteit(String zoekWaarde) {
        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setRekeningnummer(zoekWaarde);

        return rekeningNummer;
    }

    @Override
    public AbstractRepository getRepository() {
        return rekeningNummerRepository;
    }
}