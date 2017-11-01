//package nl.dias.repository;
//
//import nl.dias.domein.Aangifte;
//import nl.dias.domein.Medewerker;
//import nl.dias.domein.Relatie;
//import org.joda.time.LocalDate;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import javax.inject.Inject;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class AangifteRepositoryTest {//extends  DatabaseTest{
//    @Inject
//    private AangifteRepository aangifteRepository;
//
//    //    @Before
//    //    public void setUp() throws Exception {
//    //        aangifteRepository = new AangifteRepository();
//    //        aangifteRepository.zetPersistenceContext("unittest");
//    //    }
//
//    @Test
//    public void testGetOpenAngiftes() {
//        Medewerker medewerker = new Medewerker();
//
//        Relatie relatie = new Relatie();
//
//        aangifteRepository.getEm().getTransaction().begin();
//        aangifteRepository.getEm().persist(medewerker);
//        aangifteRepository.getEm().persist(relatie);
//        aangifteRepository.getEm().getTransaction().commit();
//
//        Aangifte aangifte = new Aangifte();
//        aangifte.setDatumAfgerond(LocalDate.now());
//        aangifte.setJaar(2013);
//        aangifte.setAfgerondDoor(medewerker);
//        aangifte.setRelatie(relatie);
//
//        aangifteRepository.opslaan(aangifte);
//
//        assertEquals(1, aangifteRepository.alles().size());
//        assertEquals(0, aangifteRepository.getOpenAngiftes(relatie).size());
//
//        aangifte.reset();
//        aangifteRepository.opslaan(aangifte);
//
//        assertEquals(1, aangifteRepository.alles().size());
//        assertEquals(1, aangifteRepository.getOpenAngiftes(relatie).size());
//
//        aangifte.afhandelen(medewerker);
//        aangifteRepository.opslaan(aangifte);
//
//        assertEquals(1, aangifteRepository.alles().size());
//        assertEquals(0, aangifteRepository.getOpenAngiftes(relatie).size());
//
//        assertEquals(1, aangifteRepository.getAlleAngiftes(relatie).size());
//
//        assertEquals(1, aangifteRepository.getGeslotenAngiftes(relatie).size());
//    }
//}
