package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.repository.AbstractRepository;
import nl.lakedigital.djfc.repository.OpmerkingRepository;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

public class OpmerkingServiceTest extends AbstractServiceTest<Opmerking> {
    @TestSubject
    public OpmerkingService opmerkingService = new OpmerkingService();
    @Mock
    private OpmerkingRepository opmerkingRepository;

    @Override
    public AbstractService getService() {
        return opmerkingService;
    }

    @Override
    public AbstractRepository getRepository() {
        return opmerkingRepository;
    }

    @Override
    public Opmerking getMinimaalGevuldeEntiteit() {
        Opmerking opmerking = new Opmerking();
        opmerking.setOpmerking("ABC");

        return opmerking;
    }

    @Override
    public Opmerking getGevuldeEntiteit() {
        Opmerking opmerking = new Opmerking();
        opmerking.setOpmerking("opm1");

        return opmerking;
    }

    @Override
    public Opmerking getGevuldeBestaandeEntiteit() {
        Opmerking opmerking = new Opmerking();
        opmerking.setOpmerking("opm2");
        opmerking.setId(2L);

        return opmerking;
    }

    @Override
    public Opmerking getTeVerwijderenEntiteit() {
        Opmerking opmerking = new Opmerking();
        opmerking.setOpmerking("opm3");
        opmerking.setId(3L);

        return opmerking;
    }

    @Test
    public void testVerwijder() {
        Opmerking opmerking = new Opmerking();
        Long id = 5L;

        expect(opmerkingRepository.lees(id)).andReturn(opmerking);
        opmerkingRepository.verwijder(opmerking);
        expectLastCall();

        replayAll();

        opmerkingService.verwijder(id);

        verifyAll();
    }
}