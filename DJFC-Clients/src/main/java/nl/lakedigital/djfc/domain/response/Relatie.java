package nl.lakedigital.djfc.domain.response;

import java.util.ArrayList;
import java.util.List;

public class Relatie {
    private String identificatie;
    private String roepnaam;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String bsn;
    private Long kantoor;
    private String geboorteDatum;
    private String overlijdensdatum;
    private String geslacht;
    private String burgerlijkeStaat;
    private String emailadres;

    private List<Adres> adressen;
    private List<Bijlage> bijlages;
    private List<GroepBijlages> groepBijlages;
    private List<RekeningNummer> rekeningNummers;
    private List<Telefoonnummer> telefoonnummers;
    private List<Opmerking> opmerkingen;
    private List<TelefoonnummerMetGesprekken> telefoonnummerMetGesprekkens;
    private List<Polis> polissen;

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getRoepnaam() {
        return roepnaam;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    public Long getKantoor() {
        return kantoor;
    }

    public void setKantoor(Long kantoor) {
        this.kantoor = kantoor;
    }

    public String getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(String geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public String getOverlijdensdatum() {
        return overlijdensdatum;
    }

    public void setOverlijdensdatum(String overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(String geslacht) {
        this.geslacht = geslacht;
    }

    public String getBurgerlijkeStaat() {
        return burgerlijkeStaat;
    }

    public void setBurgerlijkeStaat(String burgerlijkeStaat) {
        this.burgerlijkeStaat = burgerlijkeStaat;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public List<Adres> getAdressen() {
        if (adressen == null) {
            adressen = new ArrayList<>();
        }
        return adressen;
    }

    public void setAdressen(List<Adres> adressen) {
        this.adressen = adressen;
    }

    public List<Bijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = new ArrayList<>();
        }
        return bijlages;
    }

    public void setBijlages(List<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public List<RekeningNummer> getRekeningNummers() {
        if (rekeningNummers == null) {
            rekeningNummers = new ArrayList<>();
        }
        return rekeningNummers;
    }

    public List<GroepBijlages> getGroepBijlages() {
        if (groepBijlages == null) {
            groepBijlages = new ArrayList<>();
        }
        return groepBijlages;
    }

    public void setGroepBijlages(List<GroepBijlages> groepBijlages) {
        this.groepBijlages = groepBijlages;
    }

    public void setRekeningNummers(List<RekeningNummer> rekeningNummers) {
        this.rekeningNummers = rekeningNummers;
    }

    public List<Telefoonnummer> getTelefoonnummers() {
        if (telefoonnummers == null) {
            telefoonnummers = new ArrayList<>();
        }
        return telefoonnummers;
    }

    public void setTelefoonnummers(List<Telefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }

    public List<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public List<TelefoonnummerMetGesprekken> getTelefoonnummerMetGesprekkens() {
        if (telefoonnummerMetGesprekkens == null) {
            telefoonnummerMetGesprekkens = new ArrayList<>();
        }
        return telefoonnummerMetGesprekkens;
    }

    public void setTelefoonnummerMetGesprekkens(List<TelefoonnummerMetGesprekken> telefoonnummerMetGesprekkens) {
        this.telefoonnummerMetGesprekkens = telefoonnummerMetGesprekkens;
    }

    public List<Polis> getPolissen() {
        if (polissen == null) {
            polissen = new ArrayList<>();
        }
        return polissen;
    }

    public void setPolissen(List<Polis> polissen) {
        this.polissen = polissen;
    }
}
