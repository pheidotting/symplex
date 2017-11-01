//package nl.dias.web.pagina;
//
//import static nl.dias.dias_web.hulp.Hulp.klikEnWacht;
//import static nl.dias.dias_web.hulp.Hulp.vulVeld;
//import static nl.dias.dias_web.hulp.Hulp.wachtFf;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class InlogScherm extends IndexPagina {
//    @FindBy(id = "identificatie")
//    private WebElement identificatie;
//    @FindBy(id = "wachtwoord")
//    private WebElement wachtwoord;
//    @FindBy(id = "inlogButton")
//    private WebElement inlogButton;
//
//    public void inloggen(String identificatie, String wachtwoord) {
//        vulVeld(this.identificatie, identificatie);
//        vulVeld(this.wachtwoord, wachtwoord);
//        wachtFf();
//        klikEnWacht(inlogButton);
//        wachtFf();
//        wachtFf();
//        wachtFf();
//    }
//}