//package nl.dias.repository;
//
//import nl.dias.domein.VerzekeringsMaatschappij;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class VerzekeringsMaatschappijRepositoryTest {
//    private VerzekeringsMaatschappijRepository verzekeringsMaatschappijRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        verzekeringsMaatschappijRepository = new VerzekeringsMaatschappijRepository();
//        verzekeringsMaatschappijRepository.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void zoekOpNaam() {
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("naam1");
//        VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
//        maatschappij1.setNaam("naam2");
//
//        verzekeringsMaatschappijRepository.opslaan(maatschappij);
//        verzekeringsMaatschappijRepository.opslaan(maatschappij1);
//
//        assertEquals(maatschappij.getId(), verzekeringsMaatschappijRepository.zoekOpNaam("naam1").getId());
//        assertEquals(maatschappij1.getId(), verzekeringsMaatschappijRepository.zoekOpNaam("naam2").getId());
//    }
//
//    @Test
//    public void alles() {
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("aa");
//        maatschappij.setTonen(true);
//        VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
//        maatschappij1.setNaam("cc");
//        maatschappij1.setTonen(false);
//        VerzekeringsMaatschappij maatschappij2 = new VerzekeringsMaatschappij();
//        maatschappij2.setNaam("bb");
//        maatschappij2.setTonen(true);
//
//        verzekeringsMaatschappijRepository.opslaan(maatschappij1);
//        verzekeringsMaatschappijRepository.opslaan(maatschappij);
//        verzekeringsMaatschappijRepository.opslaan(maatschappij2);
//
//        List<VerzekeringsMaatschappij> lijst = verzekeringsMaatschappijRepository.alles();
//        assertEquals(3, lijst.size());
//    }
//
//}
