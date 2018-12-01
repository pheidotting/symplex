package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.ControleerLicentieRequest;
import nl.lakedigital.djfc.commons.domain.AdministratieKantoor;
import nl.lakedigital.djfc.commons.domain.Licentie;
import nl.lakedigital.djfc.messaging.sender.ControleerLicentieResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.LicentieService;
import org.easymock.*;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public class ControleerLicentieRequestRecieverTest extends EasyMockSupport {
    @TestSubject
    private ControleerLicentieRequestReciever controleerLicentieRequestReciever = new ControleerLicentieRequestReciever();

    @Mock
    private LicentieService licentieService;
    @Mock
    private ControleerLicentieResponseSender controleerLicentieResponseSender;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void verwerkMessageWelEenDag() {
        ControleerLicentieRequest controleerLicentieRequest = new ControleerLicentieRequest();
        controleerLicentieRequest.setKantoorId(9L);

        controleerLicentieResponseSender.setLicentieService(licentieService);
        expectLastCall();

        Licentie licentie = new AdministratieKantoor();
        expect(licentieService.actieveLicentie(9L)).andReturn(licentie);

        LocalDate datum = LocalDate.now().plusDays(1);
        expect(licentieService.actieveLicentie(licentie)).andReturn(datum).times(3);

        controleerLicentieResponseSender.send(licentie);
        expectLastCall();

        replayAll();

        controleerLicentieRequestReciever.verwerkMessage(controleerLicentieRequest);

        verifyAll();
    }

    @Test
    public void verwerkMessageWelZesDagen() {
        ControleerLicentieRequest controleerLicentieRequest = new ControleerLicentieRequest();
        controleerLicentieRequest.setKantoorId(9L);

        controleerLicentieResponseSender.setLicentieService(licentieService);
        expectLastCall();

        Licentie licentie = new AdministratieKantoor();
        expect(licentieService.actieveLicentie(9L)).andReturn(licentie);

        LocalDate datum = LocalDate.now().plusDays(6);
        expect(licentieService.actieveLicentie(licentie)).andReturn(datum).times(3);

        controleerLicentieResponseSender.send(licentie);
        expectLastCall();

        replayAll();

        controleerLicentieRequestReciever.verwerkMessage(controleerLicentieRequest);

        verifyAll();
    }

    @Test
    public void verwerkMessageWelZevenDagen() {
        ControleerLicentieRequest controleerLicentieRequest = new ControleerLicentieRequest();
        controleerLicentieRequest.setKantoorId(9L);

        controleerLicentieResponseSender.setLicentieService(licentieService);
        expectLastCall();

        Licentie licentie = new AdministratieKantoor();
        expect(licentieService.actieveLicentie(9L)).andReturn(licentie);

        LocalDate datum = LocalDate.now().plusDays(7);
        expect(licentieService.actieveLicentie(licentie)).andReturn(datum).times(2);

        replayAll();

        controleerLicentieRequestReciever.verwerkMessage(controleerLicentieRequest);

        verifyAll();
    }

    @Test
    public void verwerkMessageWelAchtDagen() {
        ControleerLicentieRequest controleerLicentieRequest = new ControleerLicentieRequest();
        controleerLicentieRequest.setKantoorId(9L);

        controleerLicentieResponseSender.setLicentieService(licentieService);
        expectLastCall();

        Licentie licentie = new AdministratieKantoor();
        expect(licentieService.actieveLicentie(9L)).andReturn(licentie);

        LocalDate datum = LocalDate.now().plusDays(8);
        expect(licentieService.actieveLicentie(licentie)).andReturn(datum).times(2);

        replayAll();

        controleerLicentieRequestReciever.verwerkMessage(controleerLicentieRequest);

        verifyAll();
    }
}