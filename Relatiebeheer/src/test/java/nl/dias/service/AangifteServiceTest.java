package nl.dias.service;

import nl.dias.domein.Aangifte;
import nl.dias.domein.Medewerker;
import nl.dias.domein.Relatie;
import nl.dias.repository.AangifteRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class AangifteServiceTest extends EasyMockSupport {
    @TestSubject
    private AangifteService aangifteService = new AangifteService();
    @Mock
    private AangifteRepository aangifteRepository;

    @After
    public void cleanUp() {
        verifyAll();
    }

    @Test
    public void testIsAangifteAanwezigVoorHuidigJaarMinEen() {
        Relatie relatie = new Relatie();

        List<Aangifte> aangiftesZonderHuidigJaarMinusEen = new ArrayList<Aangifte>();
        List<Aangifte> aangiftesMetHuidigJaarMinusEen = new ArrayList<Aangifte>();

        Aangifte aangifte1 = new Aangifte();
        aangifte1.setJaar(LocalDate.now().minusYears(2).getYear());
        Aangifte aangifte2 = new Aangifte();
        aangifte2.setJaar(LocalDate.now().minusYears(1).getYear());
        Aangifte aangifte3 = new Aangifte();
        aangifte3.setJaar(LocalDate.now().getYear());

        aangiftesZonderHuidigJaarMinusEen.add(aangifte1);
        aangiftesZonderHuidigJaarMinusEen.add(aangifte3);

        aangiftesMetHuidigJaarMinusEen.add(aangifte1);
        aangiftesMetHuidigJaarMinusEen.add(aangifte2);
        aangiftesMetHuidigJaarMinusEen.add(aangifte3);

        expect(aangifteRepository.getAlleAngiftes(relatie)).andReturn(aangiftesMetHuidigJaarMinusEen);

        replayAll();

        assertTrue(aangifteService.isAangifteAanwezigVoorHuidigJaarMinEen(relatie));

        verifyAll();
        resetAll();

        expect(aangifteRepository.getAlleAngiftes(relatie)).andReturn(aangiftesZonderHuidigJaarMinusEen);

        replayAll();

        assertFalse(aangifteService.isAangifteAanwezigVoorHuidigJaarMinEen(relatie));
    }

    @Test
    public void testGetOpenstaandeAangiftes() {
        Relatie relatie = new Relatie();

        List<Aangifte> aangiftes = new ArrayList<Aangifte>();
        List<Aangifte> aangiftesMetHuidigJaarMinusEen = new ArrayList<Aangifte>();

        Aangifte aangifteHuidigJaarMinusEen = new Aangifte();
        aangifteHuidigJaarMinusEen.setJaar(LocalDate.now().minusYears(1).getYear());
        aangiftesMetHuidigJaarMinusEen.add(aangifteHuidigJaarMinusEen);

        expect(aangifteRepository.getOpenAngiftes(relatie)).andReturn(aangiftes);
        expect(aangifteRepository.getAlleAngiftes(relatie)).andReturn(aangiftes);

        replayAll();

        assertEquals(aangiftesMetHuidigJaarMinusEen, aangifteService.getOpenstaandeAangiftes(relatie));

        verifyAll();
        resetAll();

        expect(aangifteRepository.getOpenAngiftes(relatie)).andReturn(aangiftes);

        replayAll();

        assertEquals(aangiftes, aangifteService.getOpenstaandeAangiftes(relatie));

        verifyAll();
        resetAll();

        expect(aangifteRepository.getOpenAngiftes(relatie)).andReturn(aangiftesMetHuidigJaarMinusEen);

        replayAll();

        assertEquals(aangiftesMetHuidigJaarMinusEen, aangifteService.getOpenstaandeAangiftes(relatie));

        verifyAll();
        resetAll();

        expect(aangifteRepository.getOpenAngiftes(relatie)).andReturn(aangiftes);

        replayAll();

        assertEquals(aangiftesMetHuidigJaarMinusEen, aangifteService.getOpenstaandeAangiftes(relatie));
    }

    @Test
    public void testGetAfgeslotenAangiftes() {
        List<Aangifte> aangiftes = new ArrayList<Aangifte>();
        Relatie relatie = new Relatie();

        expect(aangifteRepository.getGeslotenAngiftes(relatie)).andReturn(aangiftes);

        replayAll();

        assertEquals(aangiftes, aangifteService.getAfgeslotenAangiftes(relatie));
    }

    @Test
    public void testAfronden() {
        Medewerker medewerker = new Medewerker();
        medewerker.setVoornaam("Hendrik");
        medewerker.setAchternaam("Haverkamp");

        Long id = 46L;
        LocalDate datum = new LocalDate(2014, 2, 4);
        Aangifte aangifte = new Aangifte();
        Aangifte aangifteMetDatumAfgerond = new Aangifte();
        aangifte.setDatumAfgerond(datum);
        aangifteMetDatumAfgerond.setAfgerondDoor(medewerker);

        expect(aangifteRepository.lees(id)).andReturn(aangifte);

        aangifteRepository.opslaan(aangifteMetDatumAfgerond);
        expectLastCall();

        replayAll();

        aangifteService.afronden(id, datum, 3L);
    }
}
