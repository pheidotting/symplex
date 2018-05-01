package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.domain.Kantoor;
import nl.lakedigital.as.messaging.request.KantoorAangemeldRequest;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.LicentieService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public class KantoorAangemeldRequestRecieverTest extends EasyMockSupport {
    @TestSubject
    private KantoorAangemeldRequestReciever kantoorAangemeldRequestReciever = new KantoorAangemeldRequestReciever();

    @Mock
    private LicentieService licentieService;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void verwerkMessage() {
        Kantoor kantoor = new Kantoor();

        KantoorAangemeldRequest kantoorAangemeldRequest = new KantoorAangemeldRequest();
        kantoorAangemeldRequest.setKantoor(kantoor);

        licentieService.maakTrialAan(kantoor);
        expectLastCall();

        replayAll();

        kantoorAangemeldRequestReciever.verwerkMessage(kantoorAangemeldRequest);

        verifyAll();
    }
}