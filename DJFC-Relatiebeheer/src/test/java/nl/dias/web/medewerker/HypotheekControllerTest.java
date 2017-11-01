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
//import nl.dias.domein.Bank;
//import nl.dias.domein.Hypotheek;
//import nl.dias.domein.HypotheekPakket;
//import nl.dias.domein.SoortHypotheek;
//import nl.dias.domein.json.JsonBank;
//import nl.dias.domein.json.JsonHypotheek;
//import nl.dias.domein.json.JsonHypotheekPakket;
//import nl.dias.domein.json.JsonSoortHypotheek;
//import nl.dias.service.BankService;
//import nl.dias.service.HypotheekService;
//import nl.dias.web.mapper.BankMapper;
//import nl.dias.web.mapper.HypotheekMapper;
//import nl.dias.web.mapper.HypotheekPakketMapper;
//import nl.dias.web.mapper.SoortHypotheekMapper;
//
//import org.easymock.EasyMockSupport;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//public class HypotheekControllerTest extends EasyMockSupport {
//    private HypotheekService hypotheekService;
//    private SoortHypotheekMapper soortHypotheekMapper;
//    private HypotheekMapper hypotheekMapper;
//    private HypotheekPakketMapper hypotheekPakketMapper;
//    private BankService bankService;
//    private BankMapper bankMapper;
//    private HypotheekController controller;
//
//    @Before
//    public void setUp() throws Exception {
//        controller = new HypotheekController();
//
//        hypotheekService = createMock(HypotheekService.class);
//        controller.setHypotheekService(hypotheekService);
//
//        soortHypotheekMapper = createMock(SoortHypotheekMapper.class);
//        controller.setSoortHypotheekMapper(soortHypotheekMapper);
//
//        hypotheekMapper = createMock(HypotheekMapper.class);
//        controller.setHypotheekMapper(hypotheekMapper);
//
//        hypotheekPakketMapper = createMock(HypotheekPakketMapper.class);
//        controller.setHypotheekPakketMapper(hypotheekPakketMapper);
//
//        bankService = createMock(BankService.class);
//        controller.setBankService(bankService);
//
//        bankMapper = createMock(BankMapper.class);
//        controller.setBankMapper(bankMapper);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        verifyAll();
//    }
//
//    @Test
//    public void testLeesNull() {
//        replayAll();
//
//        assertEquals(new JsonHypotheek(), controller.lees(null));
//    }
//
//    @Test
//    public void testLeesLeeg() {
//        replayAll();
//
//        assertEquals(new JsonHypotheek(), controller.lees(""));
//    }
//
//    @Test
//    public void testLeesGetal() {
//        replayAll();
//
//        assertEquals(new JsonHypotheek(), controller.lees("0"));
//    }
//
//    @Test
//    public void testLeesNul() {
//        JsonHypotheek jsonHypotheek = createMock(JsonHypotheek.class);
//        Hypotheek hypotheek = createMock(Hypotheek.class);
//
//        expect(hypotheekService.leesHypotheek(1L)).andReturn(hypotheek);
//        expect(hypotheekMapper.mapNaarJson(hypotheek)).andReturn(jsonHypotheek);
//
//        replayAll();
//
//        assertEquals(new JsonHypotheek(), controller.lees("1"));
//    }
//
//    @Test
//    public void testAlleSoortenHypotheek() {
//        List<SoortHypotheek> soorten = new ArrayList<>();
//        List<JsonSoortHypotheek> jsonSoorten = new ArrayList<>();
//        Set<SoortHypotheek> soortenSet = new HashSet<>();
//
//        expect(hypotheekService.alleSoortenHypotheekInGebruik()).andReturn(soorten);
//        expect(soortHypotheekMapper.mapAllNaarJson(soortenSet)).andReturn(jsonSoorten);
//
//        replayAll();
//
//        assertEquals(jsonSoorten, controller.alleSoortenHypotheek());
//    }
//
//    @Test
//    public void testAlleHypotheken() {
//        Long relatieId = 58L;
//        List<Hypotheek> hypotheken = new ArrayList<>();
//        List<JsonHypotheek> jsonHypotheken = new ArrayList<>();
//        Set<Hypotheek> hypothekenSet = new HashSet<>();
//
//        expect(hypotheekService.allesVanRelatie(relatieId)).andReturn(hypotheken);
//        expect(hypotheekMapper.mapAllNaarJson(hypothekenSet)).andReturn(jsonHypotheken);
//
//        replayAll();
//
//        assertEquals(jsonHypotheken, controller.alleHypotheken(relatieId));
//    }
//
//    @Test
//    public void alleHypothekenInclDePakketten() {
//        Long relatieId = 58L;
//        List<Hypotheek> hypotheken = new ArrayList<>();
//        List<JsonHypotheek> jsonHypotheken = new ArrayList<>();
//        Set<Hypotheek> hypothekenSet = new HashSet<>();
//
//        expect(hypotheekService.allesVanRelatieInclDePakketten(relatieId)).andReturn(hypotheken);
//        expect(hypotheekMapper.mapAllNaarJson(hypothekenSet)).andReturn(jsonHypotheken);
//
//        replayAll();
//
//        assertEquals(jsonHypotheken, controller.alleHypothekenInclDePakketten(relatieId));
//    }
//
//    @Test
//    public void testAlleHypotheekPakketten() {
//        Long relatieId = 58L;
//        List<HypotheekPakket> hypotheekPakketten = new ArrayList<>();
//        List<JsonHypotheekPakket> jsonHypotheekPakketten = new ArrayList<>();
//        Set<HypotheekPakket> hypotheekPakketSet = new HashSet<>();
//
//        expect(hypotheekService.allePakketenVanRelatie(relatieId)).andReturn(hypotheekPakketten);
//        expect(hypotheekPakketMapper.mapAllNaarJson(hypotheekPakketSet)).andReturn(jsonHypotheekPakketten);
//
//        replayAll();
//
//        assertEquals(jsonHypotheekPakketten, controller.alleHypotheekPakketten(relatieId));
//    }
//
//    @Test
//    public void testOpslaan() {
//        Hypotheek hypotheek = createMock(Hypotheek.class);
//        JsonHypotheek jsonHypotheek = createMock(JsonHypotheek.class);
//
//        expect(jsonHypotheek.getId()).andReturn(2L).times(3);
//        expect(hypotheekService.leesHypotheek(2L)).andReturn(hypotheek);
//
//        expect(hypotheekMapper.mapVanJson(jsonHypotheek, hypotheek)).andReturn(hypotheek);
//        expect(jsonHypotheek.getHypotheekVorm()).andReturn("hypotheekVorm");
//        expect(jsonHypotheek.getRelatie()).andReturn(1L);
//        expect(jsonHypotheek.getBankId()).andReturn(2L);
//        expect(jsonHypotheek.getGekoppeldeHypotheek()).andReturn(null);
//        hypotheekService.opslaan(hypotheek, "hypotheekVorm", 1L, null, 2L);
//        expectLastCall();
//
//        expect(hypotheek.getId()).andReturn(46L);
//
//        replayAll();
//
//        assertEquals(200, controller.opslaan(jsonHypotheek).getStatus());
//    }
//
//    @Test
//    public void testOpslaanMetNulJson() {
//        Hypotheek hypotheek = createMock(Hypotheek.class);
//        JsonHypotheek jsonHypotheek = createMock(JsonHypotheek.class);
//
//        expect(jsonHypotheek.getId()).andReturn(null);
//
//        expect(hypotheekMapper.mapVanJson(jsonHypotheek, new Hypotheek())).andReturn(hypotheek);
//        expect(jsonHypotheek.getHypotheekVorm()).andReturn("hypotheekVorm");
//        expect(jsonHypotheek.getRelatie()).andReturn(1L);
//        expect(jsonHypotheek.getBankId()).andReturn(2L);
//        expect(jsonHypotheek.getGekoppeldeHypotheek()).andReturn(null);
//        hypotheekService.opslaan(hypotheek, "hypotheekVorm", 1L, null, 2L);
//        expectLastCall();
//
//        expect(hypotheek.getId()).andReturn(46L);
//
//        replayAll();
//
//        assertEquals(200, controller.opslaan(jsonHypotheek).getStatus());
//    }
//
//    @Test
//    public void alleBanken() {
//        List<Bank> bankenList = new ArrayList<Bank>();
//        Set<Bank> bankenSet = new HashSet<Bank>();
//        List<JsonBank> jsonBanken = new ArrayList<JsonBank>();
//
//        expect(bankService.alles()).andReturn(bankenList);
//        expect(bankMapper.mapAllNaarJson(bankenSet)).andReturn(jsonBanken);
//
//        replayAll();
//
//        assertEquals(jsonBanken, controller.alleBanken());
//    }
//    // @Test
//    // public void testOpslaanMetException() {
//    // Hypotheek hypotheek = createMock(Hypotheek.class);
//    // JsonHypotheek jsonHypotheek = createMock(JsonHypotheek.class);
//    //
//    // expect(hypotheekMapper.mapVanJson(jsonHypotheek)).andReturn(hypotheek);
//    // expect(jsonHypotheek.getHypotheekVorm()).andReturn("hypotheekVorm");
//    // expect(jsonHypotheek.getRelatie()).andReturn(1L);
//    // hypotheekService.opslaan(hypotheek, "hypotheekVorm", 1L);
//    // expectLastCall().andThrow(new NullPointerException());
//    //
//    // replayAll();
//    //
//    // assertEquals(500, controller.opslaan(jsonHypotheek).getStatus());
//    // }
// }
