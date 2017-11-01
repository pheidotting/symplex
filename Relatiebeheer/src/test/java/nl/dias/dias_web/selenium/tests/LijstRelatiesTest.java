//package nl.dias.dias_web.selenium.tests;
//
//import static org.junit.Assert.assertTrue;
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.dias_web.medewerker.GebruikerControllerTest;
//import nl.dias.dias_web.selenium.AbstractSeleniumTest;
//import nl.dias.domein.json.JsonLijstRelaties;
//import nl.dias.domein.json.JsonRelatie;
//import nl.dias.web.pagina.LijstRelaties;
//
//import org.junit.Ignore;
//import org.openqa.selenium.support.PageFactory;
//
//@Ignore
//public class LijstRelatiesTest extends AbstractSeleniumTest {
//
//    @Override
//    public void voerTestUit() {
//        GebruikerControllerTest.jsonLijstRelaties = new JsonLijstRelaties();
//        JsonRelatie jsonRelatie = new JsonRelatie();
//        jsonRelatie.setVoornaam("voornaam1");
//        jsonRelatie.setTussenvoegsel("tussenvoegsel1");
//        jsonRelatie.setAchternaam("achternaam1");
//        jsonRelatie.setGeboorteDatum("01-01-2001");
//        jsonRelatie.setAdresOpgemaakt("adresOpgemaakt1");
//        GebruikerControllerTest.jsonLijstRelaties.getJsonRelaties().add(jsonRelatie);
//        JsonRelatie jsonRelatie1 = new JsonRelatie();
//        jsonRelatie1.setVoornaam("voornaam2");
//        jsonRelatie1.setTussenvoegsel("tussenvoegsel2");
//        jsonRelatie1.setAchternaam("achternaam2");
//        jsonRelatie1.setGeboorteDatum("02-02-2002");
//        jsonRelatie1.setAdresOpgemaakt("adresOpgemaakt2");
//        GebruikerControllerTest.jsonLijstRelaties.getJsonRelaties().add(jsonRelatie1);
//
//        Hulp.naarAdres(driver, "http://localhost:9999/dias-web/index.html#lijstRelaties");
//
//        LijstRelaties pagina = PageFactory.initElements(driver, LijstRelaties.class);
//
//        assertTrue(pagina.checkVelden(GebruikerControllerTest.jsonLijstRelaties));
//    }
//
//}
