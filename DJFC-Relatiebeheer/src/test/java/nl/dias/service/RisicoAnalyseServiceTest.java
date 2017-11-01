package nl.dias.service;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.RisicoAnalyse;
import nl.dias.repository.RisicoAnalyseRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class RisicoAnalyseServiceTest extends EasyMockSupport {
    @TestSubject
    private RisicoAnalyseService service = new RisicoAnalyseService();
    @Mock
    private RisicoAnalyseRepository risicoAnalyseRepository;
    @Mock
    private BedrijfService bedrijfService;

    @Test
    public void testLees() throws Exception {
        Long id = 46L;

        RisicoAnalyse risicoAnalyse = new RisicoAnalyse();

        expect(risicoAnalyseRepository.lees(id)).andReturn(risicoAnalyse);

        replayAll();

        assertEquals(risicoAnalyse, service.lees(id));

        verifyAll();
    }

    @Test
    public void testLeesBijBedrijf() throws Exception {
        Long bedrijfsId = 58L;

        Bedrijf bedrijf = new Bedrijf();
        RisicoAnalyse risicoAnalyse = new RisicoAnalyse();

        expect(bedrijfService.lees(bedrijfsId)).andReturn(bedrijf);
        expect(risicoAnalyseRepository.leesBijBedrijf(bedrijf)).andReturn(risicoAnalyse);

        replayAll();

        assertEquals(risicoAnalyse, service.leesBijBedrijf(bedrijfsId));

        verifyAll();
    }
}