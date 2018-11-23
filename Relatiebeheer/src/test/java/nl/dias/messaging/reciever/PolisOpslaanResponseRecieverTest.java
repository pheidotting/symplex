//package nl.dias.messaging.reciever;
//
//import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
//import nl.lakedigital.as.messaging.domain.Opmerking;
//import nl.lakedigital.as.messaging.domain.Polis;
//import nl.lakedigital.as.messaging.domain.SoortEntiteit;
//import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
//import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
//import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
//import org.easymock.*;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//
//import java.util.UUID;
//
//import static org.easymock.EasyMock.*;
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//
//@RunWith(EasyMockRunner.class)
//public class PolisOpslaanResponseRecieverTest extends EasyMockSupport {
//    @TestSubject
//    private PolisOpslaanResponseReciever polisOpslaanResponseReciever = new PolisOpslaanResponseReciever();
//
//    @Mock
//    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;
//
//    @Test
//    public void testVerwerkMessage() throws Exception {
////        Opmerking opmerking = new Opmerking();
//        Polis polis = new Polis();
//        polis.setId(3L);
//        polis.setIdentificatie(UUID.randomUUID().toString());
////        polis.getOpmerkingen().add(opmerking);
//
//        PolisOpslaanRequest polisOpslaanRequest = new PolisOpslaanRequest();
//        polisOpslaanRequest.getPokketten().add(polis);
//
//        PolisOpslaanResponse polisOpslaanResponse = new PolisOpslaanResponse();
//        polisOpslaanResponse.getPakketten().add(polis);
//        polisOpslaanResponse.setAntwoordOp(polisOpslaanRequest);
//
//        Capture<OpslaanEntiteitenRequest> opslaanEntiteitenRequestCapture = newCapture();
//        opslaanEntiteitenRequestSender.send(capture(opslaanEntiteitenRequestCapture), isA(Logger.class));
//        expectLastCall();
//
//        replayAll();
//
//        polisOpslaanResponseReciever.verwerkMessage(polisOpslaanResponse);
//
//        verifyAll();
//
//        OpslaanEntiteitenRequest opslaanEntiteitenRequest = opslaanEntiteitenRequestCapture.getValue();
//
//        assertThat(opslaanEntiteitenRequest.getLijst().size(), is(1));
//        Opmerking opm = (Opmerking) opslaanEntiteitenRequest.getLijst().get(0);
//        assertThat(opm.getSoortEntiteit(), is(SoortEntiteit.POLIS));
//        assertThat(opm.getEntiteitId(), is(3L));
//    }
//}