//package nl.dias.repository;
//
//import nl.dias.domein.*;
//import nl.dias.domein.polis.AutoVerzekering;
//import nl.dias.domein.polis.Polis;
//import org.joda.time.LocalDateTime;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class BijlageRepositoryTest {
//    private BijlageRepository repository;
//
//    @Before
//    public void setUp() throws Exception {
//        repository = new BijlageRepository();
//        repository.zetPersistenceContext("unittest");
//    }
//
//    @Test
//    public void testAlleBijlagesBijRelatie() {
//        VerzekeringsMaatschappij maatschappij = new VerzekeringsMaatschappij();
//        maatschappij.setNaam("naamMaatschappij");
//        opslaan(maatschappij);
//
//        Relatie relatie = new Relatie();
//        opslaan(relatie);
//
//        Polis polis1 = new AutoVerzekering();
//        polis1.setRelatie(relatie);
//        polis1.setMaatschappij(maatschappij);
//        opslaan(polis1);
//
//        Polis polis2 = new AutoVerzekering();
//        polis2.setRelatie(relatie);
//        polis2.setMaatschappij(maatschappij);
//        opslaan(polis2);
//
//        StatusSchade statusSchade = new StatusSchade();
//        statusSchade.setStatus("status");
//        opslaan(statusSchade);
//
//        SoortSchade soortSchade = new SoortSchade();
//        soortSchade.setOmschrijving("omschrijving");
//        opslaan(soortSchade);
//
//        Schade schade = new Schade();
//        schade.setPolis(polis2);
//        schade.setDatumMelding(new LocalDateTime());
//        schade.setDatumSchade(new LocalDateTime());
//        schade.setSchadeNummerMaatschappij("schadeNummerMaatschappij");
//        schade.setStatusSchade(statusSchade);
//        schade.setSoortSchade(soortSchade);
//        opslaan(schade);
//
//        polis2.getSchades().add(schade);
//
//        Bijlage bijlage1 = new Bijlage();
//        bijlage1.setSoortBijlage(SoortBijlage.POLIS);
//        bijlage1.setPolis(polis1);
//        Bijlage bijlage2 = new Bijlage();
//        bijlage2.setSoortBijlage(SoortBijlage.SCHADE);
//        bijlage2.setSchade(schade);
//
//        repository.opslaan(bijlage1);
//        repository.opslaan(bijlage2);
//
//        assertEquals(2, repository.alleBijlagesBijRelatie(relatie).size());
//
//    }
//
//    public void opslaan(Object object) {
//        repository.getEm().getTransaction().begin();
//        repository.getEm().persist(object);
//        repository.getEm().getTransaction().commit();
//    }
//}
