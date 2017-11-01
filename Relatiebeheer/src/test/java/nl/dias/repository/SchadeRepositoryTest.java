//package nl.dias.repository;
//
//import nl.dias.domein.*;
//import nl.dias.domein.polis.FietsVerzekering;
//import nl.dias.domein.polis.Polis;
//import org.joda.time.LocalDateTime;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class SchadeRepositoryTest {
//    private SchadeRepository repository;
//
//    @Before
//    public void setUp() throws Exception {
//        repository = new SchadeRepository();
//        repository.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void testSoortenSchade() {
//        SoortSchade soortSchade1 = new SoortSchade();
//        soortSchade1.setIngebruik(true);
//        soortSchade1.setOmschrijving("bab");
//
//        SoortSchade soortSchade2 = new SoortSchade();
//        soortSchade2.setIngebruik(false);
//        soortSchade2.setOmschrijving("ccc");
//
//        SoortSchade soortSchade3 = new SoortSchade();
//        soortSchade3.setIngebruik(true);
//        soortSchade3.setOmschrijving("deb");
//
//        repository.getTx().begin();
//        repository.getEm().persist(soortSchade1);
//        repository.getEm().persist(soortSchade2);
//        repository.getEm().persist(soortSchade3);
//        repository.getTx().commit();
//
//        assertEquals(2, repository.soortenSchade().size());
//        assertEquals(2, repository.soortenSchade("b").size());
//        assertEquals(1, repository.soortenSchade("a").size());
//        assertEquals(1, repository.soortenSchade("e").size());
//        assertEquals(0, repository.soortenSchade("c").size());
//    }
//
//    @Test
//    public void getStatussen() {
//        StatusSchade statusSchade1 = new StatusSchade();
//        statusSchade1.setStatus("status1");
//        statusSchade1.setIngebruik(true);
//        StatusSchade statusSchade2 = new StatusSchade();
//        statusSchade2.setStatus("status2");
//        statusSchade2.setIngebruik(true);
//        StatusSchade statusSchade3 = new StatusSchade();
//        statusSchade3.setStatus("status3");
//        statusSchade3.setIngebruik(true);
//
//        repository.getTx().begin();
//        repository.getEm().persist(statusSchade1);
//        repository.getEm().persist(statusSchade2);
//        repository.getEm().persist(statusSchade3);
//        repository.getTx().commit();
//
//        assertEquals(3, repository.getStatussen().size());
//        assertEquals(statusSchade1, repository.getStatussen("status1"));
//        assertEquals(statusSchade2, repository.getStatussen("status2"));
//        assertEquals(statusSchade3, repository.getStatussen("status3"));
//    }
//
//    @Test
//    public void zoekOpSchadeNummerMaatschappij() {
//        StatusSchade statusSchade = maakStatusSchade("status");
//
//        repository.getTx().begin();
//        repository.getEm().persist(statusSchade);
//        repository.getTx().commit();
//
//        Schade schade1 = maakSchade("schadeNummerMaatschappij1", statusSchade);
//        Schade schade2 = maakSchade("schadeNummerMaatschappij2", statusSchade);
//
//        repository.opslaan(schade1);
//        repository.opslaan(schade2);
//
//        assertEquals(schade1, repository.zoekOpSchadeNummerMaatschappij("schadeNummerMaatschappij1"));
//        assertEquals(schade2, repository.zoekOpSchadeNummerMaatschappij("schadeNummerMaatschappij2"));
//    }
//
//    @Test
//    public void alleSchadesBijRelatie() {
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("naam");
//
//        Relatie relatie1 = new Relatie();
//        Relatie relatie2 = new Relatie();
//
//        Polis polis1 = new FietsVerzekering();
//        Polis polis2 = new FietsVerzekering();
//
//        StatusSchade statusSchade = maakStatusSchade("status");
//
//        repository.getTx().begin();
//        repository.getEm().persist(maatschappij);
//        polis1.setMaatschappij(maatschappij);
//        polis2.setMaatschappij(maatschappij);
//
//        repository.getEm().persist(statusSchade);
//        repository.getEm().persist(relatie1);
//        repository.getEm().persist(relatie2);
//
//        repository.getEm().persist(polis1);
//        repository.getEm().persist(polis2);
//
//        relatie1.getPolissen().add(polis1);
//        relatie2.getPolissen().add(polis2);
//        polis1.setRelatie(relatie1);
//        polis2.setRelatie(relatie2);
//
//        repository.getEm().merge(relatie1);
//        repository.getEm().merge(relatie2);
//
//        repository.getEm().merge(polis1);
//        repository.getEm().merge(polis2);
//
//        repository.getTx().commit();
//
//        Schade schade1 = maakSchade("schadeNummer1", statusSchade);
//        Schade schade2 = maakSchade("schadeNummer2", statusSchade);
//        Schade schade3 = maakSchade("schadeNummer3", statusSchade);
//
//        schade1.setPolis(polis1);
//        polis1.getSchades().add(schade1);
//
//        schade2.setPolis(polis2);
//        polis2.getSchades().add(schade2);
//
//        schade3.setPolis(polis2);
//        polis2.getSchades().add(schade3);
//
//        repository.opslaan(schade1);
//        repository.opslaan(schade2);
//        repository.opslaan(schade3);
//
//        assertEquals(1, repository.alleSchadesBijRelatie(relatie1).size());
//        assertEquals(2, repository.alleSchadesBijRelatie(relatie2).size());
//
//    }
//
//    private Schade maakSchade(String schadeNummer, StatusSchade statusSchade) {
//        Schade schade1 = new Schade();
//        schade1.setSchadeNummerMaatschappij(schadeNummer);
//        schade1.setStatusSchade(statusSchade);
//        schade1.setDatumTijdSchade(new LocalDateTime());
//        schade1.setDatumTijdMelding(new LocalDateTime());
//
//        return schade1;
//    }
//
//    private StatusSchade maakStatusSchade(String status) {
//        StatusSchade statusSchade = new StatusSchade();
//        statusSchade.setStatus(status);
//        statusSchade.setIngebruik(true);
//
//        return statusSchade;
//    }
//}
