//package nl.dias.repository;
//
//import nl.dias.domein.*;
//import nl.dias.exception.BsnNietGoedException;
//import nl.dias.exception.IbanNietGoedException;
//import nl.dias.exception.PostcodeNietGoedException;
//import nl.dias.exception.TelefoonnummerNietGoedException;
//import org.joda.time.LocalDate;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//@Ignore
//public class KantoorRepositoryTest {
//    private KantoorRepository kantoorService;
//
//    @Before
//    public void init() {
//        kantoorService = new KantoorRepository();
//        kantoorService.setPersistenceContext("unittest");
//    }
//
//    @Test
//    public void test() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        Kantoor kantoor = new Kantoor();
//        kantoor.setNaam("Patrick's mooie kantoortje");
//        Adres adres = new Adres();
//        adres.setHuisnummer(46L);
//        adres.setPlaats("Plaaaaaaaaaaaaaats");
//        adres.setPostcode("1234BB");
//        adres.setStraat("Straaaaaaaaaaaaaaaaat");
//        adres.setToevoeging("toevoeging");
//        adres.setSoortAdres(Adres.SoortAdres.POSTADRES);
//        //        adres.setKantoor(kantoor);
//        //        kantoor.getAdressen().add(adres);
//        kantoor.setKvk(138383838L);
//        kantoor.setBtwNummer("btwNummer");
//        kantoor.setDatumOprichting(new LocalDate());
//        kantoor.setEmailadres("patrick@heidotting.nl");
//        kantoor.setRechtsvorm(Rechtsvorm.EENM);
//        kantoor.setSoortKantoor(SoortKantoor.HYP);
//
//        Adres factuurAdres = new Adres();
//        factuurAdres.setHuisnummer(58L);
//        factuurAdres.setPlaats("Ploats");
//        factuurAdres.setPostcode("2345JJ");
//        factuurAdres.setStraat("Stroate");
//        factuurAdres.setToevoeging("toevoeg");
//        factuurAdres.setSoortAdres(Adres.SoortAdres.FACTUURADRES);
//        //        kantoor.getAdressen().add(factuurAdres);
//
//        Medewerker medewerker = new Medewerker();
//        medewerker.setIdentificatie("pp");
//        medewerker.setHashWachtwoord("hh");
//        medewerker.setKantoor(kantoor);
//
//        kantoor.getMedewerkers().add(medewerker);
//
//        RekeningNummer rekeningNummer = new RekeningNummer();
//        rekeningNummer.setBic("bic");
//        rekeningNummer.setRekeningnummer("NL96SNSB0907007406");
//
//
//        RekeningNummer rekeningNummer1 = new RekeningNummer();
//        rekeningNummer1.setBic("bic");
//        rekeningNummer1.setRekeningnummer("NL96SNSB0907007406");
//
//
//        try {
//            kantoorService.opslaanKantoor(kantoor);
//        } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
//            fail();
//        }
//
//        assertEquals(1, kantoorService.alles().size());
//
//        kantoor.setNaam("Toch niet zo heel mooi dit kantoor");
//
//        try {
//            kantoorService.opslaanKantoor(kantoor);
//        } catch (PostcodeNietGoedException | TelefoonnummerNietGoedException | BsnNietGoedException | IbanNietGoedException e) {
//            fail();
//        }
//
//        assertEquals(1, kantoorService.alles().size());
//
//        kantoorService.verwijder(kantoor);
//
//        assertEquals(0, kantoorService.alles().size());
//    }
//}
