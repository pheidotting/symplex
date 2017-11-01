//package nl.dias.web.pagina;
//
//import nl.dias.dias_web.hulp.Hulp;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class PaginaMetMenuBalk extends IndexPagina {
//    @FindBy(id = "beherenRelatie")
//    private WebElement beherenRelatie;
//    @FindBy(id = "bedrijven")
//    private WebElement bedrijven;
//    @FindBy(id = "bedrijf")
//    private WebElement bedrijf;
//    @FindBy(id = "polissen")
//    private WebElement polissen;
//    @FindBy(id = "polis")
//    private WebElement polis;
//    @FindBy(id = "schades")
//    private WebElement schades;
//    @FindBy(id = "schade")
//    private WebElement schade;
//    @FindBy(id = "bijlages")
//    private WebElement bijlages;
//
//    public enum MenuItem {
//        BEHERENRELATIE, BEDRIJVEN, BEDRIJF, POLISSEN, POLIS, SCHADES, SCHADE, BIJLAGES;
//    }
//
//    public void klikMenuItemAan(MenuItem menuItem, WebDriver driver) {
//        String vorigeUrl = driver.getCurrentUrl();
//        do {
//            switch (menuItem) {
//            case BEDRIJF:
//                Hulp.klikEnWacht(bedrijf);
//                Hulp.wachtFf();
//                break;
//            case BEDRIJVEN:
//                Hulp.klikEnWacht(bedrijven);
//                Hulp.wachtFf();
//                break;
//            case BEHERENRELATIE:
//                Hulp.klikEnWacht(beherenRelatie);
//                Hulp.wachtFf();
//                break;
//            case POLISSEN:
//                Hulp.klikEnWacht(polissen);
//                Hulp.wachtFf();
//                break;
//            case POLIS:
//                Hulp.klikEnWacht(polis);
//                Hulp.wachtFf();
//                break;
//            case SCHADES:
//                Hulp.klikEnWacht(schades);
//                Hulp.wachtFf();
//                break;
//            case SCHADE:
//                Hulp.klikEnWacht(schade);
//                Hulp.wachtFf();
//                break;
//            case BIJLAGES:
//                Hulp.klikEnWacht(bijlages);
//                Hulp.wachtFf();
//                break;
//            default:
//                break;
//            }
//        } while (vorigeUrl.equals(driver.getCurrentUrl()));
//    }
//}
