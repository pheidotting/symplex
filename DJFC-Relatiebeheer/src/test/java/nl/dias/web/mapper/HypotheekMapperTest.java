//package nl.dias.web.mapper;
//
//import nl.dias.domein.Bedrag;
//import nl.dias.domein.Hypotheek;
//import nl.dias.domein.Relatie;
//import nl.dias.domein.SoortHypotheek;
//import nl.lakedigital.djfc.commons.json.JsonHypotheek;
//import org.easymock.EasyMockSupport;
//import org.joda.time.LocalDate;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.math.BigDecimal;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class HypotheekMapperTest extends EasyMockSupport {
//    private HypotheekMapper mapper;
//    private Hypotheek hypotheek;
//    private JsonHypotheek jsonHypotheek;
//    private OpmerkingMapper opmerkingMapper;
//    private BijlageMapper bijlageMapper;
//
//    @Before
//    public void setUp() throws Exception {
//        mapper = new HypotheekMapper();
//
//        opmerkingMapper = createMock(OpmerkingMapper.class);
//        mapper.setOpmerkingMapper(opmerkingMapper);
//
//        bijlageMapper = createMock(BijlageMapper.class);
//        mapper.setBijlageMapper(bijlageMapper);
//
//        SoortHypotheek soortHypotheek = new SoortHypotheek();
//        soortHypotheek.setOmschrijving("soortHypotheek");
//        soortHypotheek.setId(2L);
//
//        Relatie relatie = new Relatie();
//        relatie.setId(46L);
//
//        hypotheek = new Hypotheek();
//        hypotheek.setDuur(1L);
//        hypotheek.setDuurRenteVastePeriode(2L);
//        hypotheek.setEindDatum(new LocalDate(2014, 2, 1));
//        hypotheek.setEindDatumRenteVastePeriode(new LocalDate(2014, 2, 3));
//        hypotheek.setHypotheekBedrag(new Bedrag("4325.56"));
//        hypotheek.setHypotheekVorm(soortHypotheek);
//        hypotheek.setId(3L);
//        hypotheek.setIngangsDatum(new LocalDate(2014, 3, 4));
//        hypotheek.setIngangsDatumRenteVastePeriode(new LocalDate(2014, 4, 5));
//        hypotheek.setKoopsom(new Bedrag("123.45"));
//        hypotheek.setMarktWaarde(new Bedrag("234.56"));
//        hypotheek.setOmschrijving("omschrijving");
//        hypotheek.setOnderpand("Onderpand");
//        hypotheek.setRelatie(relatie);
//        hypotheek.setRente(new BigDecimal("3"));
//        hypotheek.setTaxatieDatum(new LocalDate(2014, 5, 6));
//        hypotheek.setVrijeVerkoopWaarde(new Bedrag(456.78));
//        hypotheek.setWaardeNaVerbouwing(new Bedrag(567.89));
//        hypotheek.setWaardeVoorVerbouwing(new Bedrag(789.01));
//        hypotheek.setWozWaarde(new Bedrag(678.90));
//        hypotheek.setBank("naamBank");
//
//        jsonHypotheek = new JsonHypotheek();
//        // jsonHypotheek.setDuur(1L);
//        // jsonHypotheek.setDuurRenteVastePeriode(2L);
//        // jsonHypotheek.setEindDatum("01-02-2014");
//        // jsonHypotheek.setEindDatumRenteVastePeriode("03-02-2014");
//        // jsonHypotheek.setHypotheekBedrag("4325.56");
//        // jsonHypotheek.setHypotheekVorm("2");
//        // jsonHypotheek.setId(3L);
//        // jsonHypotheek.setIngangsDatum("04-03-2014");
//        // jsonHypotheek.setIngangsDatumRenteVastePeriode("05-04-2014");
//        // jsonHypotheek.setKoopsom("123.45");
//        // jsonHypotheek.setMarktWaarde("234.56");
//        // jsonHypotheek.setOmschrijving("omschrijving");
//        // jsonHypotheek.setOnderpand("Onderpand");
//        // jsonHypotheek.setRelatie(46L);
//        // // jsonHypotheek.setRente("3,1");
//        // jsonHypotheek.setTaxatieDatum("06-05-2014");
//        // jsonHypotheek.setVrijeVerkoopWaarde("456.78");
//        // jsonHypotheek.setWaardeNaVerbouwing("567.89");
//        // jsonHypotheek.setWaardeVoorVerbouwing("789.01");
//        // jsonHypotheek.setWozWaarde("678.9");
//        // jsonHypotheek.setOpmerkingen(new ArrayList<JsonOpmerking>());
//        // jsonHypotheek.setBijlages(new ArrayList<JsonBijlage>());
//        // jsonHypotheek.setBank("naamBank");
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        // verifyAll();
//    }
//
//    @Test
//    public void testMapVanJson() {
//        // jsonHypotheek.setHypotheekVorm(null);
//        // jsonHypotheek.setRelatie(null);
//
//        hypotheek.setHypotheekVorm(null);
//        hypotheek.setRelatie(null);
//
//        assertEquals(hypotheek, mapper.mapVanJson(jsonHypotheek));
//    }
//
//}
