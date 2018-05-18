//package nl.dias.web.mapper;
//
//import nl.dias.domein.Bedrag;
//import nl.dias.domein.Schade;
//import nl.dias.domein.SoortSchade;
//import nl.dias.domein.StatusSchade;
//import nl.dias.domein.polis.AutoVerzekering;
//import nl.lakedigital.djfc.commons.json.JsonBijlage;
//import nl.lakedigital.djfc.commons.json.JsonOpmerking;
//import nl.lakedigital.djfc.commons.json.JsonSchade;
//import org.easymock.EasyMock;
//import org.easymock.EasyMockSupport;
//import org.joda.time.LocalDate;
//import org.joda.time.LocalDateTime;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//public class SchadeMapperTest extends EasyMockSupport {
//    private SchadeMapper mapper;
//    private OpmerkingMapper opmerkingMapper;
//    private BijlageMapper bijlageMapper;
//    private Schade schade;
//    private JsonSchade jsonSchade;
//
//    @Before
//    public void setUp() throws Exception {
//        mapper = new SchadeMapper();
//
//        opmerkingMapper = createMock(OpmerkingMapper.class);
//        mapper.setOpmerkingMapper(opmerkingMapper);
//
//        bijlageMapper = createMock(BijlageMapper.class);
//        mapper.setBijlageMapper(bijlageMapper);
//
//        schade = new Schade();
//        schade.setDatumAfgehandeld(new LocalDate(2014, 7, 1));
//        schade.setDatumMelding(new LocalDateTime(2014, 6, 30, 9, 12));
//        schade.setDatumSchade(new LocalDateTime(2014, 6, 29, 10, 23));
//        schade.setEigenRisico(new Bedrag(100.0));
//        schade.setLocatie("Ergens tussen de weg en de straat");
//        schade.setOmschrijving("Tja, toen was het ineens boem!");
//        schade.setPolis(new AutoVerzekering());
//        schade.setSchadeNummerMaatschappij("schadeNummerMaatschappij");
//        schade.setSchadeNummerTussenPersoon("schadeNummerTussenPersoon");
//        schade.getBijlages();
//        schade.getOpmerkingen();
//
//        SoortSchade soortSchade = new SoortSchade();
//        soortSchade.setOmschrijving("Diefstal");
//        schade.setSoortSchade(soortSchade);
//
//        StatusSchade statusSchade = new StatusSchade();
//        statusSchade.setStatus("statusSchade");
//        schade.setStatusSchade(statusSchade);
//
//        jsonSchade = new JsonSchade();
//        jsonSchade.setDatumAfgehandeld("01-07-2014");
//        jsonSchade.setDatumMelding("30-06-2014 09:12");
//        jsonSchade.setDatumSchade("29-06-2014 10:23");
//        jsonSchade.setEigenRisico("100.0");
//        jsonSchade.setLocatie("Ergens tussen de weg en de straat");
//        jsonSchade.setOmschrijving("Tja, toen was het ineens boem!");
//        jsonSchade.setPolis("1");
//        jsonSchade.setSchadeNummerMaatschappij("schadeNummerMaatschappij");
//        jsonSchade.setSchadeNummerTussenPersoon("schadeNummerTussenPersoon");
//        jsonSchade.setSoortSchade("Diefstal");
//        jsonSchade.setStatusSchade("statusSchade");
//        jsonSchade.getBijlages();
//        jsonSchade.getOpmerkingen();
//        jsonSchade.setPolis("1");
//
//    }
//
//    @Test
//    public void testMapVanJsonJsonSchade() {
//
//        replayAll();
//
//        Schade schadeUit = mapper.mapVanJson(jsonSchade);
//
//        assertEquals(schade.getId(), schade.getId());
//        assertEquals(schade.getDatumAfgehandeld(), schade.getDatumAfgehandeld());
//        assertEquals(schade.getDatumMelding(), schade.getDatumMelding());
//        assertEquals(schade.getDatumSchade(), schade.getDatumSchade());
//        assertEquals(schade.getEigenRisico(), schade.getEigenRisico());
//        assertEquals(schade.getLocatie(), schade.getLocatie());
//        assertEquals(schade.getOmschrijving(), schade.getOmschrijving());
//        assertEquals(schade.getSchadeNummerMaatschappij(), schade.getSchadeNummerMaatschappij());
//        assertEquals(schade.getSchadeNummerTussenPersoon(), schade.getSchadeNummerTussenPersoon());
//
//        verifyAll();
//    }
//
//    @Test
//    public void testMapNaarJsonSchade() {
//        List<JsonBijlage> bijlages = new ArrayList<>();
//        List<JsonOpmerking> opmerkingen = new ArrayList<>();
//
//        EasyMock.expect(bijlageMapper.mapAllNaarJson(schade.getBijlages())).andReturn(bijlages);
//        EasyMock.expect(opmerkingMapper.mapAllNaarJson(schade.getOpmerkingen())).andReturn(opmerkingen);
//
//        replayAll();
//
//        assertEquals(jsonSchade, mapper.mapNaarJson(schade));
//
//        verifyAll();
//    }
//
//}
