package nl.dias.service;

import nl.dias.domein.Schade;
import nl.dias.domein.SoortSchade;
import nl.dias.domein.StatusSchade;
import nl.dias.domein.polis.ArbeidsongeschiktheidVerzekeringParticulier;
import nl.dias.domein.polis.Polis;
import nl.dias.repository.SchadeRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;

@Ignore
@RunWith(EasyMockRunner.class)
public class SchadeServiceTest extends EasyMockSupport {
    @TestSubject
    private SchadeService service = new SchadeService();
    @Mock
    private SchadeRepository schadeRepository;
    @Mock
    private PolisService polisService;

    @Test
    public void testSoortenSchade() {
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> lijst = new ArrayList<SoortSchade>();
        lijst.add(soortSchade);

        expect(schadeRepository.soortenSchade()).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.soortenSchade());

        verifyAll();
    }

    @Test
    public void testSoortenSchadeString() {
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> lijst = new ArrayList<SoortSchade>();
        lijst.add(soortSchade);

        expect(schadeRepository.soortenSchade("omschr")).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.soortenSchade("omschr"));

        verifyAll();
    }

    @Test
    public void opslaanMetEnum() {
        String soortSchadeString = "soortSchade";
        String polisId = "46";
        String statusSchadeString = "statusSchade";

        Schade schade = createMock(Schade.class);
        SoortSchade soortSchade = createMock(SoortSchade.class);
        List<SoortSchade> soortSchades = new ArrayList<>();
        soortSchades.add(soortSchade);
        StatusSchade statusSchade = createMock(StatusSchade.class);

        schade.setPolis(Long.valueOf(polisId));
        expectLastCall();

        expect(schadeRepository.soortenSchade(soortSchadeString)).andReturn(soortSchades);
        schade.setSoortSchade(soortSchade);
        expectLastCall();

        expect(schadeRepository.getStatussen(statusSchadeString)).andReturn(statusSchade);
        schade.setStatusSchade(statusSchade);
        expectLastCall();

        schadeRepository.opslaan(schade);
        expectLastCall();

        replayAll();

        service.opslaan(schade, soortSchadeString, polisId, statusSchadeString);

        verifyAll();
    }

    @Test
    public void opslaanZonderEnum() {
        String soortSchadeString = "soortSchade";
        String polisId = "46";
        String statusSchadeString = "statusSchade";

        Schade schade = createMock(Schade.class);
        List<SoortSchade> soortSchades = new ArrayList<>();
        StatusSchade statusSchade = createMock(StatusSchade.class);
        schade.setPolis(Long.valueOf(polisId));
        expectLastCall();

        expect(schadeRepository.soortenSchade(soortSchadeString)).andReturn(soortSchades);
        schade.setSoortSchadeOngedefinieerd(soortSchadeString);
        expectLastCall();

        expect(schadeRepository.getStatussen(statusSchadeString)).andReturn(statusSchade);
        schade.setStatusSchade(statusSchade);
        expectLastCall();

        schadeRepository.opslaan(schade);
        expectLastCall();

        replayAll();

        service.opslaan(schade, soortSchadeString, polisId, statusSchadeString);

        verifyAll();
    }

    @Test
    public void testGetStatussen() {
        StatusSchade statusSchade = new StatusSchade();
        String zoekTerm = "zoekStatus";

        expect(schadeRepository.getStatussen(zoekTerm)).andReturn(statusSchade);

        replayAll();

        assertEquals(statusSchade, service.getStatussen(zoekTerm));

        verifyAll();
    }

    @Test
    public void testGetStatussenString() {
        List<StatusSchade> statussenSchade = new ArrayList<>();

        expect(schadeRepository.getStatussen()).andReturn(statussenSchade);

        replayAll();

        assertEquals(statussenSchade, service.getStatussen());

        verifyAll();
    }

    @Test
    public void testZoekOpSchadeNummerMaatschappij() {
        Schade schade = new Schade();
        String schadeNummerMaatschappij = "schadeNummerMaatschappij";

        expect(schadeRepository.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij)).andReturn(schade);

        replayAll();

        assertEquals(schade, service.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij));

        verifyAll();
    }

    @Test
    public void testVerwijder() {
        Schade schade = new Schade();
        Long id = 46L;

        expect(schadeRepository.lees(id)).andReturn(schade);
        schadeRepository.verwijder(schade);
        expectLastCall();

        replayAll();

        service.verwijder(id);

        verifyAll();
    }

    @Test
    public void testAlleSchadesBijRelatie() {
        List<Polis> polissen = new ArrayList<>();
        Polis polis = new ArbeidsongeschiktheidVerzekeringParticulier();
        polis.setId(8L);
        polissen.add(polis);

        List<Schade> lijst = new ArrayList<>();
        Long relatie = 7L;

        expect(polisService.allePolissenBijRelatie(relatie)).andReturn(polissen);
        expect(schadeRepository.allesBijPolis(polis.getId())).andReturn(lijst);

        replayAll();

        service.alleSchadesBijRelatie(relatie);

        verifyAll();
    }
}
