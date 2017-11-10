package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.*;
import nl.lakedigital.djfc.domain.particulier.AutoVerzekering;
import nl.lakedigital.djfc.repository.SchadeRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class SchadeServiceTest extends EasyMockSupport {
    @TestSubject
    private SchadeService service = new SchadeService();
    @Mock
    private SchadeRepository schadeRepository;
    @Mock
    private PolisService polisService;
    @Mock
    private IdentificatieClient identificatieClient;

    @Test
    public void alles() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        Polis polis = new AutoVerzekering(soortEntiteit, entiteitId);
        polis.setId(4L);
        List<Polis> polissen = new ArrayList<>();
        polissen.add(polis);

        expect(polisService.alles(soortEntiteit, entiteitId)).andReturn(polissen);

        Schade schade = new Schade();
        List<Schade> schades = new ArrayList<>();
        schades.add(schade);
        expect(schadeRepository.alleSchades(polis.getId())).andReturn(schades);

        replayAll();

        assertThat(service.alles(soortEntiteit, entiteitId), is(schades));

        verifyAll();
    }

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
    public void test() {
        Schade schade = new Schade();

        schadeRepository.opslaan(schade);
        expectLastCall();

        replayAll();

        service.opslaan(schade);

        verifyAll();
    }

    @Test
    public void zoekOpSchadeNummerMaatschappij() {
        Schade schade = new Schade();
        String zoek = "abc";

        expect(schadeRepository.zoekOpSchadeNummerMaatschappij(zoek)).andReturn(newArrayList(schade));

        replayAll();

        assertThat(service.zoekOpSchadeNummerMaatschappij(zoek), is(newArrayList(schade)));

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

        expect(schadeRepository.soortenSchade(soortSchadeString)).andReturn(soortSchades);
        schade.setSoortSchade(soortSchade);
        expectLastCall();

        expect(schadeRepository.getStatussen(statusSchadeString)).andReturn(statusSchade);
        schade.setStatusSchade(statusSchade);
        expectLastCall();

        schade.setPolis(Long.valueOf(polisId));
        expectLastCall();

        schadeRepository.opslaan(schade);
        expectLastCall();

        Identificatie identificatie = new Identificatie();
        identificatie.setEntiteitId(46L);
        expect(identificatieClient.zoekIdentificatieCode(polisId)).andReturn(identificatie);

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
        //        Polis polis = createMock(Polis.class);
        StatusSchade statusSchade = createMock(StatusSchade.class);

        expect(schadeRepository.soortenSchade(soortSchadeString)).andReturn(soortSchades);
        schade.setSoortSchadeOngedefinieerd(soortSchadeString);
        expectLastCall();

        expect(schadeRepository.getStatussen(statusSchadeString)).andReturn(statusSchade);
        schade.setStatusSchade(statusSchade);
        expectLastCall();

        schade.setPolis(Long.valueOf(polisId));
        expectLastCall();

        Identificatie identificatie = new Identificatie();
        identificatie.setEntiteitId(46L);
        expect(identificatieClient.zoekIdentificatieCode(polisId)).andReturn(identificatie);

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

    //    @Test
    //    public void testZoekOpSchadeNummerMaatschappij() {
    //        Schade schade = new Schade();
    //        String schadeNummerMaatschappij = "schadeNummerMaatschappij";
//
    //        expect(schadeRepository.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij)).andReturn(schade);
//
    //        replayAll();
//
    //        assertEquals(schade, service.zoekOpSchadeNummerMaatschappij(schadeNummerMaatschappij));
//
    //        verifyAll();
    //    }

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

//    @Test
//    public void testAlleSchadesBijRelatie() {
//        List<Schade> lijst = new ArrayList<>();
//        Relatie relatie = new Relatie();
//
//        expect(schadeRepository.alleSchadesBijRelatie(relatie)).andReturn(lijst);
//
//        replayAll();
//
//        service.alleSchadesBijRelatie(relatie);
//
//        verifyAll();
//    }
}
