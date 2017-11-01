//package nl.dias.web.pagina;
//
//import java.util.List;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.Bedrag;
//import nl.dias.domein.json.JsonSchade;
//import nl.dias.web.mapper.PolisMapper;
//
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class SchadeOverzicht {
//    @FindBy(name = "dropdown")
//    private List<WebElement> dropdown;
//    @FindBy(name = "plaatsOpmerking")
//    private List<WebElement> plaatsOpmerking;
//    @FindBy(name = "bewerkSchade")
//    private List<WebElement> bewerkSchade;
//    @FindBy(name = "titel")
//    private List<WebElement> titel;
//    @FindBy(name = "schadeNummerMaatschappij")
//    private List<WebElement> schadeNummerMaatschappij;
//    @FindBy(name = "schadeNummerTussenPersoon")
//    private List<WebElement> schadeNummerTussenPersoon;
//    @FindBy(name = "locatie")
//    private List<WebElement> locatie;
//    @FindBy(name = "statusSchade")
//    private List<WebElement> statusSchade;
//    @FindBy(name = "datumTijdSchade")
//    private List<WebElement> datumTijdSchade;
//    @FindBy(name = "datumTijdMelding")
//    private List<WebElement> datumTijdMelding;
//    @FindBy(name = "datumAfgehandeld")
//    private List<WebElement> datumAfgehandeld;
//    @FindBy(name = "eigenRisico")
//    private List<WebElement> eigenRisico;
//    @FindBy(name = "omschrijving")
//    private List<WebElement> omschrijving;
//    @FindBy(name = "opmerking")
//    private List<WebElement> opmerking;
//
//    public int zoekSchade(JsonSchade jsonSchade) {
//        int gevonden = 0;
//        boolean werdGevonden = false;
//        for (WebElement element : titel) {
//            if (element.getText().equals(jsonSchade.getSoortSchade() + " (" + jsonSchade.getSchadeNummerMaatschappij() + ")")) {
//                gevonden++;
//                werdGevonden = true;
//                break;
//            }
//            gevonden++;
//        }
//        if (!werdGevonden) {
//            gevonden = -1;
//        } else {
//            --gevonden;
//        }
//        return gevonden;
//    }
//
//    public String controleerSchade(JsonSchade jsonSchade) {
//        StringBuilder sb = new StringBuilder();
//        int nummer = zoekSchade(jsonSchade);
//        if (nummer == -1) {
//            return jsonSchade.getSoortSchade() + " (" + jsonSchade.getSchadeNummerMaatschappij() + ")" + " niet gevonden";
//        }
//        Hulp.klikEnWacht(titel.get(nummer));
//        if (!Hulp.controleerVeld(this.schadeNummerMaatschappij.get(nummer), (jsonSchade.getSchadeNummerMaatschappij()))) {
//            sb.append("|").append(Hulp.getText(this.schadeNummerMaatschappij.get(nummer))).append(",").append((jsonSchade.getSchadeNummerMaatschappij())).append(";")
//                    .append("schadeNummerMaatschappij");
//        }
//        if (!Hulp.controleerVeld(this.schadeNummerTussenPersoon.get(nummer), (jsonSchade.getSchadeNummerTussenPersoon()))) {
//            sb.append("|").append(Hulp.getText(this.schadeNummerTussenPersoon.get(nummer))).append(",").append((jsonSchade.getSchadeNummerTussenPersoon())).append(";")
//                    .append("schadeNummerTussenPersoon");
//        }
//        if (!Hulp.controleerVeld(this.locatie.get(nummer), (jsonSchade.getLocatie()))) {
//            sb.append("|").append(Hulp.getText(this.locatie.get(nummer))).append(",").append((jsonSchade.getLocatie())).append(";").append("locatie");
//        }
//        if (!Hulp.controleerVeld(this.statusSchade.get(nummer), (jsonSchade.getStatusSchade()))) {
//            sb.append("|").append(Hulp.getText(this.statusSchade.get(nummer))).append(",").append((jsonSchade.getStatusSchade())).append(";").append("statusSchade");
//        }
//        if (!Hulp.controleerVeld(this.datumTijdSchade.get(nummer), (jsonSchade.getDatumTijdSchade()))) {
//            sb.append("|").append(Hulp.getText(this.datumTijdSchade.get(nummer))).append(",").append((jsonSchade.getDatumTijdSchade())).append(";").append("datumTijdSchade");
//        }
//        if (!Hulp.controleerVeld(this.datumTijdMelding.get(nummer), (jsonSchade.getDatumTijdMelding()))) {
//            sb.append("|").append(Hulp.getText(this.datumTijdMelding.get(nummer))).append(",").append((jsonSchade.getDatumTijdMelding())).append(";").append("datumTijdMelding");
//        }
//        if (!Hulp.controleerVeld(this.datumAfgehandeld.get(nummer), (jsonSchade.getDatumAfgehandeld()))) {
//            sb.append("|").append(Hulp.getText(this.datumAfgehandeld.get(nummer))).append(",").append((jsonSchade.getDatumAfgehandeld())).append(";").append("datumAfgehandeld");
//        }
//        if (!Hulp.controleerVeld(this.eigenRisico.get(nummer), (PolisMapper.zetBedragOm(new Bedrag(jsonSchade.getEigenRisico()))))) {
//            sb.append("|").append(Hulp.getText(this.eigenRisico.get(nummer))).append(",").append((PolisMapper.zetBedragOm(new Bedrag(jsonSchade.getEigenRisico())))).append(";").append("eigenRisico");
//        }
//        if (!Hulp.controleerVeld(this.omschrijving.get(nummer), (jsonSchade.getOmschrijving()))) {
//            sb.append("|").append(Hulp.getText(this.omschrijving.get(nummer))).append(",").append((jsonSchade.getOmschrijving())).append(";").append("omschrijving");
//        }
//        Hulp.klikEnWacht(titel.get(nummer));
//        return sb.toString();
//    }
//
//    public void opmerkingBijSchade(JsonSchade jsonSchade) {
//        int nummer = zoekSchade(jsonSchade);
//
//        Hulp.doeCheckMetTimeOut(nummer < dropdown.size());
//
//        Hulp.klikEnWacht(dropdown.get(nummer));
//        Hulp.klikEnWacht(plaatsOpmerking.get(nummer));
//    }
//
//    public void bewerkSchade(JsonSchade jsonSchade) {
//        int nummer = zoekSchade(jsonSchade);
//
//        Hulp.doeCheckMetTimeOut(nummer < dropdown.size());
//
//        Hulp.klikEnWacht(dropdown.get(nummer));
//        Hulp.klikEnWacht(bewerkSchade.get(nummer));
//    }
//
//    public String controleerSchades(JsonSchade... schades) {
//        StringBuilder sb = new StringBuilder();
//        int aantalOpmerkingen = 0;
//
//        if (schades.length != titel.size()) {
//            sb.append("aantal komt niet overeen");
//        }
//
//        for (JsonSchade s : schades) {
//            sb.append(controleerSchade(s));
//            aantalOpmerkingen = aantalOpmerkingen + s.getOpmerkingen().size();
//        }
//
//        if (aantalOpmerkingen != opmerking.size()) {
//            sb.append("aantal opmerkingen komt niet overeen, verwacht : " + aantalOpmerkingen + ", maar was : " + opmerking.size());
//        }
//
//        return sb.toString();
//    }
//}
