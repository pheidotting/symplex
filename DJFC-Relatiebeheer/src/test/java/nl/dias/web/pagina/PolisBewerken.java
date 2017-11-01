//package nl.dias.web.pagina;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.json.JsonPolis;
//
//import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class PolisBewerken {
//    @FindBy(id = "verzekeringsMaatschappijen")
//    private WebElement verzekeringsMaatschappijen;
//    @FindBy(id = "soortVerzekering")
//    private WebElement soortVerzekering;
//    @FindBy(id = "polisId")
//    private WebElement polisId;
//    @FindBy(id = "polisNummer")
//    private WebElement polisNummer;
//    @FindBy(id = "premie")
//    private WebElement premie;
//    @FindBy(id = "ingangsDatumString")
//    private WebElement ingangsDatumString;
//    @FindBy(id = "wijzigingsdatumString")
//    private WebElement wijzigingsdatumString;
//    @FindBy(id = "prolongatiedatumString")
//    private WebElement prolongatiedatumString;
//    @FindBy(id = "betaalfrequentie")
//    private WebElement betaalfrequentie;
//    @FindBy(id = "bedrijfBijPolis")
//    private WebElement bedrijfBijPolis;
//    @FindBy(id = "uploadPolis1File")
//    private WebElement uploadPolis1File;
//    @FindBy(id = "opslaanPolis")
//    private WebElement opslaanPolis;
//
//    public void vulVelden(JsonPolis jsonPolis) {
//        Hulp.wachtFf();
//        Hulp.selecteerUitSelectieBox(verzekeringsMaatschappijen, jsonPolis.getMaatschappij());
//        Hulp.wachtFf();
//        Hulp.selecteerUitSelectieBox(soortVerzekering, jsonPolis.getSoort());
//        Hulp.wachtFf();
//        Hulp.vulVeld(polisNummer, jsonPolis.getPolisNummer());
//        Hulp.wachtFf();
//        Hulp.vulVeld(premie, jsonPolis.getPremie());
//        Hulp.wachtFf();
//        Hulp.vulVeld(ingangsDatumString, jsonPolis.getIngangsDatum());
//        Hulp.wachtFf();
//        Hulp.vulVeld(wijzigingsdatumString, jsonPolis.getWijzigingsDatum());
//        Hulp.wachtFf();
//        Hulp.vulVeld(prolongatiedatumString, jsonPolis.getProlongatieDatum());
//        Hulp.wachtFf();
//        Hulp.selecteerUitSelectieBox(betaalfrequentie, jsonPolis.getBetaalfrequentie());
//        Hulp.wachtFf();
//        if (jsonPolis.getBedrijf() != null) {
//            Hulp.selecteerUitSelectieBox(bedrijfBijPolis, jsonPolis.getBedrijf());
//            Hulp.wachtFf();
//        }
//
//        drukOpOpslaan();
//    }
//
//    public void vulVeldenEnDrukOpOpslaan(JsonPolis jsonPolis) {
//        vulVelden(jsonPolis);
//        Hulp.wachtFf();
//        drukOpOpslaan();
//    }
//
//    public void drukOpOpslaan() {
//        Hulp.klikEnWacht(opslaanPolis);
//    }
//
//    public boolean isBedrijfBijPolisZichtbaar() {
//        Hulp.wachtFf();
//        try {
//            return bedrijfBijPolis.isDisplayed();
//        } catch (NoSuchElementException e) {
//            return false;
//        }
//    }
//}
