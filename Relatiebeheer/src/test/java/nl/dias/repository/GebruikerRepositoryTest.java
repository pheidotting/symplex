//package nl.dias.repository;
//
//import nl.dias.domein.*;
//import nl.lakedigital.loginsystem.exception.NietGevondenException;
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//@Ignore
//public class GebruikerRepositoryTest {
//    private GebruikerRepository gebruikerRepository;
//
//    @Before
//    public void setUp() throws Exception {
//        gebruikerRepository = new GebruikerRepository();
//        gebruikerRepository.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void zoekRelatiesOpTelefoonnummer() {
//        Relatie relatie1 = new Relatie();
//        Relatie relatie2 = new Relatie();
//
//        Telefoonnummer telefoonnummer1 = new Telefoonnummer();
//        telefoonnummer1.setTelefoonnummer("1");
//        Telefoonnummer telefoonnummer2 = new Telefoonnummer();
//        telefoonnummer2.setTelefoonnummer("2");
//        Telefoonnummer telefoonnummer3 = new Telefoonnummer();
//        telefoonnummer3.setTelefoonnummer("3");
//
//        relatie1.getTelefoonnummers().add(telefoonnummer1);
//        telefoonnummer1.setRelatie(relatie1);
//        relatie1.getTelefoonnummers().add(telefoonnummer2);
//        telefoonnummer2.setRelatie(relatie1);
//        relatie2.getTelefoonnummers().add(telefoonnummer3);
//        telefoonnummer3.setRelatie(relatie2);
//
//        gebruikerRepository.opslaan(relatie1);
//        gebruikerRepository.opslaan(relatie2);
//
//        assertEquals(relatie1, gebruikerRepository.zoekRelatiesOpTelefoonnummer("1").get(0));
//        assertEquals(1, gebruikerRepository.zoekRelatiesOpTelefoonnummer("1").size());
//
//        assertEquals(relatie1, gebruikerRepository.zoekRelatiesOpTelefoonnummer("2").get(0));
//        assertEquals(1, gebruikerRepository.zoekRelatiesOpTelefoonnummer("2").size());
//
//        assertEquals(relatie2, gebruikerRepository.zoekRelatiesOpTelefoonnummer("3").get(0));
//        assertEquals(1, gebruikerRepository.zoekRelatiesOpTelefoonnummer("3").size());
//
//        assertEquals(0, gebruikerRepository.zoekRelatiesOpTelefoonnummer("4").size());
//    }
//
//    @Test
//    public void zoekOpBedrijfsnaam() {
//        Relatie relatie = new Relatie();
//        Relatie relatie1 = new Relatie();
//
//        Bedrijf bedrijf = new Bedrijf();
//        Bedrijf bedrijf1 = new Bedrijf();
//        bedrijf.setNaam("aabb");
//        bedrijf1.setNaam("bbcc");
//
//        gebruikerRepository.opslaan(relatie);
//        gebruikerRepository.opslaan(relatie1);
//
//        assertEquals(1, gebruikerRepository.zoekRelatiesOpBedrijfsnaam("aa").size());
//        assertEquals(relatie, gebruikerRepository.zoekRelatiesOpBedrijfsnaam("aa").get(0));
//
//        assertEquals(2, gebruikerRepository.zoekRelatiesOpBedrijfsnaam("bb").size());
//        assertEquals(relatie, gebruikerRepository.zoekRelatiesOpBedrijfsnaam("bb").get(0));
//        assertEquals(relatie1, gebruikerRepository.zoekRelatiesOpBedrijfsnaam("bb").get(1));
//
//        assertEquals(1, gebruikerRepository.zoekRelatiesOpBedrijfsnaam("cc").size());
//        assertEquals(relatie1, gebruikerRepository.zoekRelatiesOpBedrijfsnaam("cc").get(0));
//    }
//
//    @Test
//    public void zoek() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        Kantoor kantoor = new Kantoor();
//        kantoor.setDatumOprichting(new LocalDate());
//
//        gebruikerRepository.getEm().persist(kantoor);
//
//        Medewerker medewerker = new Medewerker();
//        medewerker.setIdentificatie("id");
//        medewerker.setHashWachtwoord("ww");
//
//        medewerker.setKantoor(kantoor);
//        kantoor.getMedewerkers().add(medewerker);
//
//        gebruikerRepository.opslaan(medewerker);
//
//        try {
//            assertEquals(medewerker, gebruikerRepository.zoek("id"));
//        } catch (NietGevondenException e) {
//            fail("Zou gevonden moeten worden");
//        }
//
//        try {
//            assertEquals(medewerker, gebruikerRepository.zoek("i"));
//            fail("Zou niet gevonden mogen worden");
//        } catch (NietGevondenException e) {
//        }
//    }
//
//    @Test
//    public void beheerder() throws NietGevondenException, UnsupportedEncodingException, NoSuchAlgorithmException {
//        Beheerder beheerder = new Beheerder();
//        beheerder.setHashWachtwoord("ww");
//        beheerder.setIdentificatie("p@h.n");
//
//        gebruikerRepository.opslaan(beheerder);
//
//        assertEquals(1, gebruikerRepository.alles().size());
//        assertEquals(beheerder, gebruikerRepository.zoek("p@h.n"));
//    }
//
//    @Test
//    public void opSessieEnIp() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        Medewerker medewerker = new Medewerker();
//        medewerker.setIdentificatie("identificatie");
//
//        Kantoor kantoor = new Kantoor();
//        gebruikerRepository.getEm().persist(kantoor);
//
//        medewerker.setKantoor(kantoor);
//
//        Sessie sessie = new Sessie();
//        sessie.setGebruiker(medewerker);
//        sessie.setIpadres("12345");
//        sessie.setSessie("abcde");
//        medewerker.getSessies().add(sessie);
//
//        Sessie sessie1 = new Sessie();
//        sessie1.setGebruiker(medewerker);
//        sessie1.setIpadres("3456");
//        sessie1.setSessie("cdefg");
//        medewerker.getSessies().add(sessie1);
//
//        gebruikerRepository.opslaan(medewerker);
//
//        Medewerker medewerker1 = new Medewerker();
//        medewerker1.setIdentificatie("identificatie1");
//
//        medewerker1.setKantoor(kantoor);
//
//        Sessie sessie2 = new Sessie();
//        sessie2.setGebruiker(medewerker1);
//        sessie2.setIpadres("67890");
//        sessie2.setSessie("efghi");
//        medewerker1.getSessies().add(sessie2);
//
//        Sessie sessie3 = new Sessie();
//        sessie3.setGebruiker(medewerker1);
//        sessie3.setIpadres("789012");
//        sessie3.setSessie("jklmn");
//        medewerker1.getSessies().add(sessie3);
//
//        gebruikerRepository.opslaan(medewerker1);
//
//        try {
//            assertEquals(medewerker.getId(), gebruikerRepository.zoekOpSessieEnIpadres("abcde", "12345").getId());
//        } catch (NietGevondenException e) {
//            fail("niet gevonden");
//        }
//        try {
//            gebruikerRepository.zoekOpSessieEnIpadres("abcdef", "12345");
//            fail("zou niet gevonden mogen worden");
//        } catch (NietGevondenException e) {
//        }
//        try {
//            assertEquals(medewerker.getId(), gebruikerRepository.zoekOpSessieEnIpadres("abcde", "123456").getId());
//            fail("zou niet gevonden mogen worden");
//        } catch (NietGevondenException e) {
//        }
//        try {
//            assertEquals(medewerker.getId(), gebruikerRepository.zoekOpSessieEnIpadres("cdefg", "3456").getId());
//        } catch (NietGevondenException e) {
//            fail("niet gevonden");
//        }
//    }
//
//    @Test
//    public void testAlleRelaties() {
//        Kantoor kantoor1 = new Kantoor();
//        Kantoor kantoor2 = new Kantoor();
//
//        opslaan(kantoor1);
//        opslaan(kantoor2);
//
//        Relatie relatie1 = new Relatie();
//        Relatie relatie2 = new Relatie();
//        Relatie relatie3 = new Relatie();
//
//        kantoor1.getRelaties().add(relatie1);
//        kantoor1.getRelaties().add(relatie2);
//        kantoor2.getRelaties().add(relatie3);
//        relatie1.setKantoor(kantoor1);
//        relatie2.setKantoor(kantoor1);
//        relatie3.setKantoor(kantoor2);
//
//        opslaan(relatie1);
//        opslaan(relatie2);
//        opslaan(relatie3);
//
//        assertEquals(2, gebruikerRepository.alleRelaties(kantoor1).size());
//        assertEquals(1, gebruikerRepository.alleRelaties(kantoor2).size());
//        assertEquals(3, gebruikerRepository.alleRelaties().size());
//    }
//
//    @Test
//    public void testZoekOpNaam() {
//        Relatie relatie1 = new Relatie();
//        relatie1.setVoornaam("ab");
//        relatie1.setAchternaam("bc");
//        Relatie relatie2 = new Relatie();
//        relatie2.setVoornaam("cd");
//        relatie2.setAchternaam("de");
//        Relatie relatie3 = new Relatie();
//        relatie3.setVoornaam("ef");
//        relatie3.setAchternaam("fg");
//
//        opslaan(relatie1);
//        opslaan(relatie2);
//        opslaan(relatie3);
//
//        assertEquals(1, gebruikerRepository.zoekOpNaam("a").size());
//        assertEquals("ab", gebruikerRepository.zoekOpNaam("a").get(0).getVoornaam());
//
//        assertEquals(1, gebruikerRepository.zoekOpNaam("b").size());
//        assertEquals("ab", gebruikerRepository.zoekOpNaam("b").get(0).getVoornaam());
//
//        assertEquals(2, gebruikerRepository.zoekOpNaam("c").size());
//        assertEquals("ab", gebruikerRepository.zoekOpNaam("c").get(0).getVoornaam());
//        assertEquals("cd", gebruikerRepository.zoekOpNaam("c").get(1).getVoornaam());
//
//        assertEquals(1, gebruikerRepository.zoekOpNaam("d").size());
//        assertEquals("cd", gebruikerRepository.zoekOpNaam("d").get(0).getVoornaam());
//
//        assertEquals(2, gebruikerRepository.zoekOpNaam("e").size());
//        assertEquals("cd", gebruikerRepository.zoekOpNaam("e").get(0).getVoornaam());
//        assertEquals("ef", gebruikerRepository.zoekOpNaam("e").get(1).getVoornaam());
//    }
//
//    @Test
//    public void zoekOpAdres() {
//        Relatie relatie1 = new Relatie();
//        Adres adres1 = new Adres();
//        adres1.setStraat("ab");
//        adres1.setPlaats("bc");
//        adres1.setSoortAdres(Adres.SoortAdres.WOONADRES);
//        adres1.setRelatie(relatie1);
//        relatie1.getAdressen().add(adres1);
//        Relatie relatie2 = new Relatie();
//        Adres adres2 = new Adres();
//        adres2.setStraat("cd");
//        adres2.setPlaats("de");
//        adres2.setSoortAdres(Adres.SoortAdres.WOONADRES);
//        adres2.setRelatie(relatie2);
//        relatie2.getAdressen().add(adres2);
//        Relatie relatie3 = new Relatie();
//        Adres adres3 = new Adres();
//        adres3.setStraat("ef");
//        adres3.setPlaats("fg");
//        adres3.setSoortAdres(Adres.SoortAdres.WOONADRES);
//        adres3.setRelatie(relatie3);
//        relatie3.getAdressen().add(adres3);
//
//        opslaan(relatie1);
//        opslaan(relatie2);
//        opslaan(relatie3);
//
//        List<Relatie> result = gebruikerRepository.zoekOpAdres("a");
//        assertEquals(1, result.size());
//        assertEquals("ab", result.get(0).getAdres().getStraat());
//
//        assertEquals(1, gebruikerRepository.zoekOpAdres("b").size());
//        assertEquals("ab", gebruikerRepository.zoekOpAdres("b").get(0).getAdres().getStraat());
//
//        assertEquals(2, gebruikerRepository.zoekOpAdres("c").size());
//        assertEquals("ab", gebruikerRepository.zoekOpAdres("c").get(0).getAdres().getStraat());
//        assertEquals("cd", gebruikerRepository.zoekOpAdres("c").get(1).getAdres().getStraat());
//
//        assertEquals(1, gebruikerRepository.zoekOpAdres("d").size());
//        assertEquals("cd", gebruikerRepository.zoekOpAdres("d").get(0).getAdres().getStraat());
//
//        assertEquals(2, gebruikerRepository.zoekOpAdres("e").size());
//        assertEquals("cd", gebruikerRepository.zoekOpAdres("e").get(0).getAdres().getStraat());
//        assertEquals("ef", gebruikerRepository.zoekOpAdres("e").get(1).getAdres().getStraat());
//    }
//
//    @Test
//    public void verwijderAdressenBijRelatie() {
//        Relatie relatie = new Relatie();
//        Adres adres = new Adres();
//        adres.setRelatie(relatie);
//        relatie.getAdressen().add(adres);
//
//        opslaan(relatie);
//
//        relatie = (Relatie) gebruikerRepository.lees(relatie.getId());
//
//        assertEquals(1, gebruikerRepository.getEm().createNativeQuery("select * from ADRES").getResultList().size());
//
//        gebruikerRepository.verwijderAdressenBijRelatie(relatie);
//
//        assertEquals(0, gebruikerRepository.getEm().createNativeQuery("select * from ADRES").getResultList().size());
//    }
//
//    // @Test
//    // @Ignore
//    // public void opCookie() {
//    // Medewerker medewerker = new Medewerker();
//    // medewerker.setIdentificatie("identificatie");
//    //
//    // Kantoor kantoor = new Kantoor();
//    // gebruikerRepository.getEm().persist(kantoor);
//    // medewerker.setKantoor(kantoor);
//    //
//    // Sessie sessie = new Sessie();
//    // sessie.setGebruiker(medewerker);
//    // sessie.setCookieCode("abc");
//    // medewerker.getSessies().add(sessie);
//    //
//    // Sessie sessie1 = new Sessie();
//    // sessie1.setGebruiker(medewerker);
//    // sessie1.setCookieCode("def");
//    // medewerker.getSessies().add(sessie1);
//    //
//    // gebruikerRepository.opslaan(medewerker);
//    //
//    // Medewerker medewerker1 = new Medewerker();
//    // medewerker1.setIdentificatie("identificatie1");
//    //
//    // medewerker1.setKantoor(kantoor);
//    //
//    // Sessie sessie2 = new Sessie();
//    // sessie2.setGebruiker(medewerker1);
//    // sessie2.setCookieCode("ghi");
//    // medewerker1.getSessies().add(sessie2);
//    //
//    // Sessie sessie3 = new Sessie();
//    // sessie3.setGebruiker(medewerker1);
//    // sessie3.setCookieCode("jkl");
//    // medewerker1.getSessies().add(sessie3);
//    //
//    // gebruikerRepository.opslaan(medewerker1);
//    //
//    // try {
//    // assertEquals(medewerker.getId(),
//    // gebruikerRepository.zoekOpCookieCode("abc").getId());
//    // } catch (NietGevondenException e) {
//    // fail("niet gevonden");
//    // }
//    // try {
//    // assertEquals(medewerker.getId(),
//    // gebruikerRepository.zoekOpCookieCode("cde").getId());
//    // fail("zou niet gevonden mogen worden");
//    // } catch (NietGevondenException e) {
//    // }
//    // try {
//    // assertEquals(medewerker.getId(),
//    // gebruikerRepository.zoekOpCookieCode("def").getId());
//    // } catch (NietGevondenException e) {
//    // fail("niet gevonden");
//    // }
//    //
//    // }
//    //
//     @Test
//     public void patrick() throws Exception{
//     Beheerder beheerder = new Beheerder();
//     beheerder.setAchternaam("Jonge");
//     beheerder.setVoornaam("Bene");
//     beheerder.setTussenvoegsel("de");
//     beheerder.setIdentificatie("djfc.bene");
//     beheerder.setHashWachtwoord("bene");
//
//         gebruikerRepository.opslaan(beheerder);
//
//     Beheerder beheerder2 = new Beheerder();
//         beheerder2.setAchternaam("Zwiers");
//         beheerder2.setVoornaam("Gerben");
//         beheerder2.setIdentificatie("djfc.gerben");
//         beheerder2.setHashWachtwoord("gerben");
//
//         gebruikerRepository.opslaan(beheerder2);
//
//     Beheerder beheerder3 = new Beheerder();
//         beheerder3.setAchternaam("Vooys");
//         beheerder3.setVoornaam("Karin");
//         beheerder3.setIdentificatie("djfc.karin");
//         beheerder3.setHashWachtwoord("karin");
//
//         gebruikerRepository.opslaan(beheerder3);
//     }
//
//    private void opslaan(Object object) {
//        gebruikerRepository.getEm().getTransaction().begin();
//        gebruikerRepository.getEm().persist(object);
//        gebruikerRepository.getEm().getTransaction().commit();
//    }
//
//    private void update(Object object) {
//        gebruikerRepository.getEm().getTransaction().begin();
//        gebruikerRepository.getEm().merge(object);
//        gebruikerRepository.getEm().getTransaction().commit();
//    }
//}
