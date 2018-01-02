package nl.lakedigital.djfc.domain.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Relatie {
    private Long id;
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
    private List<Hypotheek> hypotheken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Hypotheek> getHypotheken() {
        if (hypotheken == null) {
            hypotheken = new ArrayList<>();
        }
        return hypotheken;
    }

    public void setHypotheken(List<Hypotheek> hypotheken) {
        this.hypotheken = hypotheken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relatie)) {
            return false;
        }
        Relatie relatie = (Relatie) o;
        return Objects.equals(getId(), relatie.getId()) && Objects.equals(getIdentificatie(), relatie.getIdentificatie()) && Objects.equals(getRoepnaam(), relatie.getRoepnaam()) && Objects.equals(getVoornaam(), relatie.getVoornaam()) && Objects.equals(getTussenvoegsel(), relatie.getTussenvoegsel()) && Objects.equals(getAchternaam(), relatie.getAchternaam()) && Objects.equals(getBsn(), relatie.getBsn()) && Objects.equals(getKantoor(), relatie.getKantoor()) && Objects.equals(getGeboorteDatum(), relatie.getGeboorteDatum()) && Objects.equals(getOverlijdensdatum(), relatie.getOverlijdensdatum()) && Objects.equals(getGeslacht(), relatie.getGeslacht()) && Objects.equals(getBurgerlijkeStaat(), relatie.getBurgerlijkeStaat()) && Objects.equals(getEmailadres(), relatie.getEmailadres());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdentificatie(), getRoepnaam(), getVoornaam(), getTussenvoegsel(), getAchternaam(), getBsn(), getKantoor(), getGeboorteDatum(), getOverlijdensdatum(), getGeslacht(), getBurgerlijkeStaat(), getEmailadres());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("identificatie", identificatie).append("roepnaam", roepnaam).append("voornaam", voornaam).append("tussenvoegsel", tussenvoegsel).append("achternaam", achternaam).append("bsn", bsn).append("kantoor", kantoor).append("geboorteDatum", geboorteDatum).append("overlijdensdatum", overlijdensdatum).append("geslacht", geslacht).append("burgerlijkeStaat", burgerlijkeStaat).append("emailadres", emailadres).append("adressen", adressen).append("bijlages", bijlages).append("groepBijlages", groepBijlages).append("rekeningNummers", rekeningNummers).append("telefoonnummers", telefoonnummers).append("opmerkingen", opmerkingen).append("telefoonnummerMetGesprekkens", telefoonnummerMetGesprekkens).append("polissen", polissen).toString();
    }
}
