//package nl.dias.web;
//
//import static org.easymock.EasyMock.expect;
//import static org.easymock.EasyMock.isA;
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import nl.dias.domein.Bedrijf;
//import nl.dias.domein.Kantoor;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.json.JsonBedrijf;
//import nl.dias.domein.json.JsonLijstRelaties;
//import nl.dias.domein.json.JsonRelatie;
//import nl.dias.repository.KantoorRepository;
//import nl.dias.service.AuthorisatieService;
//import nl.dias.service.BedrijfService;
//import nl.dias.service.GebruikerService;
//import nl.dias.web.mapper.BedrijfMapper;
//import nl.dias.web.mapper.RelatieMapper;
//import nl.dias.web.medewerker.GebruikerController;
//
//import org.easymock.EasyMock;
//import org.easymock.EasyMockSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class GebruikerControllerTest extends EasyMockSupport {
//    private GebruikerService gebruikerService;
//    private GebruikerController controller;
//    private RelatieMapper mapper;
//    private KantoorRepository kantoorRepository;
//    private BedrijfMapper bedrijfMapper;
//    private BedrijfService bedrijfService;
//    private HttpServletRequest httpServletRequest;
//    private AuthorisatieService authorisatieService;
//
//    @Before
//    public void setUp() throws Exception {
//        controller = new GebruikerController();
//
//        gebruikerService = createMock(GebruikerService.class);
//        controller.setGebruikerService(gebruikerService);
//
//        mapper = createMock(RelatieMapper.class);
//        controller.setRelatieMapper(mapper);
//
//        kantoorRepository = createMock(KantoorRepository.class);
//        controller.setKantoorRepository(kantoorRepository);
//
//        bedrijfMapper = createMock(BedrijfMapper.class);
//        controller.setBedrijfMapper(bedrijfMapper);
//
//        bedrijfService = createMock(BedrijfService.class);
//        controller.setBedrijfService(bedrijfService);
//
//        httpServletRequest = createMock(HttpServletRequest.class);
//        controller.setHttpServletRequest(httpServletRequest);
//
//        authorisatieService = createMock(AuthorisatieService.class);
//        controller.setAuthorisatieService(authorisatieService);
//    }
//
//    @After
//    public void teardown() {
//        verifyAll();
//    }
//
//    @Test
//    public void verwijder() {
//        gebruikerService.verwijder(58L);
//        EasyMock.expectLastCall();
//
//        replayAll();
//
//        controller.verwijderen(58L);
//    }
//
//    @Test
//    public void lees() {
//        Relatie relatie = new Relatie();
//        JsonRelatie jsonRelatie = new JsonRelatie();
//
//        EasyMock.expect(gebruikerService.lees(46L)).andReturn(relatie);
//        EasyMock.expect(mapper.mapNaarJson(relatie)).andReturn(jsonRelatie);
//
//        replayAll();
//
//        assertEquals(jsonRelatie, controller.lees("46"));
//
//        EasyMock.verify(gebruikerService);
//    }
//
//    @Test
//    public void lijst() {
//        Relatie relatie = new Relatie();
//        List<Relatie> lijst = new ArrayList<>();
//        lijst.add(relatie);
//
//        JsonLijstRelaties jsonLijstRelaties = new JsonLijstRelaties();
//        JsonRelatie jsonRelatie = new JsonRelatie();
//        jsonLijstRelaties.getJsonRelaties().add(jsonRelatie);
//
//        Kantoor kantoor = new Kantoor();
//
//        EasyMock.expect(kantoorRepository.getIngelogdKantoor()).andReturn(kantoor);
//        EasyMock.expect(gebruikerService.alleRelaties(kantoor)).andReturn(lijst);
//        EasyMock.expect(mapper.mapNaarJson(relatie)).andReturn(jsonRelatie);
//
//        replayAll();
//
//        assertEquals(jsonLijstRelaties, controller.lijstRelaties(null));
//    }
//
//    @Test
//    public void lijstMetWeglaten() {
//        Relatie relatie = new Relatie();
//        relatie.setId(5L);
//        List<Relatie> lijst = new ArrayList<>();
//        lijst.add(relatie);
//
//        JsonLijstRelaties jsonLijstRelaties = new JsonLijstRelaties();
//        jsonLijstRelaties.getJsonRelaties();
//
//        Kantoor kantoor = new Kantoor();
//
//        EasyMock.expect(kantoorRepository.getIngelogdKantoor()).andReturn(kantoor);
//        EasyMock.expect(gebruikerService.alleRelaties(kantoor)).andReturn(lijst);
//
//        replayAll();
//
//        assertEquals(jsonLijstRelaties, controller.lijstRelaties("5"));
//    }
//
//    @Test
//    public void opslaan() {
//        HttpSession httpSession = createMock(HttpSession.class);
//        expect(httpServletRequest.getSession()).andReturn(httpSession);
//        expect(httpSession.getAttribute("sessie")).andReturn(null);
//        expect(httpServletRequest.getRemoteAddr()).andReturn("remoteAddr");
//
//        Medewerker medewerker = createMock(Medewerker.class);
//        expect(authorisatieService.getIngelogdeGebruiker(httpServletRequest, null, "remoteAddr")).andReturn(medewerker);
//        Kantoor kantoor = createMock(Kantoor.class);
//        expect(medewerker.getKantoor()).andReturn(kantoor);
//        expect(kantoor.getId()).andReturn(58L);
//        expect(kantoorRepository.lees(58L)).andReturn(kantoor);
//
//        Relatie relatie = new Relatie();
//        JsonRelatie jsonRelatie = new JsonRelatie();
//
//        EasyMock.expect(mapper.mapVanJson(jsonRelatie)).andReturn(relatie);
//        gebruikerService.opslaan(isA(Relatie.class));
//        EasyMock.expectLastCall();
//
//        replayAll();
//
//        assertEquals(202, controller.opslaan(jsonRelatie).getStatus());
//    }
//
//    @Test
//    public void opslaanBedrijf() {
//        JsonBedrijf jsonBedrijf = new JsonBedrijf();
//        jsonBedrijf.setRelatie("1");
//        Bedrijf bedrijf = new Bedrijf();
//
//        Relatie relatie = new Relatie();
//
//        EasyMock.expect(gebruikerService.lees(1L)).andReturn(relatie);
//        EasyMock.expect(bedrijfMapper.mapVanJson(jsonBedrijf)).andReturn(bedrijf);
//
//        bedrijf.setRelatie(relatie);
//
//        bedrijfService.opslaan(bedrijf);
//        EasyMock.expectLastCall();
//        gebruikerService.opslaan(relatie);
//        EasyMock.expectLastCall();
//
//        replayAll();
//
//        controller.opslaanBedrijf(jsonBedrijf);
//    }
//
//    @Test
//    public void testZoekOpNaamAdresOfPolisNummer() {
//        String zoekTerm = "zoek";
//
//        List<Relatie> relaties = new ArrayList<>();
//        List<JsonRelatie> jsonRelaties = new ArrayList<>();
//
//        expect(gebruikerService.zoekOpNaamAdresOfPolisNummer(zoekTerm)).andReturn(relaties);
//        expect(mapper.mapAllNaarJson(relaties)).andReturn(jsonRelaties);
//
//        replayAll();
//
//        // assertEquals(jsonRelaties,
//        // controller.zoekOpNaamAdresOfPolisNummer(zoekTerm));
//    }
//
//    // @Test
//    // public void toevoegenRelatieRelatie() {
//    // Medewerker medewerker = new Medewerker();
//    // medewerker.setId(2L);
//    // medewerker.setKantoor(new Kantoor());
//    //
//    // Relatie relatieAanToevoegen = new Relatie();
//    // relatieAanToevoegen.setGeboorteDatum(new LocalDate(2013, 11, 3));
//    // relatieAanToevoegen.setOverlijdensdatum(new LocalDate(2013, 11, 3));
//    // Relatie relatieToeTeVoegen = new Relatie();
//    // relatieToeTeVoegen.setGeboorteDatum(new LocalDate(2013, 11, 3));
//    // relatieToeTeVoegen.setOverlijdensdatum(new LocalDate(2013, 11, 3));
//    //
//    // EasyMock.expect(gebruikerService.lees(2L)).andReturn(relatieAanToevoegen).times(2);
//    // EasyMock.expect(gebruikerService.lees(3L)).andReturn(relatieToeTeVoegen);
//    //
//    // relatieAanToevoegen.getOnderlingeRelaties().add(new
//    // OnderlingeRelatie(relatieAanToevoegen, relatieToeTeVoegen,
//    // OnderlingeRelatieSoort.K));
//    //
//    // gebruikerService.opslaan(relatieAanToevoegen);
//    // EasyMock.expectLastCall();
//    //
//    // EasyMock.replay(gebruikerService);
//    //
//    // String verwacht =
//    // "{\"geboorteDatumString\":\"03-11-2013\",\"overlijdensdatumString\":\"03-11-2013\",\"onderlingeRelaties\":[{\"soort\":\"Kind\",\"metWie\":\"null null\"},{\"soort\":\"Ouder\",\"metWie\":\"null null\"}],\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}";
//    // String gekregen = controller.toevoegenRelatieRelatie("2", "3", "O");
//    //
//    // if (!verwacht.equals(gekregen)) {
//    // verwacht =
//    // "{\"geboorteDatumString\":\"03-11-2013\",\"overlijdensdatumString\":\"03-11-2013\",\"onderlingeRelaties\":[{\"soort\":\"Ouder\",\"metWie\":\"null null\"},{\"soort\":\"Kind\",\"metWie\":\"null null\"}],\"telefoonNummers\":[],\"rekeningnummers\":[],\"opmerkingen\":[],\"polissen\":[]}";
//    // }
//    // assertEquals(verwacht, gekregen);
//    //
//    // EasyMock.verify(gebruikerService);
//    // }
//    //
//    // @Test
//    // public void toevoegenRelatieRelatieNumeriekFoutEen() {
//    // assertEquals("\"Numeriek veld verwacht\"",
//    // controller.toevoegenRelatieRelatie("a", "3", "O"));
//    // }
//    //
//    // @Test
//    // public void toevoegenRelatieRelatieNumeriekFoutTwee() {
//    // assertEquals("\"Numeriek veld verwacht\"",
//    // controller.toevoegenRelatieRelatie("1", "b", "O"));
//    // }
//    //
//    // @Test
//    // public void toevoegenRelatieRelatieSoortFoutEen() {
//    // assertEquals("\"De soort Relatie moet worden ingevuld.\"",
//    // controller.toevoegenRelatieRelatie("1", "2", null));
//    // }
//    //
//    // @Test
//    // public void toevoegenRelatieRelatieSoortFoutTwee() {
//    // assertEquals("\"De soort Relatie moet worden ingevuld.\"",
//    // controller.toevoegenRelatieRelatie("1", "2", ""));
//    // }
//    //
//    // @Test
//    // public void inloggen() {
//    // //
//    // // EasyMock.expect(response.getWriter()).andReturn(out);
//    // //
//    // //
//    // EasyMock.expect(request.getParameter("emailadres")).andReturn("a@b.c");
//    // //
//    // EasyMock.expect(request.getParameter("wachtwoord")).andReturn("wachtwoord");
//    // // EasyMock.expect(request.getParameter("onthouden")).andReturn("true");
//    // // EasyMock.expect(request.getRemoteAddr()).andReturn("ipadres");
//    // // EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
//    //
//    // Medewerker medewerker = new Medewerker();
//    // medewerker.setId(2L);
//    // medewerker.setIdentificatie("a@b.c");
//    // medewerker.setVoornaam("A");
//    // medewerker.setHashWachtwoord("wachtwoord");
//    //
//    // @SuppressWarnings("serial")
//    // Onderwerp onderwerp = new Onderwerp() {
//    // };
//    // onderwerp.setIdentificatie("a@b.c");
//    // onderwerp.setHashWachtwoord("wachtwoord");
//    //
//    // try {
//    // EasyMock.expect(gebruikerService.zoek("a@b.c")).andReturn(medewerker);
//    // } catch (NietGevondenException e) {
//    // }
//    //
//    // // inloggen.setCookieCode("cookieCode");
//    // // Cookie cookie = new Cookie("inloggen", "cookieCode");
//    // // cookie.setDomain("dias");
//    // // inloggen.setCookie(cookie);
//    // // response.addCookie(cookie);
//    // // EasyMock.expectLastCall();
//    //
//    // try {
//    // inlogUtil.inloggen(onderwerp, null, medewerker);
//    // EasyMock.expectLastCall();
//    // } catch (LeegVeldException | NietGevondenException |
//    // OnjuistWachtwoordException e) {
//    // }
//    //
//    // HttpSession sessie = EasyMock.createMock(HttpSession.class);
//    // EasyMock.expect(request.getSession()).andReturn(sessie).times(2);
//    //
//    // EasyMock.expect(request.getHeader("user-agent")).andReturn("agent");
//    //
//    // EasyMock.expect(sessie.getId()).andReturn("11").times(2);
//    //
//    // EasyMock.expect(request.getRemoteAddr()).andReturn("12345").times(2);
//    //
//    // // sessie.setAttribute("ingelogdeGebruiker", 58);
//    // // EasyMock.expectLastCall();
//    //
//    // gebruikerService.opslaan(medewerker);
//    // EasyMock.expectLastCall();
//    //
//    // //
//    // out.println("{\"kantoornaam\":\"B\",\"voornaam\":\"A\",\"idKantoor\":\"58\",\"idMedewerker\":\"46\"}");
//    // // EasyMock.expectLastCall();
//    //
//    // // out.close();
//    // // EasyMock.expectLastCall();
//    //
//    // EasyMock.replay(gebruikerService, request, sessie);
//    //
//    // assertEquals("\"ok\"", controller.inloggen("a@b.c", "wachtwoord",
//    // "false", request));
//    //
//    // EasyMock.verify(gebruikerService, request, sessie);
//    // }
//    //
//    // @Test
//    // public void isIngelogd() throws Exception {
//    // controller = PowerMock.createPartialMock(GebruikerController.class,
//    // "checkIngelogd");
//    // controller.setGebruikerService(gebruikerService);
//    //
//    // PowerMock.expectPrivate(controller, "checkIngelogd", request);
//    //
//    // EasyMock.replay(request);
//    // PowerMock.replayAll();
//    //
//    // controller.isIngelogd(request);
//    //
//    // EasyMock.verify(request);
//    // PowerMock.verifyAll();
//    // }
//
// }
