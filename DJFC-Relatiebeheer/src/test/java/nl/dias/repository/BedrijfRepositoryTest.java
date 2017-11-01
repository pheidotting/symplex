//package nl.dias.repository;
//
//import nl.dias.domein.Adres;
//import nl.dias.domein.Bedrijf;
//import nl.dias.domein.Relatie;
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class BedrijfRepositoryTest {
//    private BedrijfRepository bedrijfRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        bedrijfRepository = new BedrijfRepository();
//        bedrijfRepository.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void test() {
//        Relatie relatie1 = new Relatie();
//        Relatie relatie2 = new Relatie();
//
//        bedrijfRepository.getEm().getTransaction().begin();
//        bedrijfRepository.getEm().persist(relatie1);
//        bedrijfRepository.getEm().persist(relatie2);
//        bedrijfRepository.getEm().getTransaction().commit();
//
//        Bedrijf bedrijf1 = new Bedrijf();
//        bedrijf1.setNaam("bedrijf1");
//        Adres adres = new Adres();
//        adres.setToevoeging("toevoeging");
//        adres.setStraat("straat");
//        adres.setPostcode("postco");
//        adres.setHuisnummer(42L);
//        adres.setPlaats("plaats");
//        bedrijf1.getAdressen().add(adres);
//        Bedrijf bedrijf2 = new Bedrijf();
//        bedrijf2.setNaam("bedrijf2");
//        Bedrijf bedrijf3 = new Bedrijf();
//        bedrijf3.setNaam("bedrijf3");
//
//        bedrijfRepository.opslaan(bedrijf1);
//        bedrijfRepository.opslaan(bedrijf2);
//        bedrijfRepository.opslaan(bedrijf3);
//
//        bedrijf3.setKvk("kvk");
//        bedrijfRepository.opslaan(bedrijf3);
//
//        bedrijf3.setKvk("kvkNieuw");
//        bedrijfRepository.opslaan(bedrijf3);
//
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//        System.out.println(ReflectionToStringBuilder.toString(bedrijf3));
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//        bedrijf3.setKvk("kvNie3uw");
//        bedrijfRepository.opslaan(bedrijf3);
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//        System.out.println(ReflectionToStringBuilder.toString(bedrijf3));
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//        System.out.println("# # # # # # # # # # # # # #");
//        System.out.println(" # # # # # # # # # # # # # #");
//
//        bedrijf1.setKvk("kvkNie");
//        bedrijfRepository.opslaan(bedrijf1);
//
//        assertEquals(bedrijf1, bedrijfRepository.alleBedrijvenBijRelatie(relatie1).get(0));
//        assertEquals(2, bedrijfRepository.alleBedrijvenBijRelatie(relatie2).size());
//    }
//
//}
