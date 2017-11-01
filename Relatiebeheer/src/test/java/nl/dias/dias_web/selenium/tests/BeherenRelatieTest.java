//package nl.dias.dias_web.selenium.tests;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.dias_web.medewerker.GebruikerControllerTest;
//import nl.dias.dias_web.selenium.AbstractSeleniumTest;
//import nl.dias.domein.json.JsonRekeningNummer;
//import nl.dias.domein.json.JsonRelatie;
//import nl.dias.domein.json.JsonTelefoonnummer;
//import nl.dias.web.pagina.BeherenRelatie;
//import nl.dias.web.pagina.BeherenRelatieRekeningnummer;
//import nl.dias.web.pagina.BeherenRelatieTelefoonnummer;
//import org.junit.Ignore;
//import org.openqa.selenium.support.PageFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.assertEquals;
//
//@Ignore
//public class BeherenRelatieTest extends AbstractSeleniumTest {
//
//    @Override
//    public void voerTestUit() {
//        GebruikerControllerTest.jsonRelatie = new JsonRelatie();
//
//        Hulp.naarAdres(driver, "http://localhost:9999/dias-web/index.html#beherenRelatie/3");
//
//        BeherenRelatie pagina = PageFactory.initElements(driver, BeherenRelatie.class);
//
//        JsonRelatie jsonRelatie = maakJsonRelatie();
//
//        //        pagina.vulVeldenEnDrukOpOpslaan(jsonRelatie.getVoornaam(), jsonRelatie.getAchternaam(), jsonRelatie.getTussenvoegsel(), jsonRelatie.getStraat(), jsonRelatie.getHuisnummer().toString(),
//        //                jsonRelatie.getToevoeging(), jsonRelatie.getPostcode(), jsonRelatie.getPlaats(), jsonRelatie.getBsn(), jsonRelatie.getIdentificatie(), jsonRelatie.getGeboorteDatum(),
//        //                jsonRelatie.getOverlijdensdatum(), jsonRelatie.getGeslacht(), jsonRelatie.getBurgerlijkeStaat(), allJsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRelatie.getRekeningnummers()),
//        //                allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonRelatie.getTelefoonnummers()));
//
//        assertEquals(jsonRelatie, GebruikerControllerTest.jsonRelatie);
//
//        pagina.drukOpVerwijderen();
//        assertEquals("http://localhost:9999/dias-web/index.html#lijstRelaties", driver.getCurrentUrl());
//    }
//
//    private JsonRelatie maakJsonRelatie() {
//        JsonRelatie jsonRelatie = new JsonRelatie();
//
//        jsonRelatie.setAchternaam("achternaam");
//        jsonRelatie.setBsn("bsn");
//        jsonRelatie.setIdentificatie("identificatie");
//        jsonRelatie.setTussenvoegsel("vd");
//        jsonRelatie.setVoornaam("voornaam");
//        jsonRelatie.setGeboorteDatum("06-09-1979");
//        jsonRelatie.setOverlijdensdatum("03-05-2012");
//
//        jsonRelatie.setGeslacht("Vrouw");
//        jsonRelatie.setBurgerlijkeStaat("Gehuwd");
//
//        //        jsonRelatie.setStraat("Herderstraat");
//        //        jsonRelatie.setHuisnummer("65");
//        //        jsonRelatie.setToevoeging("toevoeging");
//        //        jsonRelatie.setPostcode("1234AA");
//        //        jsonRelatie.setPlaats("plaats");
////        jsonRelatie.setOnderlingeRelaties(new ArrayList<Long>());
//        jsonRelatie.setRekeningnummers(new ArrayList<JsonRekeningNummer>());
//        jsonRelatie.getRekeningnummers().add(new JsonRekeningNummer(null, "bic1", "rekeningnummer1"));
//        jsonRelatie.getRekeningnummers().add(new JsonRekeningNummer(null, "bic2", "rekeningnummer2"));
//        jsonRelatie.getRekeningnummers().add(new JsonRekeningNummer(null, "bic3", "rekeningnummer3"));
//        jsonRelatie.getRekeningnummers().add(new JsonRekeningNummer(null, "bic4", "rekeningnummer4"));
//        jsonRelatie.getRekeningnummers().add(new JsonRekeningNummer(null, "bic5", "rekeningnummer5"));
//
//        jsonRelatie.setTelefoonnummers(new ArrayList<JsonTelefoonnummer>());
//        jsonRelatie.getTelefoonnummers().add(new JsonTelefoonnummer(null, "telefoonnummer1", "Vast", null));
//        jsonRelatie.getTelefoonnummers().add(new JsonTelefoonnummer(null, "telefoonnummer2", "Mobiel", null));
//        jsonRelatie.getTelefoonnummers().add(new JsonTelefoonnummer(null, "telefoonnummer3", "Werk", null));
//        jsonRelatie.getTelefoonnummers().add(new JsonTelefoonnummer(null, "telefoonnummer4", "Mobiel", null));
//        jsonRelatie.getTelefoonnummers().add(new JsonTelefoonnummer(null, "telefoonnummer5", "Vast", null));
//
//        return jsonRelatie;
//    }
//
//    private List<BeherenRelatieRekeningnummer> allJsonRekeningNummerToBeherenRelatieRekeningnummer(List<JsonRekeningNummer> jsonRekeningNummers) {
//        List<BeherenRelatieRekeningnummer> beherenRelatieRekeningnummers = new ArrayList<>();
//
//        for (JsonRekeningNummer jsonRekeningNummer : jsonRekeningNummers) {
//            beherenRelatieRekeningnummers.add(jsonRekeningNummerToBeherenRelatieRekeningnummer(jsonRekeningNummer));
//        }
//
//        return beherenRelatieRekeningnummers;
//    }
//
//    private BeherenRelatieRekeningnummer jsonRekeningNummerToBeherenRelatieRekeningnummer(JsonRekeningNummer jsonRekeningNummer) {
//        BeherenRelatieRekeningnummer beherenRelatieRekeningnummer = new BeherenRelatieRekeningnummer();
//
//        beherenRelatieRekeningnummer.setBic(jsonRekeningNummer.getBic());
//        if (jsonRekeningNummer.getId() != null) {
//            beherenRelatieRekeningnummer.setId(jsonRekeningNummer.getId().toString());
//        }
//        beherenRelatieRekeningnummer.setRekeninnummer(jsonRekeningNummer.getRekeningnummer());
//
//        return beherenRelatieRekeningnummer;
//    }
//
//    private List<BeherenRelatieTelefoonnummer> allJsonTelefoonnummerToBeherenRelatieTelefoonnummer(List<JsonTelefoonnummer> jsonTelefoonnummers) {
//        List<BeherenRelatieTelefoonnummer> beherenRelatieTelefoonnummers = new ArrayList<>();
//
//        for (JsonTelefoonnummer jsonTelefoonnummer : jsonTelefoonnummers) {
//            beherenRelatieTelefoonnummers.add(jsonTelefoonnummerToBeherenRelatieTelefoonnummer(jsonTelefoonnummer));
//        }
//
//        return beherenRelatieTelefoonnummers;
//    }
//
//    private BeherenRelatieTelefoonnummer jsonTelefoonnummerToBeherenRelatieTelefoonnummer(JsonTelefoonnummer jsonTelefoonnummer) {
//        BeherenRelatieTelefoonnummer beherenRelatieTelefoonnummer = new BeherenRelatieTelefoonnummer();
//
//        if (jsonTelefoonnummer.getId() != null) {
//            beherenRelatieTelefoonnummer.setId(jsonTelefoonnummer.getId().toString());
//        }
//        beherenRelatieTelefoonnummer.setSoortTelefoonnummer(jsonTelefoonnummer.getSoort());
//        beherenRelatieTelefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());
//
//        return beherenRelatieTelefoonnummer;
//    }
//}
