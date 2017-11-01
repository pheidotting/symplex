//package nl.dias.repository;
//
//import nl.dias.domein.*;
//import nl.dias.domein.polis.*;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import javax.persistence.NoResultException;
//import javax.persistence.Query;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//@Ignore
//public class PolisRepositoryTest {
//    private PolisRepository polisRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        polisRepository = new PolisRepository();
//        polisRepository.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void test() {
//        Relatie relatie = new Relatie();
//        polisRepository.getEm().getTransaction().begin();
//        polisRepository.getEm().persist(relatie);
//        polisRepository.getEm().getTransaction().commit();
//
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("Patrick's Verzekeringen");
//        polisRepository.getEm().getTransaction().begin();
//        polisRepository.getEm().persist(maatschappij);
//        polisRepository.getEm().getTransaction().commit();
//
//        AutoVerzekering autoVerzekering = new AutoVerzekering();
//        autoVerzekering.setMaatschappij(maatschappij.getId());
//        autoVerzekering.setRelatie(relatie);
//        autoVerzekering.setIngangsDatum(new LocalDate());
//        autoVerzekering.setPremie(new Bedrag(123.00));
//        autoVerzekering.setPolisNummer("polisNummer");
//        ReisVerzekering reisVerzekering = new ReisVerzekering();
//        reisVerzekering.setMaatschappij(maatschappij.getId());
//        reisVerzekering.setRelatie(relatie);
//
//        relatie.getPolissen().add(autoVerzekering);
//        relatie.getPolissen().add(reisVerzekering);
//
//        polisRepository.opslaan(autoVerzekering);
//        System.out.println(autoVerzekering.getId());
//        polisRepository.opslaan(reisVerzekering);
//        System.out.println(reisVerzekering.getId());
//
//        assertEquals(2, polisRepository.alles().size());
//        assertEquals(1, polisRepository.zoekPolissenOpSoort(AutoVerzekering.class).size());
//        assertEquals(1, polisRepository.zoekPolissenOpSoort(ReisVerzekering.class).size());
//
//        // TODO delete werkt nog niet
//        relatie.getPolissen().remove(autoVerzekering);
//        polisRepository.verwijder(autoVerzekering);
//
//        assertEquals(0, polisRepository.zoekPolissenOpSoort(AutoVerzekering.class).size());
//        assertEquals(1, polisRepository.zoekPolissenOpSoort(ReisVerzekering.class).size());
//    }
//
//    @Test
//    public void allePolissenBijMaatschappij() {
//        VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
//        VerzekeringsMaatschappij maatschappij2 = new VerzekeringsMaatschappij();
//
//        maatschappij1.setNaam("maatschappij1");
//        maatschappij2.setNaam("maatschappij2");
//
//        polisRepository.getEm().getTransaction().begin();
//        polisRepository.getEm().persist(maatschappij1);
//        polisRepository.getEm().persist(maatschappij2);
//        polisRepository.getEm().getTransaction().commit();
//    }
//
//    @Test
//    public void allePolissenBijRelatieEnZijnBedrijven() {
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("Naam");
//        opslaan(maatschappij);
//
//        Relatie relatie = new Relatie();
//
//        Bedrijf bedrijf1 = new Bedrijf();
//        bedrijf1.setNaam("Bedrijf1");
//        Bedrijf bedrijf2 = new Bedrijf();
//        bedrijf2.setNaam("Bedrijf2");
//
//        AutoVerzekering autoVerzekering = new AutoVerzekering();
//        autoVerzekering.setRelatie(relatie);
//        autoVerzekering.setMaatschappij(maatschappij.getId());
//        WoonhuisVerzekering woonhuisVerzekering = new WoonhuisVerzekering();
//        woonhuisVerzekering.setBedrijf(bedrijf1);
//        woonhuisVerzekering.setMaatschappij(maatschappij.getId());
//        OngevallenVerzekering ongevallenVerzekering = new OngevallenVerzekering();
//        ongevallenVerzekering.setBedrijf(bedrijf2);
//        ongevallenVerzekering.setMaatschappij(maatschappij.getId());
//
//        relatie.getPolissen().add(autoVerzekering);
//        bedrijf1.getPolissen().add(woonhuisVerzekering);
//        bedrijf2.getPolissen().add(ongevallenVerzekering);
//
//        opslaan(relatie);
//
//        assertEquals(3, polisRepository.allePolissenVanRelatieEnZijnBedrijf(relatie).size());
//
//    }
//
//    @Test
//    public void zoekOpPolisNummer() {
//        Kantoor kantoor1 = new Kantoor();
//        Kantoor kantoor2 = new Kantoor();
//
//        opslaan(kantoor1);
//        opslaan(kantoor2);
//
//        Relatie relatie1 = new Relatie();
//        Relatie relatie2 = new Relatie();
//
//        relatie1.setKantoor(kantoor1);
//        relatie2.setKantoor(kantoor2);
//
//        opslaan(relatie1);
//        opslaan(relatie2);
//
//        kantoor1.getRelaties().add(relatie1);
//        kantoor1.getRelaties().add(relatie2);
//
//        update(kantoor1);
//        update(kantoor2);
//
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("Naam");
//        opslaan(maatschappij);
//
//        AutoVerzekering verzekering1 = new AutoVerzekering();
//        CamperVerzekering verzekering2 = new CamperVerzekering();
//        FietsVerzekering verzekering3 = new FietsVerzekering();
//
//        verzekering1.setPolisNummer("polisNummer1");
//        verzekering1.setRelatie(relatie1);
//        verzekering1.setMaatschappij(maatschappij.getId());
//        verzekering2.setPolisNummer("polisNummer2");
//        verzekering2.setRelatie(relatie1);
//        verzekering2.setMaatschappij(maatschappij.getId());
//        verzekering3.setPolisNummer("polisNummer3");
//        verzekering3.setRelatie(relatie2);
//        verzekering3.setMaatschappij(maatschappij.getId());
//
//        opslaan(verzekering1);
//        opslaan(verzekering2);
//        opslaan(verzekering3);
//
//        relatie1.getPolissen().add(verzekering1);
//        relatie1.getPolissen().add(verzekering2);
//        relatie2.getPolissen().add(verzekering3);
//
//        update(relatie1);
//        update(relatie2);
//
//        assertEquals(verzekering1, polisRepository.zoekOpPolisNummer("polisNummer1", kantoor1));
//        assertEquals(verzekering2, polisRepository.zoekOpPolisNummer("polisNummer2", kantoor1));
//        assertEquals(verzekering3, polisRepository.zoekOpPolisNummer("polisNummer3", kantoor2));
//
//        try {
//            assertEquals(verzekering3, polisRepository.zoekOpPolisNummer("polisNummer3", kantoor1));
//            fail("exception verwacht");
//        } catch (NoResultException e) {
//            // verwacht
//        }
//    }
//
//    @Test
//    public void testAllePolissenBijMaatschappij() {
//        VerzekeringsMaatschappij maatschappij1 = new VerzekeringsMaatschappij();
//        maatschappij1.setNaam("naam1");
//
//        VerzekeringsMaatschappij maatschappij2 = new VerzekeringsMaatschappij();
//        maatschappij2.setNaam("naam2");
//
//        opslaan(maatschappij1);
//        opslaan(maatschappij2);
//
//        AutoVerzekering autoVerzekering = new AutoVerzekering();
//        CamperVerzekering camperVerzekering = new CamperVerzekering();
//        FietsVerzekering fietsVerzekering = new FietsVerzekering();
//
//        autoVerzekering.setMaatschappij(maatschappij1.getId());
//        camperVerzekering.setMaatschappij(maatschappij1.getId());
//        fietsVerzekering.setMaatschappij(maatschappij2.getId());
//
//        polisRepository.opslaan(autoVerzekering);
//        polisRepository.opslaan(camperVerzekering);
//        polisRepository.opslaan(fietsVerzekering);
//
//        assertEquals(2, polisRepository.allePolissenBijMaatschappij(maatschappij1).size());
//        assertEquals(1, polisRepository.allePolissenBijMaatschappij(maatschappij2).size());
//    }
//
//    @Test
//    public void testOpslaanBijlage() {
//        Bijlage bijlage = new Bijlage();
//
//        polisRepository.opslaanBijlage(bijlage);
//
//        Query query = polisRepository.getEm().createQuery("select b from Bijlage b");
//        assertEquals(1, query.getResultList().size());
//
//        assertEquals(bijlage, polisRepository.leesBijlage(bijlage.getId()));
//    }
//
//    private void opslaan(Object object) {
//        polisRepository.getEm().getTransaction().begin();
//        polisRepository.getEm().persist(object);
//        polisRepository.getEm().getTransaction().commit();
//    }
//
//    private void update(Object object) {
//        polisRepository.getEm().getTransaction().begin();
//        polisRepository.getEm().merge(object);
//        polisRepository.getEm().getTransaction().commit();
//    }
//}
