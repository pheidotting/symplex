//package nl.dias.web.pagina;
//
//import java.util.List;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.json.JsonLijstRelaties;
//import nl.dias.domein.json.JsonRelatie;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class LijstRelaties {
//    @FindBy(name = "voornaam")
//    private List<WebElement> voornaam;
//    @FindBy(name = "tussenvoegsel")
//    private List<WebElement> tussenvoegsel;
//    @FindBy(name = "achternaam")
//    private List<WebElement> achternaam;
//    @FindBy(name = "geboortedatum")
//    private List<WebElement> geboortedatum;
//    @FindBy(name = "adres")
//    private List<WebElement> adres;
//    @FindBy(id = "toevoegenNieuweRelatie")
//    private WebElement toevoegenNieuweRelatie;
//
//    public WebElement getToevoegenNieuweRelatie() {
//        return toevoegenNieuweRelatie;
//    }
//
//    public boolean checkVelden(JsonLijstRelaties jsonLijstRelaties) {
//        boolean ok = true;
//
//        Hulp.wachtFf();
//        if (voornaam.size() != jsonLijstRelaties.getJsonRelaties().size()) {
//            ok = false;
//        }
//
//        int index = 0;
//        for (JsonRelatie jsonRelatie : jsonLijstRelaties.getJsonRelaties()) {
//            Hulp.wachtFf();
//            if (!jsonRelatie.getVoornaam().equals(voornaam.get(index).getText())) {
//                ok = false;
//            }
//            Hulp.wachtFf();
//            if (!jsonRelatie.getTussenvoegsel().equals(tussenvoegsel.get(index).getText())) {
//                ok = false;
//            }
//            Hulp.wachtFf();
//            if (!jsonRelatie.getAchternaam().equals(achternaam.get(index).getText())) {
//                ok = false;
//            }
//            Hulp.wachtFf();
//            if (!jsonRelatie.getGeboorteDatum().equals(geboortedatum.get(index).getText())) {
//                ok = false;
//            }
//            Hulp.wachtFf();
//            if (!jsonRelatie.getAdresOpgemaakt().equals(adres.get(index).getText())) {
//                ok = false;
//            }
//            Hulp.wachtFf();
//            index++;
//        }
//
//        return ok;
//    }
//
//    public boolean zoekRelatieOpEnKlikDezeAan(JsonRelatie jsonRelatie) {
//        return zoekRelatieOpEnKlikDezeAan(jsonRelatie, false);
//    }
//
//    public boolean zoekRelatieOpEnKlikDezeAan(JsonRelatie jsonRelatie, boolean recursief) {
//        Integer gevondenIndex = null;
//        boolean gevonden = false;
//
//        for (int i = 0; i < voornaam.size(); i++) {
//            if (recordKomtOvereen(jsonRelatie, i)) {
//                gevondenIndex = i;
//                break;
//            }
//        }
//        if (gevondenIndex != null) {
//            voornaam.get(gevondenIndex).click();
//            Hulp.wachtFf();
//            gevonden = true;
//        }
//        if (!gevonden && !recursief) {
//            gevonden = zoekRelatieOpEnKlikDezeAan(jsonRelatie, true);
//        }
//        return gevonden;
//    }
//
//    public void toevoegenNieuweRelatie() {
//        Hulp.klikEnWacht(toevoegenNieuweRelatie);
//    }
//
//    private boolean recordKomtOvereen(JsonRelatie jsonRelatie, int index) {
//        boolean komtOvereen = true;
//
//        Hulp.wachtFf(3000);
//
//        String voornaam = this.voornaam.get(index).getText();
//        String tussenvoegsel = this.tussenvoegsel.get(index).getText();
//        String achternaam = this.achternaam.get(index).getText();
//        String geboortedatum = this.geboortedatum.get(index).getText();
//        String adres = this.adres.get(index).getText();
//
//        if (!jsonRelatie.getVoornaam().equals(voornaam)) {
//            komtOvereen = false;
//        }
//        if (!jsonRelatie.getTussenvoegsel().equals(tussenvoegsel)) {
//            komtOvereen = false;
//        }
//        if (!jsonRelatie.getAchternaam().equals(achternaam)) {
//            komtOvereen = false;
//        }
//        if (!jsonRelatie.getGeboorteDatum().equals(geboortedatum)) {
//            komtOvereen = false;
//        }
//        if (!jsonRelatie.getAdresOpgemaakt().trim().equals(adres.trim())) {
//            komtOvereen = false;
//        }
//
//        return komtOvereen;
//    }
//
//}
