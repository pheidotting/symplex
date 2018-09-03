//package nl.dias.web.mapper;
//
//import nl.dias.domein.*;
//import nl.dias.domein.polis.Polis;
//import nl.lakedigital.djfc.commons.json.*;
//import org.easymock.EasyMockRunner;
//import org.easymock.EasyMockSupport;
//import org.easymock.Mock;
//import org.easymock.TestSubject;
//import org.joda.time.LocalDate;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.easymock.EasyMock.expect;
//import static org.junit.Assert.assertEquals;
//
//@RunWith(EasyMockRunner.class)
//public class RelatieMapperTest extends EasyMockSupport {
//    @TestSubject
//    private RelatieMapper mapper = new RelatieMapper();
//
//    @Mock
//    private TelefoonnummerMapper telefoonnummerMapper;
//    @Mock
//    private RekeningnummerMapper rekeningnummerMapper;
//    @Mock
//    private OpmerkingMapper opmerkingMapper;
//    @Mock
//    private AdresMapper adresMapper;
//    @Mock
//    private BijlageMapper bijlageMapper;
//
//    private Relatie relatie;
//    private JsonRelatie jsonRelatie;
//    private Set<Relatie> relaties;
//    private List<JsonRelatie> jsonRelaties;
//
//    @Before
//    public void setUp() throws Exception {
//        relatie = new Relatie();
//        relatie.setAchternaam("achternaam");
//        relatie.setVoornaam("Patrick");
//        relatie.setRoepnaam("Henk");
//        relatie.setTussenvoegsel("tussenvoegsel");
//        relatie.setGeslacht(Geslacht.M);
//        relatie.setBurgerlijkeStaat(BurgerlijkeStaat.C);
//        relatie.setGeboorteDatum(new LocalDate(2014, 2, 3));
//        relatie.setOverlijdensdatum(new LocalDate(2014, 6, 7));
//        relatie.setTelefoonnummers(new HashSet<Telefoonnummer>());
//        relatie.setPokketten(new HashSet<Polis>());
//        relatie.setRekeningnummers(new HashSet<RekeningNummer>());
//        relatie.setOpmerkingen(new HashSet<Opmerking>());
//
//        relaties = new HashSet<Relatie>();
//        relaties.add(relatie);
//
//        jsonRelatie = new JsonRelatie();
//        jsonRelatie.setBurgerlijkeStaat("Samenlevingscontract");
//        jsonRelatie.setAchternaam("achternaam");
//        jsonRelatie.setVoornaam("Patrick");
//        jsonRelatie.setRoepnaam("Henk");
//        jsonRelatie.setTussenvoegsel("tussenvoegsel");
//        jsonRelatie.setGeslacht("Man");
//        jsonRelatie.setGeboorteDatum("03-02-2014");
//        jsonRelatie.setOverlijdensdatum("07-06-2014");
//        jsonRelatie.setTelefoonnummers(new ArrayList<JsonTelefoonnummer>());
//        jsonRelatie.setRekeningnummers(new ArrayList<JsonRekeningNummer>());
//        jsonRelatie.setOpmerkingen(new ArrayList<JsonOpmerking>());
//
//        jsonRelaties = new ArrayList<JsonRelatie>();
//        jsonRelaties.add(jsonRelatie);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        verifyAll();
//    }
//
//    @Test
//    public void testMapVanJson() {
//
//        replayAll();
//
//        assertEquals(relatie, mapper.mapVanJson(jsonRelatie));
//    }
//
//    @Test
//    public void testMapAllNaarJson() {
//        expect(telefoonnummerMapper.mapAllNaarJson(new HashSet<Telefoonnummer>())).andReturn(new ArrayList<JsonTelefoonnummer>());
//        expect(rekeningnummerMapper.mapAllNaarJson(new HashSet<RekeningNummer>())).andReturn(new ArrayList<JsonRekeningNummer>());
//        expect(opmerkingMapper.mapAllNaarJson(new HashSet<Opmerking>())).andReturn(new ArrayList<JsonOpmerking>());
//        expect(adresMapper.mapAllNaarJson(new HashSet<Adres>())).andReturn(new ArrayList<JsonAdres>());
//        expect(bijlageMapper.mapAllNaarJson(new HashSet<Bijlage>())).andReturn(new ArrayList<JsonBijlage>());
//
//        replayAll();
//
//        assertEquals(jsonRelaties, mapper.mapAllNaarJson(relaties));
//    }
//
//    @Test
//    public void testMapNaarJson() {
//        expect(telefoonnummerMapper.mapAllNaarJson(new HashSet<Telefoonnummer>())).andReturn(new ArrayList<JsonTelefoonnummer>());
//        expect(rekeningnummerMapper.mapAllNaarJson(new HashSet<RekeningNummer>())).andReturn(new ArrayList<JsonRekeningNummer>());
//        expect(opmerkingMapper.mapAllNaarJson(new HashSet<Opmerking>())).andReturn(new ArrayList<JsonOpmerking>());
//        expect(adresMapper.mapAllNaarJson(new HashSet<Adres>())).andReturn(new ArrayList<JsonAdres>());
//        expect(bijlageMapper.mapAllNaarJson(new HashSet<Bijlage>())).andReturn(new ArrayList<JsonBijlage>());
//
//        replayAll();
//
//        assertEquals(jsonRelatie, mapper.mapNaarJson(relatie));
//    }
//
//}
