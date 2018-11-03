//package nl.dias.web;
//
//import static org.junit.Assert.assertEquals;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.VerzekeringsMaatschappij;
//import nl.dias.domein.json.JsonPolis;
//import nl.dias.domein.polis.MotorVerzekering;
//import nl.dias.domein.polis.Polis;
//import nl.dias.service.GebruikerService;
//import nl.dias.service.PolisService;
//import nl.dias.web.medewerker.PolisController;
//
//import org.easymock.EasyMock;
//import org.easymock.EasyMockSupport;
//import org.joda.time.LocalDate;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//@Ignore
//public class PolisControllerTest extends EasyMockSupport {
//    private PolisController controller;
//    // private HttpServletRequest request;
//
//    private PolisService polisService;
//    private GebruikerService gebruikerService;
//
//    @Before
//    public void setUp() throws Exception {
//        controller = new PolisController();
//
//        polisService = createMock(PolisService.class);
//        controller.setPolisService(polisService);
//
//        gebruikerService = createMock(GebruikerService.class);
//        controller.setGebruikerService(gebruikerService);
//    }
//
//    @After
//    public void teardown() {
//        verifyAll();
//    }
//
//    @Test
//    public void testAutoVerzekering() {
//        VerzekeringsMaatschappij verzekeringsMaatschappij = new VerzekeringsMaatschappij();
//        verzekeringsMaatschappij.setNaam("naamVerzekeringsMaatschappij");
//
//        Relatie relatie = new Relatie();
//
//        JsonPolis opslaanPolis = new JsonPolis();
//        opslaanPolis.setBetaalfrequentie("Maandelijks");
//        opslaanPolis.setIngangsDatum("2013-02-01");
//        opslaanPolis.setMaatschappij("naamVerzekeringsMaatschappij");
//
//        // Polis polis = new AutoVerzekering();
//        // polis.setPolisNummer("polisNummer");
//        // ((AutoVerzekering)
//        // polis).setSoortAutoVerzekering(SoortAutoVerzekering.OLDTIMER);
//        // ((AutoVerzekering) polis).setKenteken("kenteken");
//        // // polis.setIngangsDatum(new LocalDate(2013, 2, 1));
//        // polis.setPremie(new Bedrag("100"));
//        // polis.setMaatschappij(verzekeringsMaatschappij);
//        // polis.setRelatie(relatie);
//
//        // polisService.opslaan(polis);
//        // EasyMock.expectLastCall();
//
//        EasyMock.expect(gebruikerService.lees(2L)).andReturn(relatie);
//
//        replayAll();
//
//        // assertEquals("\"ok\"", controller.opslaan("bestaandeMaatschappij",
//        // "Auto", "polisNummer", "Oldtimer", "kenteken", null, "01-02-2013",
//        // "100", "2"));
//        assertEquals("\"ok\"", controller.opslaan(opslaanPolis));
//
//        verifyAll();
//    }
//
//    @Test
//    public void testMotorVerzekering() {
//        VerzekeringsMaatschappij verzekeringsMaatschappij = new VerzekeringsMaatschappij();
//        verzekeringsMaatschappij.setNaam("naamVerzekeringsMaatschappij");
//
//        Relatie relatie = new Relatie();
//
//        Polis polis = new MotorVerzekering();
//        polis.setPolisNummer("polisNummer");
//        polis.setIngangsDatum(new LocalDate(2013, 2, 1));
//        polis.setMaatschappij(verzekeringsMaatschappij);
//        polis.setRelatie(relatie);
//
//        polisService.opslaan(polis);
//        EasyMock.expectLastCall();
//
//        EasyMock.expect(gebruikerService.lees(2L)).andReturn(relatie);
//
//        replayAll();
//
//        // assertEquals("\"ok\"", controller.opslaan("bestaandeMaatschappij",
//        // "Motor", "polisNummer", null, "kenteken", "true", "01-02-2013", null,
//        // "2"));
//
//        verifyAll();
//    }
//
//    // @Test
//    // public void testAllePolissen() {
//    // VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//    // maatschappij.setNaam("Naam");
//    //
//    // Relatie relatie = new Relatie();
//    //
//    // Bedrijf bedrijf1 = new Bedrijf();
//    // bedrijf1.setRelatie(relatie);
//    // Bedrijf bedrijf2 = new Bedrijf();
//    // bedrijf2.setRelatie(relatie);
//    //
//    // relatie.getBedrijven().add(bedrijf1);
//    // relatie.getBedrijven().add(bedrijf2);
//    //
//    // AutoVerzekering autoVerzekering = new AutoVerzekering();
//    // autoVerzekering.setRelatie(relatie);
//    // autoVerzekering.setMaatschappij(maatschappij);
//    // WoonhuisVerzekering woonhuisVerzekering = new WoonhuisVerzekering();
//    // woonhuisVerzekering.setBedrijf(bedrijf1);
//    // woonhuisVerzekering.setMaatschappij(maatschappij);
//    // OngevallenVerzekering ongevallenVerzekering = new
//    // OngevallenVerzekering();
//    // ongevallenVerzekering.setBedrijf(bedrijf2);
//    // ongevallenVerzekering.setMaatschappij(maatschappij);
//    //
//    // relatie.getPokketten().add(autoVerzekering);
//    // bedrijf1.getPokketten().add(woonhuisVerzekering);
//    // bedrijf2.getPokketten().add(ongevallenVerzekering);
//    //
//    // EasyMock.expect(gebruikerService.lees(1L)).andReturn(relatie);
//    //
//    // List<Polis> lijst = new ArrayList<>();
//    // lijst.add(autoVerzekering);
//    // lijst.add(woonhuisVerzekering);
//    // lijst.add(ongevallenVerzekering);
//    //
//    // EasyMock.expect(polisService.allePolissenVanRelatieEnZijnBedrijf(relatie)).andReturn(lijst);
//    // lijst.get(0).setRelatie(null);
//    // lijst.get(1).setBedrijf(null);
//    // lijst.get(2).setBedrijf(null);
//    //
//    // // Gson gson = new Gson();
//    //
//    // replayAll();
//    //
//    // // assertEquals(gson.toJson(lijst), controller.allePolissen("1"));
//    // assertNull(controller.allePolissen("1"));
//    //
//    // verifyAll();
//    // }
//}
