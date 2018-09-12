package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.VerzekeringsMaatschappij;
import nl.lakedigital.djfc.repository.VerzekeringsMaatschappijRepository;
import org.easymock.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(EasyMockRunner.class)
public class VerzekeringsMaatschappijServiceTest extends EasyMockSupport {
    @TestSubject
    private VerzekeringsMaatschappijService service = new VerzekeringsMaatschappijService();
    @Mock
    private VerzekeringsMaatschappijRepository repository;

    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testZoekOpNaam() {
        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();

        EasyMock.expect(repository.zoekOpNaam("naam")).andReturn(maatschappij);

        replayAll();

        assertEquals(maatschappij, service.zoekOpNaam("naam"));
    }

    @Test
    public void testAlles() {
        List<VerzekeringsMaatschappij> lijst = new ArrayList<VerzekeringsMaatschappij>();

        EasyMock.expect(repository.alles()).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.alles());
    }

}
