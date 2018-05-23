//package nl.dias.web.pagina;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.json.JsonSchade;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class SchadeBewerken extends PaginaMetMenuBalk {
//    @FindBy(id = "polisVoorSchademelding")
//    private WebElement polisVoorSchademelding;
//    @FindBy(id = "schadeNummerMaatschappij")
//    private WebElement schadeNummerMaatschappij;
//    @FindBy(id = "schadeNummerTussenpersoon")
//    private WebElement schadeNummerTussenpersoon;
//    @FindBy(id = "soortSchade")
//    private WebElement soortSchade;
//    @FindBy(id = "locatieSchade")
//    private WebElement locatieSchade;
//    @FindBy(id = "statusSchade")
//    private WebElement statusSchade;
//    @FindBy(id = "datumTijdSchade")
//    private WebElement datumTijdSchade;
//    @FindBy(id = "datumTijdMelding")
//    private WebElement datumTijdMelding;
//    @FindBy(id = "datumAfgehandeld")
//    private WebElement datumAfgehandeld;
//    @FindBy(id = "eigenRisico")
//    private WebElement eigenRisico;
//    @FindBy(id = "omschrijving")
//    private WebElement omschrijving;
//    @FindBy(id = "uploadSchade1File")
//    private WebElement uploadSchade1File;
//    @FindBy(id = "uploadSchade2File")
//    private WebElement uploadSchade2File;
//    @FindBy(id = "uploadSchade3File")
//    private WebElement uploadSchade3File;
//    @FindBy(id = "uploadSchade4File")
//    private WebElement uploadSchade4File;
//    @FindBy(id = "uploadSchade5File")
//    private WebElement uploadSchade5File;
//    @FindBy(id = "uploadSchade6File")
//    private WebElement uploadSchade6File;
//    @FindBy(id = "uploadSchade7File")
//    private WebElement uploadSchade7File;
//    @FindBy(id = "uploadSchade8File")
//    private WebElement uploadSchade8File;
//    @FindBy(id = "uploadSchade9File")
//    private WebElement uploadSchade9File;
//    @FindBy(id = "uploadSchade10File")
//    private WebElement uploadSchade10File;
//    @FindBy(id = "schadeMeldingOpslaan")
//    private WebElement schadeMeldingOpslaan;
//
//    public void vulVelden(JsonSchade jsonSchade) {
//        Hulp.wachtFf();
//        Hulp.vulVeld(schadeNummerMaatschappij, jsonSchade.getSchadeNummerMaatschappij());
//        Hulp.wachtFf();
//        Hulp.vulVeld(schadeNummerTussenpersoon, jsonSchade.getSchadeNummerTussenPersoon());
//        Hulp.wachtFf();
//        Hulp.vulVeld(soortSchade, jsonSchade.getSoortSchade());
//        Hulp.wachtFf();
//        Hulp.vulVeld(locatieSchade, jsonSchade.getLocatie());
//        Hulp.wachtFf();
//        Hulp.selecteerUitSelectieBox(statusSchade, jsonSchade.getStatusSchade());
//        Hulp.wachtFf();
//        Hulp.vulVeld(datumTijdSchade, jsonSchade.getDatumSchade());
//        Hulp.wachtFf();
//        Hulp.vulVeld(datumTijdMelding, jsonSchade.getDatumMelding());
//        Hulp.wachtFf();
//        Hulp.vulVeld(datumAfgehandeld, jsonSchade.getDatumAfgehandeld());
//        Hulp.wachtFf();
//        Hulp.vulVeld(eigenRisico, jsonSchade.getEigenRisico());
//        Hulp.wachtFf();
//        Hulp.vulVeld(omschrijving, jsonSchade.getOmschrijving());
//        Hulp.wachtFf();
//        Hulp.selecteerUitSelectieBox(polisVoorSchademelding, jsonSchade.getPolis().toString());
//    }
//
//    public void vulVeldenEnDrukOpOpslaan(JsonSchade jsonSchade) {
//        vulVelden(jsonSchade);
//        Hulp.wachtFf();
//        drukOpOpslaan();
//    }
//
//    public void drukOpOpslaan() {
//        Hulp.klikEnWacht(schadeMeldingOpslaan);
//    }
//
//}
