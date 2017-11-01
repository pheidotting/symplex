//package nl.dias.web.pagina;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.json.JsonOpmerking;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class OpmerkingBewerken {
//    @FindBy(id = "opmerking")
//    private WebElement opmerking;
//    @FindBy(id = "opmerkingOpslaan")
//    private WebElement opmerkingOpslaan;
//
//    public void vulVelden(JsonOpmerking jsonOpmerking) {
//        Hulp.vulVeld(opmerking, jsonOpmerking.getOpmerking());
//    }
//
//    public void drukOpOpslaan() {
//        Hulp.klikEnWacht(opmerkingOpslaan);
//    }
//
//    public void vulVeldenEnDrukOpOpslaan(JsonOpmerking jsonOpmerking) {
//        vulVelden(jsonOpmerking);
//        drukOpOpslaan();
//    }
//}
