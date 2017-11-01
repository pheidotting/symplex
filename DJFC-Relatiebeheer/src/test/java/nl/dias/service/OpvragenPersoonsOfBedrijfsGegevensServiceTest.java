package nl.dias.service;

import nl.dias.domein.Gebruiker;
import nl.dias.domein.Hypotheek;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.ArbeidsongeschiktheidVerzekeringParticulier;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.zakelijk.ArbeidsongeschiktheidVerzekering;
import nl.lakedigital.as.messaging.domain.Bedrijf;
import nl.lakedigital.as.messaging.domain.Persoon;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class OpvragenPersoonsOfBedrijfsGegevensServiceTest extends EasyMockSupport {
    @TestSubject
    private OpvragenPersoonsOfBedrijfsGegevensService opvragenPersoonsOfBedrijfsGegevensService = new OpvragenPersoonsOfBedrijfsGegevensService();

    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private PolisService polisService;
    @Mock
    private SchadeService schadeService;
    @Mock
    private HypotheekService hypotheekService;
    @Mock
    private BedrijfService bedrijfService;

    @Test
    public void testOpvragenGegevensOpvragenPersoonOpPersoonId() throws Exception {
        Long entiteitId = 46L;
        String soortEntiteit = "RELATIE";

        Gebruiker gebruiker = new Relatie();
        gebruiker.setAchternaam("Haverkamp");
        gebruiker.setVoornaam("Hendrik");

        expect(gebruikerService.lees(entiteitId)).andReturn(gebruiker);

        replayAll();

        Persoon persoon = new Persoon();
        persoon.setAchternaam("Haverkamp");
        persoon.setVoornaam("Hendrik");

        assertThat(opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(entiteitId, soortEntiteit), is(persoon));

        verifyAll();
    }

    @Test
    public void testOpvragenGegevensOpvragenPersoonOpPolisId() throws Exception {
        Long entiteitId = 46L;
        String soortEntiteit = "POLIS";
        Long persoonsId = 58L;

        Gebruiker gebruiker = new Relatie();
        gebruiker.setAchternaam("Haverkamp");
        gebruiker.setVoornaam("Hendrik");

        Polis polis = new ArbeidsongeschiktheidVerzekeringParticulier();
        polis.setRelatie(persoonsId);

        expect(polisService.lees(entiteitId)).andReturn(polis);
        expect(gebruikerService.lees(persoonsId)).andReturn(gebruiker);

        replayAll();

        Persoon persoon = new Persoon();
        persoon.setAchternaam("Haverkamp");
        persoon.setVoornaam("Hendrik");

        assertThat(opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(entiteitId, soortEntiteit), is(persoon));

        verifyAll();
    }

    @Test
    public void testOpvragenGegevensOpvragenPersoonOpSchadeId() throws Exception {
        Long entiteitId = 46L;
        String soortEntiteit = "SCHADE";
        Long persoonsId = 58L;
        Long polisId = 34L;

        Gebruiker gebruiker = new Relatie();
        gebruiker.setAchternaam("Haverkamp");
        gebruiker.setVoornaam("Hendrik");

        Polis polis = new ArbeidsongeschiktheidVerzekeringParticulier();
        polis.setRelatie(persoonsId);
        polis.setId(polisId);

        Schade schade = new Schade();
        schade.setPolis(polisId);

        expect(schadeService.lees(entiteitId)).andReturn(schade);
        expect(polisService.lees(polisId)).andReturn(polis);
        expect(gebruikerService.lees(persoonsId)).andReturn(gebruiker);

        replayAll();

        Persoon persoon = new Persoon();
        persoon.setAchternaam("Haverkamp");
        persoon.setVoornaam("Hendrik");

        assertThat(opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(entiteitId, soortEntiteit), is(persoon));

        verifyAll();
    }

    @Test
    public void testOpvragenGegevensOpvragenPersoonOpHypotheekId() throws Exception {
        Long entiteitId = 46L;
        String soortEntiteit = "HYPOTHEEK";
        Long persoonsId = 58L;

        Gebruiker gebruiker = new Relatie();
        gebruiker.setAchternaam("Haverkamp");
        gebruiker.setVoornaam("Hendrik");
        gebruiker.setId(persoonsId);

        Hypotheek hypotheek = new Hypotheek();
        hypotheek.setRelatie((Relatie) gebruiker);

        expect(hypotheekService.leesHypotheek(entiteitId)).andReturn(hypotheek);
        expect(gebruikerService.lees(persoonsId)).andReturn(gebruiker);

        replayAll();

        Persoon persoon = new Persoon();
        persoon.setAchternaam("Haverkamp");
        persoon.setVoornaam("Hendrik");

        assertThat(opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(entiteitId, soortEntiteit), is(persoon));

        verifyAll();
    }

    @Test
    public void testOpvragenGegevensOpvragenBedrijfOpPersoonId() throws Exception {
        Long entiteitId = 46L;
        String soortEntiteit = "BEDRIJF";

        nl.dias.domein.Bedrijf bedr = new nl.dias.domein.Bedrijf();
        bedr.setNaam("Los Pollos Hermanos");
        bedr.setId(entiteitId);

        expect(bedrijfService.lees(entiteitId)).andReturn(bedr);

        replayAll();

        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("Los Pollos Hermanos");

        assertThat(opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(entiteitId, soortEntiteit), is(bedrijf));

        verifyAll();
    }

    @Test
    public void testOpvragenGegevensOpvragenBedrijfOpPolisId() throws Exception {
        Long entiteitId = 46L;
        String soortEntiteit = "POLIS";
        Long bedrijfId = 58L;

        nl.dias.domein.Bedrijf bedr = new nl.dias.domein.Bedrijf();
        bedr.setNaam("Los Pollos Hermanos");
        bedr.setId(bedrijfId);

        Polis polis = new ArbeidsongeschiktheidVerzekering();
        polis.setBedrijf(bedrijfId);

        expect(polisService.lees(entiteitId)).andReturn(polis);
        expect(bedrijfService.lees(bedrijfId)).andReturn(bedr);

        replayAll();

        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("Los Pollos Hermanos");

        assertThat(opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(entiteitId, soortEntiteit), is(bedrijf));

        verifyAll();
    }

    @Test
    public void testOpvragenGegevensOpvragenBedrijfOpSchadeId() throws Exception {
        Long entiteitId = 46L;
        String soortEntiteit = "SCHADE";
        Long bedrijfId = 58L;
        Long polisId = 34L;

        nl.dias.domein.Bedrijf bedr = new nl.dias.domein.Bedrijf();
        bedr.setNaam("Los Pollos Hermanos");
        bedr.setId(bedrijfId);

        Polis polis = new ArbeidsongeschiktheidVerzekeringParticulier();
        polis.setBedrijf(bedrijfId);
        polis.setId(polisId);

        Schade schade = new Schade();
        schade.setPolis(polisId);

        expect(schadeService.lees(entiteitId)).andReturn(schade);
        expect(polisService.lees(polisId)).andReturn(polis);
        expect(bedrijfService.lees(bedrijfId)).andReturn(bedr);

        replayAll();

        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("Los Pollos Hermanos");

        assertThat(opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(entiteitId, soortEntiteit), is(bedrijf));

        verifyAll();
    }
}