//package nl.dias.web;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import nl.dias.domein.VerzekeringsMaatschappij;
//import nl.dias.service.VerzekeringsMaatschappijService;
//import nl.dias.web.medewerker.JsonController;
//
//import org.easymock.EasyMock;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class JsonControllerTest {
//    private JsonController controller;
//    private VerzekeringsMaatschappijService maatschappijService;
//
//    @Before
//    public void setUp() throws Exception {
//        controller = new JsonController();
//
//        maatschappijService = EasyMock.createMock(VerzekeringsMaatschappijService.class);
//        controller.setMaatschappijService(maatschappijService);
//    }
//
//    @After
//    public void tearDown() {
//        System.setProperty("omgeving", "");
//    }
//
//    @Test
//    public void lijstVerzekeringsMaatschappijen() {
//        List<VerzekeringsMaatschappij> lijst = new ArrayList<>();
//        VerzekeringsMaatschappij vm1 = new VerzekeringsMaatschappij();
//        vm1.setNaam("Naam1");
//        lijst.add(vm1);
//        VerzekeringsMaatschappij vm2 = new VerzekeringsMaatschappij();
//        vm2.setNaam("Naam2");
//        lijst.add(vm2);
//
//        List<String> verwacht = new ArrayList<>();
//        verwacht.add("Kies een maatschappij...");
//        verwacht.add("Naam1");
//        verwacht.add("Naam2");
//
//        EasyMock.expect(maatschappijService.alles()).andReturn(lijst);
//
//        EasyMock.replay(maatschappijService);
//
//        assertEquals(verwacht, controller.lijstVerzekeringsMaatschappijen());
//
//        EasyMock.verify(maatschappijService);
//    }
//
//    @Test
//    public void extraInfoFAT() {
//        System.setProperty("omgeving", "FAT");
//
//        assertEquals("FAT", controller.extraInfo());
//    }
//
//    @Test
//    public void extraInfoGAT() {
//        System.setProperty("omgeving", "GAT");
//
//        assertEquals("GAT", controller.extraInfo());
//    }
//
//    @Test
//    public void extraInfoPRD() {
//        System.setProperty("omgeving", "PRD");
//
//        assertEquals("PRD", controller.extraInfo());
//    }
//
//}
