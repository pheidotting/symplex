//package nl.dias.web;
//
//import static org.junit.Assert.assertEquals;
//import nl.dias.repository.KantoorRepository;
//import nl.dias.web.medewerker.SeleniumController;
//
//import org.easymock.EasyMock;
//import org.junit.Before;
//import org.junit.Test;
//
//public class SeleniumControllerTest {
//    private KantoorRepository kantoorService;
//    private SeleniumController controller;
//
//    @Before
//    public void setUp() throws Exception {
//        kantoorService = EasyMock.createMock(KantoorRepository.class);
//
//        controller = new SeleniumController();
//        controller.setKantoorService(kantoorService);
//        System.setProperty("omgeving", "");
//    }
//
//    @Test
//    public void leegAlles() {
//        assertEquals("false", controller.leegAlles());
//    }
//
//    @Test
//    public void leegAllesInFatOmgeving() {
//        System.setProperty("omgeving", "FAT");
//
//        kantoorService.wisAlles();
//        EasyMock.expectLastCall();
//
//        EasyMock.replay(kantoorService);
//
//        assertEquals("true", controller.leegAlles());
//
//        EasyMock.verify(kantoorService);
//    }
//
//    @Test
//    public void wachtwoord() {
//        System.setProperty("omgeving", "FAT");
//        SeleniumController.setWachtwoord("ww");
//
//        assertEquals("ww", controller.wachtwoord());
//    }
//}
