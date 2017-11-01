//package nl.dias.web.medewerker;
//
//import nl.dias.domein.Opmerking;
//import nl.dias.service.OpmerkingService;
//import nl.dias.web.mapper.OpmerkingMapper;
//import nl.lakedigital.djfc.commons.json.JsonOpmerking;
//import org.easymock.EasyMockSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.expectLastCall;
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class OpmerkingControllerTest extends EasyMockSupport {
//    private OpmerkingController controller;
//    private OpmerkingService opmerkingService;
//    private OpmerkingMapper opmerkingMapper;
//    private Opmerking opmerking;
//    private JsonOpmerking jsonOpmerking;
//
//    @Before
//    public void setUp() throws Exception {
//        controller = new OpmerkingController();
//
//        opmerkingMapper = createMock(OpmerkingMapper.class);
//        controller.setOpmerkingMapper(opmerkingMapper);
//
//        opmerkingService = createMock(OpmerkingService.class);
//        controller.setOpmerkingService(opmerkingService);
//
//        opmerking = createMock(Opmerking.class);
//        jsonOpmerking = createMock(JsonOpmerking.class);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        verifyAll();
//    }
//
//    @Test
//    public void testOpslaan() {
//        expect(opmerkingMapper.mapVanJson(jsonOpmerking)).andReturn(opmerking);
//        opmerkingService.opslaan(opmerking);
//        expectLastCall();
//
//        expect(opmerking.getId()).andReturn(46L);
//
//        replayAll();
//
//        assertEquals(new Long(46), new Long(controller.opslaan(jsonOpmerking)));
//    }
//
//    @Test
//    public void testOpslaanMetException() {
//        expect(opmerkingMapper.mapVanJson(jsonOpmerking)).andReturn(opmerking);
//        opmerkingService.opslaan(opmerking);
//        expectLastCall().andThrow(new IllegalArgumentException());
//
//        replayAll();
//
//        controller.opslaan(jsonOpmerking);
//    }
//
//    @Test
//    public void testNieuw() {
//        replayAll();
//
//        assertEquals(new JsonOpmerking(), controller.nieuw());
//    }
//
//}
