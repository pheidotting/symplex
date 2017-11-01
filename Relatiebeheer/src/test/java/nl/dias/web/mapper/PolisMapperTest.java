//package nl.dias.web.mapper;
//
//import nl.dias.domein.*;
//import nl.dias.domein.json.JsonBijlage;
//import nl.dias.domein.json.JsonOpmerking;
//import nl.dias.domein.json.JsonPolis;
//import nl.dias.domein.json.JsonSchade;
//import nl.dias.domein.polis.Betaalfrequentie;
//import nl.dias.domein.polis.FietsVerzekering;
//import nl.dias.domein.polis.Polis;
//import nl.dias.service.GebruikerService;
//import nl.dias.service.PolisService;
//import nl.dias.service.VerzekeringsMaatschappijService;
//import org.easymock.EasyMockSupport;
//import org.joda.time.LocalDate;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.easymock.EasyMock.expect;
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class PolisMapperTest extends EasyMockSupport {
//    private PolisMapper mapper;
//    private OpmerkingMapper opmerkingMapper;
//    private BijlageMapper bijlageMapper;
//    private SchadeMapper schadeMapper;
//    private PolisService polisService;
//    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;
//    private GebruikerService gebruikerService;
//
//    private Polis polis;
//    private JsonPolis jsonPolis;
//    private Set<Polis> polissen;
//    private List<JsonPolis> jsonPolissen;
//
//    @Before
//    public void setUp() throws Exception {
//        mapper = new PolisMapper();
//
//        opmerkingMapper = createMock(OpmerkingMapper.class);
//        mapper.setOpmerkingMapper(opmerkingMapper);
//
//        bijlageMapper = createMock(BijlageMapper.class);
//        mapper.setBijlageMapper(bijlageMapper);
//
//        schadeMapper = createMock(SchadeMapper.class);
//        mapper.setSchadeMapper(schadeMapper);
//
//        polisService = createMock(PolisService.class);
//        mapper.setPolisService(polisService);
//
//        verzekeringsMaatschappijService = createMock(VerzekeringsMaatschappijService.class);
//        mapper.setVerzekeringsMaatschappijService(verzekeringsMaatschappijService);
//
//        gebruikerService = createMock(GebruikerService.class);
//        mapper.setGebruikerService(gebruikerService);
//
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("Fa. List & Bedrog");
//
//        Bedrijf bedrijf = new Bedrijf();
//        bedrijf.setNaam("TestBedrijf");
//
//        polis = new FietsVerzekering();
//        polis.setPremie(new Bedrag("12345"));
//        polis.setMaatschappij(maatschappij);
//        polis.setIngangsDatum(new LocalDate(2014, 6, 23));
//        polis.setWijzigingsDatum(new LocalDate(2014, 6, 23));
//        polis.setProlongatieDatum(new LocalDate(2014, 6, 23));
//        polis.setBetaalfrequentie(Betaalfrequentie.H);
//        polis.getBijlages();
//        polis.setBedrijf(bedrijf);
//        polis.getSchades();
//        polis.setKenmerk("kenmerkPolis");
//
//        jsonPolis = new JsonPolis();
//        jsonPolis.setPremie("12345");
//        jsonPolis.setId(2L);
//        jsonPolis.setMaatschappij("Fa. List & Bedrog");
//        jsonPolis.setIngangsDatum("23-06-2014");
//        jsonPolis.setWijzigingsDatum("23-06-2014");
//        jsonPolis.setProlongatieDatum("23-06-2014");
//        jsonPolis.setSoort("Fiets");
//        jsonPolis.setBetaalfrequentie("Half jaar");
//        jsonPolis.getBijlages();
//        jsonPolis.getOpmerkingen();
//        jsonPolis.setBedrijf("TestBedrijf");
//        jsonPolis.getSchades();
//        jsonPolis.setRelatie("1");
//        jsonPolis.setKenmerk("kenmerkPolis");
//
//        polissen = new HashSet<Polis>();
//        polissen.add(polis);
//        jsonPolissen = new ArrayList<JsonPolis>();
//        jsonPolissen.add(jsonPolis);
//    }
//
//    @After
//    public void verify() {
//        verifyAll();
//    }
//
//    @Test
//    public void testMapVanJson() {
//        VerzekeringsMaatschappij maatschappij = createMock(VerzekeringsMaatschappij.class);
//        Relatie relatie = createMock(Relatie.class);
//        Polis polis = createMock(Polis.class);
//
//        expect(polisService.definieerPolisSoort("Fiets")).andReturn(new FietsVerzekering());
//        expect(verzekeringsMaatschappijService.zoekOpNaam("Fa. List & Bedrog")).andReturn(maatschappij);
//        expect(gebruikerService.lees(1L)).andReturn(relatie);
//        expect(polisService.lees(2L)).andReturn(polis);
//        expect(polis.getSchades()).andReturn(new HashSet<Schade>());
//        expect(polis.getOpmerkingen()).andReturn(new HashSet<Opmerking>());
//
//        replayAll();
//
//        assertEquals(this.polis, mapper.mapVanJson(jsonPolis));
//    }
//
//    @Test
//    public void testMapNaarJson() {
//        List<JsonBijlage> bijlages = new ArrayList<JsonBijlage>();
//        List<JsonOpmerking> opmerkingen = new ArrayList<JsonOpmerking>();
//        List<JsonSchade> schades = new ArrayList<JsonSchade>();
//
//        expect(bijlageMapper.mapAllNaarJson(polis.getBijlages())).andReturn(bijlages);
//        expect(opmerkingMapper.mapAllNaarJson(polis.getOpmerkingen())).andReturn(opmerkingen);
//        expect(schadeMapper.mapAllNaarJson(polis.getSchades())).andReturn(schades);
//
//        replayAll();
//
//        assertEquals(jsonPolis, mapper.mapNaarJson(polis));
//    }
//}
