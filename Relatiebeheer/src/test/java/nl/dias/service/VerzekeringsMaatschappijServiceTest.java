package nl.dias.service;

import nl.dias.domein.VerzekeringsMaatschappij;
import nl.dias.repository.VerzekeringsMaatschappijRepository;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VerzekeringsMaatschappijServiceTest extends EasyMockSupport {
    private VerzekeringsMaatschappijService service;
    private VerzekeringsMaatschappijRepository repository;

    @Before
    public void setUp() throws Exception {
        service = new VerzekeringsMaatschappijService();

        repository = createMock(VerzekeringsMaatschappijRepository.class);
        service.setVerzekeringsMaatschappijRepository(repository);
    }

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
