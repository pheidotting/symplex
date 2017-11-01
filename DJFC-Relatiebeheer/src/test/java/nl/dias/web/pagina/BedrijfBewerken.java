//package nl.dias.web.pagina;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.json.JsonBedrijf;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class BedrijfBewerken extends PaginaMetMenuBalk {
//    @FindBy(id = "naam")
//    private WebElement naam;
//    @FindBy(id = "kvk")
//    private WebElement kvk;
//    @FindBy(id = "straat")
//    private WebElement straat;
//    @FindBy(id = "huisnummer")
//    private WebElement huisnummer;
//    @FindBy(id = "toevoeging")
//    private WebElement toevoeging;
//    @FindBy(id = "postcode")
//    private WebElement postcode;
//    @FindBy(id = "plaats")
//    private WebElement plaats;
//    @FindBy(id = "opslaanBedrijf")
//    private WebElement opslaan;
//
//    public void vulVelden(JsonBedrijf jsonBedrijf) {
//        Hulp.wachtFf();
//        Hulp.vulVeld(naam, jsonBedrijf.getNaam());
//        Hulp.vulVeld(kvk, jsonBedrijf.getKvk());
//        Hulp.vulVeld(straat, jsonBedrijf.getStraat());
//        Hulp.vulVeld(huisnummer, jsonBedrijf.getHuisnummer());
//        Hulp.vulVeld(toevoeging, jsonBedrijf.getToevoeging());
//        Hulp.vulVeld(postcode, jsonBedrijf.getPostcode());
//        Hulp.vulVeld(plaats, jsonBedrijf.getPlaats());
//    }
//
//    public void drukOpOpslaan() {
//        Hulp.klikEnWacht(opslaan);
//        Hulp.wachtFf();
//    }
//
//    public void vulVeldenEnDrukOpOpslaan(JsonBedrijf jsonBedrijf) {
//        vulVelden(jsonBedrijf);
//        Hulp.wachtFf();
//        drukOpOpslaan();
//        do {
//            Hulp.wachtFf();
//        } while (leesmelding().equals(""));
//    }
//
//}
