package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class JsonRelatie {
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
    private List<String> errors;
    private String soortEntiteit;
    private JsonOpmerkingenModel opmerkingenModel;
    private List<String> lijst;
    private String readOnly;
    private String notReadOnly;
//    private List<JsonTaak> openstaandeTaken;
private String emailadres;

    public String getRoepnaam() {
        return roepnaam;
    }

    public void setRoepnaam(String roepnaam) {
        this.roepnaam = roepnaam;
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

    //    public List<JsonAdres> getAdressen() {
    //        if (adressen == null) {
    //            adressen = new ArrayList<>();
    //        }
    //        return adressen;
    //    }
    //
    //    public void setAdressen(List<JsonAdres> adressen) {
    //        this.adressen = adressen;
    //    }
    //
    //    public String getAdresOpgemaakt() {
    //        JsonAdres adres = (JsonAdres) getFirst(filter(getAdressen(), new JsonWoonAdresPredicate()), null);
    //
    //        if (adresOpgemaakt == null && adres != null) {
    //            StringBuilder sb = new StringBuilder();
    //            if (adres.getStraat() != null) {
    //                sb.append(adres.getStraat() + " ");
    //            }
    //            if (adres.getHuisnummer() != null) {
    //                sb.append(adres.getHuisnummer() + " ");
    //            }
    //            if (adres.getToevoeging() != null && !"".equals(adres.getToevoeging())) {
    //                sb.append(adres.getToevoeging() + " ");
    //            }
    //            if (adres.getPlaats() != null) {
    //                sb.append(", " + adres.getPlaats());
    //            }
    //            adresOpgemaakt = sb.toString();
    //
    //        }
    //        return adresOpgemaakt;
    //    }
    //
    //    public void setAdresOpgemaakt(String adresOpgemaakt) {
    //        this.adresOpgemaakt = adresOpgemaakt;
    //    }

    //    public List<JsonTelefoonnummer> getTelefoonnummers() {
    //        if (telefoonnummers == null) {
    //            telefoonnummers = new ArrayList<JsonTelefoonnummer>();
    //        }
    //        return telefoonnummers;
    //    }
    //
    //    public void setTelefoonnummers(List<JsonTelefoonnummer> telefoonnummers) {
    //        this.telefoonnummers = telefoonnummers;
    //    }
    //
    public String getBsn() {
        return bsn;
    }

    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    //    public List<JsonRekeningNummer> getRekeningnummers() {
    //        if (rekeningnummers == null) {
    //            rekeningnummers = new ArrayList<JsonRekeningNummer>();
    //        }
    //        return rekeningnummers;
    //    }
    //
    //    public void setRekeningnummers(List<JsonRekeningNummer> rekeningnummers) {
    //        this.rekeningnummers = rekeningnummers;
    //    }

    public Long getKantoor() {
        return kantoor;
    }

    public void setKantoor(Long kantoor) {
        this.kantoor = kantoor;
    }

    //
    //    public List<JsonOpmerking> getOpmerkingen() {
    //        return opmerkingen;
    //    }
    //
    //    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
    //        this.opmerkingen = opmerkingen;
    //    }
    //
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

    //    public List<JsonOnderlingeRelatie> getOnderlingeRelaties() {
    //        if (onderlingeRelaties == null) {
    //            onderlingeRelaties = new ArrayList<>();
    //        }
    //        return onderlingeRelaties;
    //    }
    //
    //    public void setOnderlingeRelaties(List<JsonOnderlingeRelatie> onderlingeRelaties) {
    //        this.onderlingeRelaties = onderlingeRelaties;
    //    }
    //
    //    public boolean isZakelijkeKlant() {
    //        return zakelijkeKlant;
    //    }
    //
    //    public void setZakelijkeKlant(boolean zakelijkeKlant) {
    //        this.zakelijkeKlant = zakelijkeKlant;
    //    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    //    public List<JsonBijlage> getBijlages() {
    //        return bijlages;
    //    }
    //
    //    public void setBijlages(List<JsonBijlage> bijlages) {
    //        this.bijlages = bijlages;
    //    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public JsonOpmerkingenModel getOpmerkingenModel() {
        return opmerkingenModel;
    }

    public void setOpmerkingenModel(JsonOpmerkingenModel opmerkingenModel) {
        this.opmerkingenModel = opmerkingenModel;
    }

    public List<String> getLijst() {
        return lijst;
    }

    public void setLijst(List<String> lijst) {
        this.lijst = lijst;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getNotReadOnly() {
        return notReadOnly;
    }

    public void setNotReadOnly(String notReadOnly) {
        this.notReadOnly = notReadOnly;
    }

//    public List<JsonTaak> getOpenstaandeTaken() {
//        return openstaandeTaken;
//    }
//
//    public void setOpenstaandeTaken(List<JsonTaak> openstaandeTaken) {
//        this.openstaandeTaken = openstaandeTaken;
//    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonRelatie)) {
            return false;
        }

        JsonRelatie that = (JsonRelatie) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getIdentificatie(), that.getIdentificatie()).append(getRoepnaam(), that.getRoepnaam()).append(getVoornaam(), that.getVoornaam()).append(getTussenvoegsel(), that.getTussenvoegsel()).append(getAchternaam(), that.getAchternaam()).append(getBsn(), that.getBsn()).append(getKantoor(), that.getKantoor()).append(getGeboorteDatum(), that.getGeboorteDatum()).append(getOverlijdensdatum(), that.getOverlijdensdatum()).append(getGeslacht(), that.getGeslacht()).append(getBurgerlijkeStaat(), that.getBurgerlijkeStaat()).append(getErrors(), that.getErrors()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getIdentificatie()).append(getRoepnaam()).append(getVoornaam()).append(getTussenvoegsel()).append(getAchternaam()).append(getBsn()).append(getKantoor()).append(getGeboorteDatum()).append(getOverlijdensdatum()).append(getGeslacht()).append(getBurgerlijkeStaat()).append(getErrors()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("identificatie", identificatie).append("roepnaam", roepnaam).append("voornaam", voornaam).append("tussenvoegsel", tussenvoegsel).append("achternaam", achternaam).append("bsn", bsn).append("kantoor", kantoor).append("geboorteDatum", geboorteDatum).append("overlijdensdatum", overlijdensdatum).append("geslacht", geslacht).append("burgerlijkeStaat", burgerlijkeStaat).append("errors", errors).toString();
    }
}
