//package nl.dias.repository;
//
//import com.google.common.collect.Lists;
//import nl.dias.domein.Bedrijf;
//import nl.dias.domein.JaarCijfers;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class JaarCijfersRepositoryTest  {
//    private JaarCijfersRepository jaarCijfersRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        jaarCijfersRepository = new JaarCijfersRepository();
//        jaarCijfersRepository.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void opslaan(){
//        JaarCijfers jaarCijfers=new JaarCijfers();
//        jaarCijfers.setJaar(2014L);
//
//        jaarCijfersRepository.opslaan(jaarCijfers);
//
//        assertEquals(1,jaarCijfersRepository.alles().size());
//    }
//
//    @Test
//    public void testAllesBijBedrijf() throws Exception {
//        Bedrijf bedrijf=new Bedrijf();
//
//        jaarCijfersRepository.getEm().getTransaction().begin();
//        jaarCijfersRepository.getEm().persist(bedrijf);
//        jaarCijfersRepository.getEm().getTransaction().commit();
//
//        JaarCijfers jaarCijfers=new JaarCijfers();
//        jaarCijfers.setBedrijf(bedrijf);
//
//        jaarCijfersRepository.opslaan(jaarCijfers);
//
//        assertEquals(Lists.newArrayList(jaarCijfers),jaarCijfersRepository.allesBijBedrijf(bedrijf));
//    }
//}