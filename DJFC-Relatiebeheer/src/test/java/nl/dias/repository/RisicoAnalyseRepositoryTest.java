//package nl.dias.repository;
//
//import nl.dias.domein.Bedrijf;
//import nl.dias.domein.RisicoAnalyse;
//import org.easymock.EasyMockSupport;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class RisicoAnalyseRepositoryTest extends EasyMockSupport {
//    private RisicoAnalyseRepository risicoAnalyseRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        risicoAnalyseRepository = new RisicoAnalyseRepository();
//        risicoAnalyseRepository.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void opslaan() {
//        RisicoAnalyse risicoAnalyse = new RisicoAnalyse();
//        risicoAnalyseRepository.opslaan(risicoAnalyse);
//
//        assertEquals(1, risicoAnalyseRepository.alles().size());
//    }
//
//    @Test
//    public void testAllesBijBedrijf() throws Exception {
//        Bedrijf bedrijf = new Bedrijf();
//
//        risicoAnalyseRepository.getEm().getTransaction().begin();
//        risicoAnalyseRepository.getEm().persist(bedrijf);
//        risicoAnalyseRepository.getEm().getTransaction().commit();
//
//        RisicoAnalyse risicoAnalyse = new RisicoAnalyse();
//        risicoAnalyse.setBedrijf(bedrijf);
//
//        risicoAnalyseRepository.opslaan(risicoAnalyse);
//
//        assertEquals(risicoAnalyse, risicoAnalyseRepository.leesBijBedrijf(bedrijf));
//    }
//}