//package nl.dias.web.pagina;
//
//import nl.dias.dias_web.hulp.Hulp;
//
//import org.joda.time.LocalDateTime;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public abstract class IndexPagina {
//    @FindBy(id = "alertSucces")
//    private WebElement alertSucces;
//    @FindBy(id = "alertDanger")
//    private WebElement alertDanger;
//    @FindBy(id = "uitloggen")
//    private WebElement uitloggen;
//
//    public String leesFoutmelding() {
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(Hulp.zoekTimeOut);
//        while ((alertDanger.getText() == null || alertDanger.getText().equals("")) && LocalDateTime.now().isBefore(timeOut)) {
//            Hulp.wachtFf();
//        }
//        return alertDanger.getText();
//    }
//
//    public String leesmelding() {
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(Hulp.zoekTimeOut);
//        while ((alertSucces.getText() == null || alertSucces.getText().equals("")) && LocalDateTime.now().isBefore(timeOut)) {
//            Hulp.wachtFf();
//        }
//        return alertSucces.getText();
//    }
//
//    public void uitloggen() {
//        Hulp.klikEnWacht(uitloggen);
//    }
//}
