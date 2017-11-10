//package nl.dias.web.pagina;
//
//import nl.dias.dias_web.hulp.Hulp;
//import nl.dias.domein.json.JsonRelatie;
//import org.joda.time.LocalDateTime;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BeherenRelatie extends PaginaMetMenuBalk {
//    @FindBy(id = "voornaam")
//    private WebElement voornaam;
//    @FindBy(id = "achternaam")
//    private WebElement achternaam;
//    @FindBy(id = "tussenvoegsel")
//    private WebElement tussenvoegsel;
//    @FindBy(id = "straat")
//    private WebElement straat;
//    @FindBy(id = "huisnummer")
//    private WebElement huisnummer;
//    @FindBy(id = "toevoeging")
//    private WebElement toevoeging;
//    @FindBy(id = "postcode")
//    private WebElement postcode;
//    @FindBy(id = "plaats")
//    private WebElement plaats;
//    @FindBy(id = "bsn")
//    private WebElement bsn;
//    @FindBy(id = "emailadres")
//    private WebElement emailadres;
//    @FindBy(id = "geboorteDatum")
//    private WebElement geboorteDatum;
//    @FindBy(id = "overlijdensdatum")
//    private WebElement overlijdensdatum;
//    @FindBy(id = "geslacht")
//    private WebElement geslacht;
//    @FindBy(id = "burgerlijkeStaat")
//    private WebElement burgerlijkeStaat;
//
//    @FindBy(id = "opslaanRelatie")
//    private WebElement opslaanrelatie;
//    @FindBy(id = "verwijderen")
//    private WebElement verwijderen;
//
//    // Rekeningnummer
//    @FindBy(id = "voegRekeningToe")
//    private WebElement voegRekeningToe;
//    @FindBy(name = "rekeningid")
//    private List<WebElement> rekeningid;
//    @FindBy(name = "rekeningnummer")
//    private List<WebElement> rekeningnummer;
//    @FindBy(name = "bic")
//    private List<WebElement> bic;
//    @FindBy(name = "verwijderRekening")
//    private List<WebElement> verwijderRekening;
//
//    // Telefoonummer
//    @FindBy(id = "voegTelefoonNummerToe")
//    private WebElement voegTelefoonNummerToe;
//    @FindBy(name = "telefoonid")
//    private List<WebElement> telefoonid;
//    @FindBy(name = "telnummer")
//    private List<WebElement> telnummer;
//    @FindBy(name = "soorttelnummer")
//    private List<WebElement> soorttelnummer;
//    @FindBy(name = "verwijderTelefoonNummer")
//    private List<WebElement> verwijderTelefoonNummer;
//
//    // Validatie
//    @FindBy(className = "validationMessage")
//    private List<WebElement> validatieFouten;
//
//    public int aantalFouten() {
//        LocalDateTime timeOut = new LocalDateTime().plusSeconds(Hulp.zoekTimeOut);
//        while (getValidatieFouten().size() == 0 && LocalDateTime.now().isBefore(timeOut)) {
//            Hulp.wachtFf();
//        }
//        return getValidatieFouten().size();
//    }
//
//    public List<WebElement> getValidatieFouten() {
//        List<WebElement> lijst = new ArrayList<>();
//        for (WebElement e : validatieFouten) {
//            if (e.isDisplayed()) {
//                lijst.add(e);
//            }
//        }
//        return lijst;
//    }
//
//    public void vulVeldenEnDrukOpOpslaan(String voornaam, String achternaam, String tussenvoegsel, String straat, String huisnummer, String toevoeging, String postcode, String plaats, String bsn,
//            String emailadres, String geboorteDatum, String overlijdensdatum, String geslacht, String burgerlijkeStaat, List<BeherenRelatieRekeningnummer> rekeningnummers,
//            List<BeherenRelatieTelefoonnummer> telefoonnummers) {
//        vulVelden(voornaam, achternaam, tussenvoegsel, straat, huisnummer, toevoeging, postcode, plaats, bsn, emailadres, geboorteDatum, overlijdensdatum, geslacht, burgerlijkeStaat, rekeningnummers,
//                telefoonnummers);
//        drukOpOpslaan();
//    }
//
//    public void vulVelden(String voornaam, String achternaam, String tussenvoegsel, String straat, String huisnummer, String toevoeging, String postcode, String plaats, String bsn, String emailadres,
//            String geboorteDatum, String overlijdensdatum, String geslacht, String burgerlijkeStaat, List<BeherenRelatieRekeningnummer> rekeningnummers,
//            List<BeherenRelatieTelefoonnummer> telefoonnummers) {
//        Hulp.wachtFf();
//        Hulp.vulVeld(this.voornaam, voornaam);
//        Hulp.vulVeld(this.achternaam, achternaam);
//        Hulp.vulVeld(this.tussenvoegsel, tussenvoegsel);
//        Hulp.vulVeld(this.straat, straat);
//        Hulp.vulVeld(this.huisnummer, huisnummer);
//        Hulp.vulVeld(this.toevoeging, toevoeging);
//        Hulp.vulVeld(this.postcode, postcode);
//        Hulp.vulVeld(this.plaats, plaats);
//        Hulp.vulVeld(this.bsn, bsn);
//        Hulp.vulVeld(this.geboorteDatum, geboorteDatum);
//        Hulp.vulVeld(this.overlijdensdatum, overlijdensdatum);
//        Hulp.vulVeld(this.emailadres, emailadres);
//        if (geslacht != null) {
//            Hulp.selecteerUitSelectieBox(this.geslacht, geslacht);
//        }
//        if (burgerlijkeStaat != null) {
//            Hulp.selecteerUitSelectieBox(this.burgerlijkeStaat, burgerlijkeStaat);
//        }
//        if (rekeningnummers != null) {
//            for (BeherenRelatieRekeningnummer rekeningnummer : rekeningnummers) {
//                Hulp.klikEnWacht(this.voegRekeningToe);
//                Hulp.vulVeld(this.rekeningnummer.get(this.rekeningnummer.size() - 1), rekeningnummer.getRekeninnummer());
//                Hulp.vulVeld(this.bic.get(this.bic.size() - 1), rekeningnummer.getBic());
//            }
//        }
//        if (telefoonnummers != null) {
//            for (BeherenRelatieTelefoonnummer telefoonnummer : telefoonnummers) {
//                Hulp.klikEnWacht(this.voegTelefoonNummerToe);
//                Hulp.vulVeld(this.telnummer.get(this.telnummer.size() - 1), telefoonnummer.getTelefoonnummer());
//                Hulp.selecteerUitSelectieBox(this.soorttelnummer.get(this.soorttelnummer.size() - 1), telefoonnummer.getSoortTelefoonnummer().toUpperCase());
//            }
//        }
//    }
//
//    public String checkVelden(JsonRelatie jsonRelatie) {
//        StringBuilder sb = new StringBuilder();
//
//        if (!Hulp.controleerVeld(this.voornaam, jsonRelatie.getVoornaam())) {
//            sb.append("|").append(Hulp.getText(this.voornaam)).append(",").append(jsonRelatie.getVoornaam()).append(";").append("voornaam");
//        }
//        if (!Hulp.controleerVeld(this.achternaam, jsonRelatie.getAchternaam())) {
//            sb.append("|").append(Hulp.getText(this.achternaam)).append(",").append(jsonRelatie.getAchternaam()).append(";").append("achternaam");
//        }
//        if (!Hulp.controleerVeld(this.tussenvoegsel, jsonRelatie.getTussenvoegsel())) {
//            sb.append("|").append(Hulp.getText(this.tussenvoegsel)).append(",").append(jsonRelatie.getTussenvoegsel()).append(";").append("tussenvoegsel");
//        }
//        //        if (!Hulp.controleerVeld(this.straat, jsonRelatie.getStraat())) {
//        //            sb.append("|").append(Hulp.getText(this.straat)).append(",").append(jsonRelatie.getStraat()).append(";").append("straat");
//        //        }
//        //        if (!Hulp.controleerVeld(this.huisnummer, jsonRelatie.getHuisnummer())) {
//        //            sb.append("|").append(Hulp.getText(this.huisnummer)).append(",").append(jsonRelatie.getHuisnummer()).append(";").append("huisnummer");
//        //        }
//        //        if (!Hulp.controleerVeld(this.toevoeging, jsonRelatie.getToevoeging())) {
//        //            sb.append("|").append(Hulp.getText(this.toevoeging)).append(",").append(jsonRelatie.getToevoeging()).append(";").append("toevoeging");
//        //        }
//        //        if (!Hulp.controleerVeld(this.postcode, jsonRelatie.getPostcode())) {
//        //            sb.append("|").append(Hulp.getText(this.postcode)).append(",").append(jsonRelatie.getPostcode()).append(";").append("postcode");
//        //        }
//        //        if (!Hulp.controleerVeld(this.plaats, jsonRelatie.getPlaats())) {
//        //            sb.append("|").append(Hulp.getText(this.plaats)).append(",").append(jsonRelatie.getPlaats()).append(";").append("plaats");
//        //        }
//        if (!Hulp.controleerVeld(this.bsn, jsonRelatie.getBsn())) {
//            sb.append("|").append(Hulp.getText(this.bsn)).append(",").append(jsonRelatie.getBsn()).append(";").append("bsn");
//        }
//        if (!Hulp.controleerVeld(this.emailadres, jsonRelatie.getIdentificatie())) {
//            sb.append("|").append(Hulp.getText(this.emailadres)).append(",").append(jsonRelatie.getIdentificatie()).append(";").append("emailadres");
//        }
//        if (!Hulp.controleerVeld(this.geboorteDatum, jsonRelatie.getGeboorteDatum())) {
//            sb.append("|").append(Hulp.getText(this.geboorteDatum)).append(",").append(jsonRelatie.getGeboorteDatum()).append(";").append("geboorteDatum");
//        }
//        if (!Hulp.controleerVeld(this.overlijdensdatum, jsonRelatie.getOverlijdensdatum())) {
//            sb.append("|").append(Hulp.getText(this.overlijdensdatum)).append(",").append(jsonRelatie.getOverlijdensdatum()).append(";").append("overlijdensdatum");
//        }
//        if (!Hulp.controleerVeld(this.geslacht, jsonRelatie.getGeslacht())) {
//            sb.append("|").append(Hulp.getText(this.geslacht)).append(",").append(jsonRelatie.getGeslacht()).append(";").append("geslacht");
//        }
//        if (!Hulp.controleerVeld(this.burgerlijkeStaat, jsonRelatie.getBurgerlijkeStaat())) {
//            sb.append("|").append(Hulp.getText(this.burgerlijkeStaat)).append(",").append(jsonRelatie.getBurgerlijkeStaat()).append(";").append("burgerlijkeStaat");
//        }
//        if (!Hulp.controleerVeld(this.geslacht, jsonRelatie.getGeslacht())) {
//            sb.append("|").append(Hulp.getText(this.geslacht)).append(",").append(jsonRelatie.getGeslacht()).append(";").append("geslacht");
//        }
//
//        for (int i = 0; i < jsonRelatie.getRekeningnummers().size(); i++) {
//            if (!Hulp.controleerVeld(this.rekeningnummer.get(i), jsonRelatie.getRekeningnummers().get(i).getRekeningnummer())) {
//                sb.append("|").append(Hulp.getText(this.rekeningnummer.get(i))).append(",").append(jsonRelatie.getRekeningnummers().get(i).getRekeningnummer()).append(";")
//                        .append("rekeningnummer" + i);
//            }
//            if (!Hulp.controleerVeld(this.bic.get(i), jsonRelatie.getRekeningnummers().get(i).getBic())) {
//                sb.append("|").append(Hulp.getText(this.bic.get(i))).append(",").append(jsonRelatie.getRekeningnummers().get(i).getBic()).append(";").append("bic" + i);
//            }
//        }
//
//        for (int i = 0; i < jsonRelatie.getTelefoonnummers().size(); i++) {
//            if (!Hulp.controleerVeld(this.telnummer.get(i), jsonRelatie.getTelefoonnummers().get(i).getTelefoonnummer())) {
//                sb.append("|").append(Hulp.getText(this.telnummer.get(i))).append(",").append(jsonRelatie.getTelefoonnummers().get(i).getTelefoonnummer()).append(";").append("telnummer" + i);
//            }
//            if (!Hulp.controleerVeld(this.soorttelnummer.get(i), jsonRelatie.getTelefoonnummers().get(i).getSoort().toUpperCase())) {
//                sb.append("|").append(Hulp.getText(this.soorttelnummer.get(i))).append(",").append(jsonRelatie.getTelefoonnummers().get(i).getSoort().toUpperCase()).append(";")
//                        .append("soorttelnummer" + i);
//            }
//        }
//
//        return sb.toString();
//    }
//
//    public void drukOpOpslaan() {
//        Hulp.wachtFf();
//        Hulp.klikEnWacht(opslaanrelatie);
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//        Hulp.wachtFf();
//    }
//
//    public void drukOpVerwijderen() {
//        Hulp.klikEnWacht(verwijderen);
//    }
//
//    public void verwijderRekeningnummer(int index) {
//        Hulp.wachtFf();
//        Hulp.klikEnWacht(verwijderRekening.get(index - 1));
//        Hulp.wachtFf();
//    }
//
//    public void verwijderTelefoonnummer(int index) {
//        Hulp.wachtFf();
//        Hulp.klikEnWacht(verwijderTelefoonNummer.get(index - 1));
//        Hulp.wachtFf();
//    }
//
//}
