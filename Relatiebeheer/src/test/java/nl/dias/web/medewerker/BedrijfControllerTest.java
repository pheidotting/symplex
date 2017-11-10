//package nl.dias.web.medewerker;
//
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.expectLastCall;
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import nl.dias.domein.Bedrijf;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.json.JsonBedrijf;
//import nl.dias.service.BedrijfService;
//import nl.dias.service.GebruikerService;
//import nl.dias.web.mapper.BedrijfMapper;
//
//import org.easymock.EasyMockSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//@Ignore
//public class BedrijfControllerTest extends EasyMockSupport {
//    private BedrijfController controller;
//    private BedrijfService bedrijfService;
//    private GebruikerService gebruikerService;
//    private BedrijfMapper bedrijfMapper;
//    private Bedrijf bedrijf;
//    private JsonBedrijf jsonBedrijf;
//    private Relatie relatie;
//
//    @Before
//    public void setUp() throws Exception {
//        controller = new BedrijfController();
//
//        bedrijfService = createMock(BedrijfService.class);
//        controller.setBedrijfService(bedrijfService);
//
//        gebruikerService = createMock(GebruikerService.class);
//        controller.setGebruikerService(gebruikerService);
//
//        bedrijfMapper = createMock(BedrijfMapper.class);
//        controller.setBedrijfMapper(bedrijfMapper);
//
//        bedrijf = createMock(Bedrijf.class);
//        jsonBedrijf = createMock(JsonBedrijf.class);
//        relatie = createMock(Relatie.class);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        verifyAll();
//    }
//
//    @Test
//    public void testLees() {
//        Long id = 46L;
//
//        expect(bedrijfService.lees(id)).andReturn(bedrijf);
//        expect(bedrijfMapper.mapNaarJson(bedrijf)).andReturn(jsonBedrijf);
//
//        replayAll();
//
//        assertEquals(jsonBedrijf, controller.lees(id.toString()));
//    }
//
//    @Test
//    public void testLijst() {
//        List<Bedrijf> bedrijven = new ArrayList<>();
//        bedrijven.add(bedrijf);
//        Set<Bedrijf> bedrijvenSet = new HashSet<>();
//        bedrijvenSet.add(bedrijf);
//        List<JsonBedrijf> jsonBedrijven = new ArrayList<>();
//        jsonBedrijven.add(jsonBedrijf);
//
//        expect(bedrijfService.alles()).andReturn(bedrijven);
//        expect(bedrijfMapper.mapAllNaarJson(bedrijvenSet)).andReturn(jsonBedrijven);
//
//        replayAll();
//
//        controller.lijst();
//    }
//
//    @Test
//    public void testVerwijder() {
//        Long id = 34L;
//
//        bedrijfService.verwijder(id);
//        expectLastCall();
//
//        replayAll();
//
//        assertEquals(202, controller.verwijder(id).getStatus());
//    }
//
//    @Test
//    public void testVerwijderMetException() {
//        Long id = 34L;
//
//        bedrijfService.verwijder(id);
//        expectLastCall().andThrow(new IllegalArgumentException());
//
//        replayAll();
//
//        assertEquals(500, controller.verwijder(id).getStatus());
//    }
//}
