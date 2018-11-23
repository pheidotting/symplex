package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public class VerwijderEntiteitServiceTest extends EasyMockSupport {
    @TestSubject
    private VerwijderEntiteitService verwijderEntiteitService = new VerwijderEntiteitService();

    @Mock
    private OpmerkingService opmerkingService;

    @Test
    public void verwijderenOpmerking() {
        SoortEntiteit soortEntiteit = SoortEntiteit.OPMERKING;
        Long entiteitId = 6L;

        opmerkingService.verwijder(entiteitId);
        expectLastCall();

        replayAll();

        verwijderEntiteitService.verwijderen(soortEntiteit, entiteitId);

        verifyAll();
    }

    @Test
    public void verwijderenGeenOpmerking() {
        SoortEntiteit soortEntiteit = SoortEntiteit.BIJLAGE;
        Long entiteitId = 6L;

        replayAll();

        verwijderEntiteitService.verwijderen(soortEntiteit, entiteitId);

        verifyAll();
    }
}