//package nl.dias.web.pagina;
//
//import java.util.List;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.Bedrag;
//import nl.dias.domein.json.JsonPolis;
//import nl.dias.web.mapper.PolisMapper;
//
//import org.joda.time.LocalDateTime;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//public class PolisOverzicht {
//    @FindBy(name = "titelPolis")
//    private List<WebElement> titel;
//    @FindBy(name = "dropdown")
//    private List<WebElement> dropdown;
//    @FindBy(name = "bewerkPolis")
//    private List<WebElement> bewerkPolis;
//    // private List<WebElement> schadeMelden;
//    @FindBy(name = "verwijderPolis")
//    private List<WebElement> verwijderen;
//    @FindBy(name = "polisNummer")
//    private List<WebElement> polisnummer;
//    @FindBy(name = "ingangsDatum")
//    private List<WebElement> ingangsdatum;
//    @FindBy(name = "wijzigingsDatum")
//    private List<WebElement> wijzigingsdatum;
//    @FindBy(name = "prolongatieDatum")
//    private List<WebElement> prolongatieDatum;
//    @FindBy(name = "maatschappij")
//    private List<WebElement> maatschappij;
//    @FindBy(name = "premie")
//    private List<WebElement> premie;
//    @FindBy(name = "betaalfrequentie")
//    private List<WebElement> betaalfrequentie;
//    @FindBy(name = "bedrijf")
//    private List<WebElement> bedrijf;
//
//    public int zoekPolis(JsonPolis jsonPolis) {
//        return zoekPolis(jsonPolis, true);
//    }
//
//    public int zoekPolis(JsonPolis jsonPolis, boolean doorgaan) {
//        int gevonden = 0;
//        boolean werdGevonden = false;
//        for (WebElement element : titel) {
//            if (element.getText().equals(jsonPolis.getTitel())) {
//                gevonden++;
//                werdGevonden = true;
//                break;
//            }
//            gevonden++;
//        }
//        if (!werdGevonden) {
//            if (doorgaan) {
//                gevonden = zoekPolis(jsonPolis, false);
//            }
//            gevonden = -1;
//        }
//        return gevonden;
//    }
//
//    public void bewerkPolis(int nummer) {
//        --nummer;
//        Hulp.klikEnWacht(dropdown.get(nummer));
//        Hulp.klikEnWacht(bewerkPolis.get(nummer));
//    }
//
//    public String controleerPolis(JsonPolis jsonPolis) {
//        StringBuilder sb = new StringBuilder();
//        int nummer = zoekPolis(jsonPolis);
//        if (nummer == -1) {
//            return jsonPolis.getTitel() + " niet gevonden";
//        }
//        --nummer;
//        Hulp.klikEnWacht(titel.get(nummer));
//        if (!Hulp.controleerVeld(this.polisnummer.get(nummer), (jsonPolis.getPolisNummer()))) {
//            sb.append("|").append(Hulp.getText(this.polisnummer.get(nummer))).append(",").append((jsonPolis.getPolisNummer())).append(";").append("polisnummer");
//        }
//        if (!"".equals(jsonPolis.getIngangsDatum())) {
//            if (!Hulp.controleerVeld(this.ingangsdatum.get(nummer), (jsonPolis.getIngangsDatum()))) {
//                sb.append("|").append(Hulp.getText(this.ingangsdatum.get(nummer))).append(",").append((jsonPolis.getIngangsDatum())).append(";").append("ingangsdatum");
//            }
//        }
//        if (!"".equals(jsonPolis.getWijzigingsDatum())) {
//            if (!Hulp.controleerVeld(this.wijzigingsdatum.get(nummer), (jsonPolis.getWijzigingsDatum()))) {
//                sb.append("|").append(Hulp.getText(this.wijzigingsdatum.get(nummer))).append(",").append((jsonPolis.getWijzigingsDatum())).append(";").append("wijzigingsdatum");
//            }
//        }
//        if ("".equals(jsonPolis.getProlongatieDatum())) {
//            if (!Hulp.controleerVeld(this.prolongatieDatum.get(nummer), (jsonPolis.getProlongatieDatum()))) {
//                sb.append("|").append(Hulp.getText(this.prolongatieDatum.get(nummer))).append(",").append((jsonPolis.getProlongatieDatum())).append(";").append("prolongatieDatum");
//            }
//        }
//        if (!Hulp.controleerVeld(this.maatschappij.get(nummer), (jsonPolis.getMaatschappij()))) {
//            sb.append("|").append(Hulp.getText(this.maatschappij.get(nummer))).append(",").append((jsonPolis.getMaatschappij())).append(";").append("maatschappij");
//        }
//        if (!Hulp.controleerVeld(this.premie.get(nummer), (PolisMapper.zetBedragOm(new Bedrag(jsonPolis.getPremie()))))) {
//            sb.append("|").append(Hulp.getText(this.premie.get(nummer))).append(",").append((PolisMapper.zetBedragOm(new Bedrag(jsonPolis.getPremie())))).append(";").append("premie");
//        }
//        if (!Hulp.controleerVeld(this.betaalfrequentie.get(nummer), (jsonPolis.getBetaalfrequentie()))) {
//            sb.append("|").append(Hulp.getText(this.betaalfrequentie.get(nummer))).append(",").append((jsonPolis.getBetaalfrequentie())).append(";").append("betaalfrequentie");
//        }
//        if (bedrijf != null && bedrijf.size() > 0) {
//            if (!Hulp.controleerVeld(this.bedrijf.get(nummer), (jsonPolis.getBedrijf()))) {
//                sb.append("|").append(Hulp.getText(this.bedrijf.get(nummer))).append(",").append((jsonPolis.getBedrijf())).append(";").append("bedrijf");
//            }
//        }
//        Hulp.klikEnWacht(titel.get(nummer));
//        return sb.toString();
//    }
//
//    public String controleerPolissen(JsonPolis... polissen) {
//        StringBuilder sb = new StringBuilder();
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(30);
//        while (polissen.length != titel.size() && LocalDateTime.now().isBefore(timeOut)) {
//            Hulp.wachtFf();
//        }
//        if (polissen.length != titel.size()) {
//            sb.append("aantal komt niet overeen");
//        }
//        for (JsonPolis p : polissen) {
//            sb.append(controleerPolis(p));
//        }
//        return sb.toString();
//    }
//}
