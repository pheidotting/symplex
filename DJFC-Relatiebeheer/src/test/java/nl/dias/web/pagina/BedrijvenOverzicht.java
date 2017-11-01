//package nl.dias.web.pagina;
//
//import java.util.List;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.json.JsonBedrijf;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class BedrijvenOverzicht {
//    @FindBy(name = "naam")
//    private List<WebElement> naam;
//    @FindBy(name = "kvk")
//    private List<WebElement> kvk;
//    @FindBy(name = "straat")
//    private List<WebElement> straat;
//    @FindBy(name = "huisnummer")
//    private List<WebElement> huisnummer;
//    @FindBy(name = "toevoeging")
//    private List<WebElement> toevoeging;
//    @FindBy(name = "postcode")
//    private List<WebElement> postcode;
//    @FindBy(name = "plaats")
//    private List<WebElement> plaats;
//
//    public int zoekBedrijf(JsonBedrijf jsonBedrijf) {
//        int gevonden = 0;
//        boolean werdGevonden = false;
//        for (WebElement element : naam) {
//            if (element.getText().equals(jsonBedrijf.getNaam())) {
//                gevonden++;
//                werdGevonden = true;
//                break;
//            }
//            gevonden++;
//        }
//        if (!werdGevonden) {
//            gevonden = -1;
//        }
//        return gevonden;
//    }
//
//    public String controleerBedrijf(JsonBedrijf jsonBedrijf) {
//        StringBuilder sb = new StringBuilder();
//        int nummer = zoekBedrijf(jsonBedrijf);
//        if (nummer == -1) {
//            return "niet gevonden";
//        }
//        --nummer;
//        Hulp.klikEnWacht(naam.get(nummer));
//        if (!Hulp.controleerVeld(this.kvk.get(nummer), (jsonBedrijf.getKvk()))) {
//            sb.append("|").append(Hulp.getText(this.kvk.get(nummer))).append(",").append((jsonBedrijf.getKvk())).append(";").append("nummer");
//        }
//        if (!Hulp.controleerVeld(this.straat.get(nummer), (jsonBedrijf.getStraat()))) {
//            sb.append("|").append(Hulp.getText(this.straat.get(nummer))).append(",").append((jsonBedrijf.getStraat())).append(";").append("straat");
//        }
//        if (!Hulp.controleerVeld(this.huisnummer.get(nummer), (jsonBedrijf.getHuisnummer()))) {
//            sb.append("|").append(Hulp.getText(this.huisnummer.get(nummer))).append(",").append((jsonBedrijf.getHuisnummer())).append(";").append("huisnummer");
//        }
//        if (this.toevoeging.size() > 0 && !Hulp.controleerVeld(this.toevoeging.get(nummer), (jsonBedrijf.getToevoeging()))) {
//            sb.append("|").append(Hulp.getText(this.toevoeging.get(nummer))).append(",").append((jsonBedrijf.getToevoeging())).append(";").append("toevoeging");
//        }
//        if (!Hulp.controleerVeld(this.postcode.get(nummer), (jsonBedrijf.getPostcode()))) {
//            sb.append("|").append(Hulp.getText(this.postcode.get(nummer))).append(",").append((jsonBedrijf.getPostcode())).append(";").append("postcode");
//        }
//        if (!Hulp.controleerVeld(this.plaats.get(nummer), jsonBedrijf.getPlaats())) {
//            sb.append("|").append(Hulp.getText(this.plaats.get(nummer))).append(",").append((jsonBedrijf.getPlaats())).append(";").append("plaats");
//        }
//        Hulp.klikEnWacht(naam.get(nummer));
//        return sb.toString();
//    }
//}
