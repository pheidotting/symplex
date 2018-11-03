package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import nl.lakedigital.djfc.inloggen.Sessie;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class BijlageRepositoryTest extends AbstractRepositoryTest<Bijlage> {

    @Inject
    private BijlageRepository bijlageRepository;

    @Before
    public void init() {
        Sessie.setIngelogdeGebruiker(46L);
    }

    @Test
    public void opslaan() {
        SoortEntiteit soortEntiteit = SoortEntiteit.SCHADE;
        Long entiteitId = 3L;

        assertEquals(0, bijlageRepository.alles(soortEntiteit, entiteitId).size());

        Bijlage bijlage = new Bijlage();
        bijlage.setEntiteitId(entiteitId);
        bijlage.setSoortEntiteit(soortEntiteit);
        bijlage.setBestandsNaam("aa.pdf");
        bijlage.setOmschrijving("omschr");
        bijlage.setUploadMoment(LocalDateTime.now());

        bijlageRepository.opslaan(newArrayList(bijlage));


        assertEquals(1, bijlageRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(bijlage, bijlageRepository.lees(bijlage.getId()));

        bijlage.setOmschrijving("Andere bijlage");

        bijlageRepository.opslaan(newArrayList(bijlage));

        assertEquals(1, bijlageRepository.alles(soortEntiteit, entiteitId).size());
        assertEquals(bijlage, bijlageRepository.lees(bijlage.getId()));

        bijlageRepository.verwijder(newArrayList(bijlage));

        assertEquals(0, bijlageRepository.alles(soortEntiteit, entiteitId).size());
    }

    @Override
    public Bijlage maakEntiteit(String zoekWaarde) {
        Bijlage bijlage = new Bijlage();
        bijlage.setUploadMoment(LocalDateTime.now());
        bijlage.setOmschrijving(zoekWaarde);

        return bijlage;
    }

    @Test
    public void testGroepBijlages() {
        SoortEntiteit soortEntiteit = SoortEntiteit.SCHADE;
        Long entiteitId = 3L;

        assertEquals(0, bijlageRepository.alles(soortEntiteit, entiteitId).size());

        Bijlage bijlage = new Bijlage();
        bijlage.setEntiteitId(entiteitId);
        bijlage.setSoortEntiteit(soortEntiteit);
        bijlage.setBestandsNaam("aa.pdf");
        bijlage.setOmschrijving("omschr");
        bijlage.setUploadMoment(LocalDateTime.now());

        bijlageRepository.opslaan(newArrayList(bijlage));

        assertEquals(1, bijlageRepository.alles(soortEntiteit, entiteitId).size());

        GroepBijlages groepBijlages = new GroepBijlages();
        groepBijlages.setNaam("naam");

        bijlageRepository.opslaanGroepBijlages(groepBijlages);

        groepBijlages.getBijlages().add(bijlage);
        bijlage.setGroepBijlages(groepBijlages);

        bijlageRepository.opslaanGroepBijlages(groepBijlages);
        bijlageRepository.opslaan(newArrayList(bijlage));

        Bijlage bijlage1 = new Bijlage();
        bijlage1.setEntiteitId(entiteitId);
        bijlage1.setSoortEntiteit(soortEntiteit);
        bijlage1.setBestandsNaam("bb.pdf");
        bijlage1.setOmschrijving("omschrijving2");
        bijlage1.setUploadMoment(LocalDateTime.now());

        bijlageRepository.opslaan(newArrayList(bijlage1));

        List<GroepBijlages> groepBijlagesList = bijlageRepository.alleGroepenBijlages(soortEntiteit, entiteitId);
        assertThat(groepBijlagesList.size(), is(1));
        assertThat(groepBijlagesList.get(0), is(groepBijlages));

        GroepBijlages gp = bijlageRepository.leesGroepBijlages(groepBijlages.getId());
        assertThat(gp, is(groepBijlages));

        bijlageRepository.verwijder(newArrayList(bijlage));
        bijlageRepository.verwijder(newArrayList(bijlage1));

        assertEquals(0, bijlageRepository.alles(soortEntiteit, entiteitId).size());
    }

    @Override
    public AbstractRepository getRepository() {
        return bijlageRepository;
    }
}