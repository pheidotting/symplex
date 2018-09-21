package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.VerwijderEntiteitService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public class VerwijderEntiteitRequestRecieverTest extends EasyMockSupport {
    @TestSubject
    private VerwijderEntiteitRequestReciever verwijderEntiteitRequestReciever = new VerwijderEntiteitRequestReciever();

    @Mock
    private VerwijderEntiteitService verwijderEntiteitService;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void verwerkMessage() {
        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
        soortEntiteitEnEntiteitId.setEntiteitId(6L);
        soortEntiteitEnEntiteitId.setSoortEntiteit(SoortEntiteit.OPMERKING);

        VerwijderEntiteitRequest verwijderEntiteitRequest = new VerwijderEntiteitRequest();
        verwijderEntiteitRequest.setSoortEntiteitEnEntiteitId(soortEntiteitEnEntiteitId);

        verwijderEntiteitService.verwijderen(SoortEntiteit.OPMERKING, 6L);
        expectLastCall();

        replayAll();

        verwijderEntiteitRequestReciever.verwerkMessage(verwijderEntiteitRequest);

        verifyAll();
    }
}