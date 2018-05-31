package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtRequest;
import nl.lakedigital.as.messaging.request.licentie.LicentieGekochtResponse;
import nl.lakedigital.djfc.messaging.sender.LicentieGekochtResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.LicentieService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class LicentieGekochtRequestRecieverTest extends EasyMockSupport {
    @TestSubject
    private LicentieGekochtRequestReciever licentieGekochtRequestReciever = new LicentieGekochtRequestReciever();

    @Mock
    private LicentieService licentieService;
    @Mock
    private LicentieGekochtResponseSender licentieGekochtResponseSender;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void verwerkMessage() {
        LicentieGekochtRequest licentieGekochtRequest = new LicentieGekochtRequest();
        licentieGekochtRequest.setKantoor(26L);
        licentieGekochtRequest.setLicentieType("brons");

        licentieService.nieuweLicentie("brons", 26L);
        expectLastCall();

        expect(licentieService.bepaalPrijs("brons")).andReturn(12.34);

        Capture<LicentieGekochtResponse> licentieGekochtResponseCapture = newCapture();
        licentieGekochtResponseSender.send(capture(licentieGekochtResponseCapture));
        expectLastCall();

        replayAll();

        licentieGekochtRequestReciever.verwerkMessage(licentieGekochtRequest);

        verifyAll();

        LicentieGekochtResponse response = licentieGekochtResponseCapture.getValue();
        assertThat(response.getKantoor(), is(26L));
        assertThat(response.getLicentieType(), is("brons"));
        assertThat(response.getPrijs(), is(12.34));
    }
}