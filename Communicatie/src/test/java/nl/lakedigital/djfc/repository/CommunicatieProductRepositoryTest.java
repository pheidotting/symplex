package nl.lakedigital.djfc.repository;

import nl.lakedigital.djfc.commons.domain.CommunicatieProduct;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.UitgaandeBrief;
import nl.lakedigital.djfc.commons.domain.UitgaandeEmail;
import nl.lakedigital.djfc.inloggen.Sessie;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class CommunicatieProductRepositoryTest {
    @Inject
    private CommunicatieProductRepository communicatieProductRepository;

    @Before
    public void init() {
        Sessie.setIngelogdeGebruiker(46L);
    }

    @Test
    public void testOpslaanUitgaandeEmail() throws Exception {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 3L;

        UitgaandeEmail email = new UitgaandeEmail();
        email.setEntiteitId(entiteitId);
        email.setSoortEntiteit(soortEntiteit);
        email.setEmailadres("hetemailadres");

        voerTestUit(email, soortEntiteit, entiteitId);
    }

    @Test
    public void testOpslaanUitgaandeBrief() throws Exception {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 3L;

        UitgaandeBrief brief = new UitgaandeBrief();
        brief.setEntiteitId(entiteitId);
        brief.setSoortEntiteit(soortEntiteit);
        brief.setTekst("fjooaisjfisajfowjofijweojifow");

        voerTestUit(brief, soortEntiteit, entiteitId);
    }

    private void voerTestUit(CommunicatieProduct communicatieProduct, SoortEntiteit soortEntiteit, Long entiteitId) {
        communicatieProductRepository.opslaan(communicatieProduct);

        List<CommunicatieProduct> lijst = communicatieProductRepository.alles(soortEntiteit, entiteitId);
        assertThat(lijst.size(), is(1));
        assertThat(lijst.get(0).getDatumTijdCreatie(), is(notNullValue()));

        lijst = communicatieProductRepository.alles(soortEntiteit, entiteitId);
        assertThat(lijst.size(), is(1));
        assertThat(lijst.get(0).getDatumTijdCreatie(), is(notNullValue()));

        communicatieProductRepository.verwijder(communicatieProduct);

        lijst = communicatieProductRepository.alles(soortEntiteit, entiteitId);
        assertThat(lijst.size(), is(0));
    }

    @Test
    public void leesOnverzondenEmails() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 3L;

        assertThat(communicatieProductRepository.alles(soortEntiteit, entiteitId).size(), is(0));

        UitgaandeEmail uitgaandeEmailOnverzonden = new UitgaandeEmail();
        uitgaandeEmailOnverzonden.setSoortEntiteit(soortEntiteit);
        uitgaandeEmailOnverzonden.setEntiteitId(entiteitId);
        UitgaandeEmail uitgaandeEmailVerzonden = new UitgaandeEmail();
        uitgaandeEmailVerzonden.setSoortEntiteit(soortEntiteit);
        uitgaandeEmailVerzonden.setEntiteitId(entiteitId);
        UitgaandeBrief uitgaandeBriefOnverzonden = new UitgaandeBrief();
        uitgaandeBriefOnverzonden.setSoortEntiteit(soortEntiteit);
        uitgaandeBriefOnverzonden.setEntiteitId(entiteitId);

        uitgaandeEmailVerzonden.setDatumTijdVerzending(LocalDateTime.now());

        communicatieProductRepository.opslaan(uitgaandeEmailOnverzonden);
        communicatieProductRepository.opslaan(uitgaandeEmailVerzonden);
        communicatieProductRepository.opslaan(uitgaandeBriefOnverzonden);

        assertThat(communicatieProductRepository.alles(soortEntiteit, entiteitId).size(), is(3));

        List<UitgaandeEmail> onverzondenMails = communicatieProductRepository.leesOnverzondenEmails();
        assertThat(onverzondenMails.size(), is(1));
        assertThat(onverzondenMails.get(0), is(uitgaandeEmailOnverzonden));

        communicatieProductRepository.verwijder(uitgaandeEmailOnverzonden);
        communicatieProductRepository.verwijder(uitgaandeEmailVerzonden);
        communicatieProductRepository.verwijder(uitgaandeBriefOnverzonden);

        assertThat(communicatieProductRepository.alles(soortEntiteit, entiteitId).size(), is(0));
    }
}