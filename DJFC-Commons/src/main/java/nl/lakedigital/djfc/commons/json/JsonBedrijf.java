package nl.lakedigital.djfc.commons.json;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class JsonBedrijf {

    private String id;
    private List<JsonPolis> polissen;
    private List<JsonOpmerking> opmerkingen;
    private String naam;
    private String kvk;
    private String rechtsvorm;
    private String email;
    private String internetadres;
    private String hoedanigheid;
    private String cAoVerplichtingen;
    private List<JsonContactPersoon> contactpersonen;
    private List<JsonTelefoonnummer> telefoonnummers;
    private String idDiv;
    private String idDivLink;
    private List<String> errors;
    private JsonOpmerkingenModel opmerkingenModel;
    private String adresOpgemaakt;
    private List<JsonAdres> adressen;
    private List<JsonBijlage> bijlages;
    private AdressenModel adressenModel;
    private TelefoonnummersModel telefoonnummersModel;
    private String soortEntiteit;
    private boolean readOnly;
    private boolean notReadOnly;
    private String identificatie;

    public class AdressenModel {
        private List<JsonAdres> adressen;
        private String bedrijf;

        public List<JsonAdres> getAdressen() {
            return adressen;
        }

        public void setAdressen(List<JsonAdres> adressen) {
            this.adressen = adressen;
        }

        public String getBedrijf() {
            return bedrijf;
        }

        public void setBedrijf(String bedrijf) {
            this.bedrijf = bedrijf;
        }
    }

    public class TelefoonnummersModel {
        private List<JsonTelefoonnummer> telefoonnummers;
        private String bedrijf;

        public List<JsonTelefoonnummer> getTelefoonnummers() {
            return telefoonnummers;
        }

        public void setTelefoonnummers(List<JsonTelefoonnummer> telefoonnummers) {
            this.telefoonnummers = telefoonnummers;
        }

        public String getBedrijf() {
            return bedrijf;
        }

        public void setBedrijf(String bedrijf) {
            this.bedrijf = bedrijf;
        }
    }

    public String getAdresOpgemaakt() {
        StringBuilder sb = new StringBuilder();
        if (!getAdressen().isEmpty()) {
            JsonAdres adres = adressen.get(0);

            if (adres.getStraat() != null) {
                sb.append(adres.getStraat());
                sb.append(" ");
            }
            if (adres.getHuisnummer() != null) {
                sb.append(adres.getHuisnummer());
                sb.append(" ");
            }
            if (adres.getToevoeging() != null) {
                sb.append(adres.getToevoeging());
                sb.append(" ");
            }
            if (adres.getPlaats() != null) {
                sb.append(adres.getPlaats());
            }
        }

        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<JsonPolis> getPolissen() {
        if (polissen == null) {
            polissen = new ArrayList<JsonPolis>();
        }
        return polissen;
    }

    public void setPolissen(List<JsonPolis> polissen) {
        this.polissen = polissen;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getKvk() {
        return kvk;
    }

    public void setKvk(String kvk) {
        this.kvk = kvk;
    }


    public String getIdDiv() {
        return idDiv;
    }

    public void setIdDiv(String idDiv) {
        this.idDiv = idDiv;
    }

    public String getIdDivLink() {
        return idDivLink;
    }

    public void setIdDivLink(String idDivLink) {
        this.idDivLink = idDivLink;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public JsonOpmerkingenModel getOpmerkingenModel() {
        return opmerkingenModel;
    }

    public void setOpmerkingenModel(JsonOpmerkingenModel opmerkingenModel) {
        this.opmerkingenModel = opmerkingenModel;
    }

    public List<JsonAdres> getAdressen() {
        if (adressen == null) {
            adressen = new ArrayList<>();
        }
        return adressen;
    }

    public void setAdressen(List<JsonAdres> adressen) {
        this.adressen = adressen;
    }

    public List<JsonBijlage> getBijlages() {
        if (bijlages == null) {
            bijlages = Lists.newArrayList();
        }
        return bijlages;
    }

    public void setBijlages(List<JsonBijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public String getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInternetadres() {
        return internetadres;
    }

    public void setInternetadres(String internetadres) {
        this.internetadres = internetadres;
    }

    public String getHoedanigheid() {
        return hoedanigheid;
    }

    public void setHoedanigheid(String hoedanigheid) {
        this.hoedanigheid = hoedanigheid;
    }

    public String getcAoVerplichtingen() {
        return cAoVerplichtingen;
    }

    public void setcAoVerplichtingen(String cAoVerplichtingen) {
        this.cAoVerplichtingen = cAoVerplichtingen;
    }

    public List<JsonContactPersoon> getContactpersonen() {
        if (contactpersonen == null) {
            contactpersonen = new ArrayList<>();
        }
        return contactpersonen;
    }

    public void setContactpersonen(List<JsonContactPersoon> contactpersonen) {
        this.contactpersonen = contactpersonen;
    }

    public List<JsonTelefoonnummer> getTelefoonnummers() {
        if (telefoonnummers == null) {
            telefoonnummers = Lists.newArrayList();
        }
        return telefoonnummers;
    }

    public void setTelefoonnummers(List<JsonTelefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }

    public void setAdresOpgemaakt(String adresOpgemaakt) {
        this.adresOpgemaakt = adresOpgemaakt;
    }

    public AdressenModel getAdressenModel() {
        return adressenModel;
    }

    public void setAdressenModel(AdressenModel adressenModel) {
        this.adressenModel = adressenModel;
    }

    public TelefoonnummersModel getTelefoonnummersModel() {
        return telefoonnummersModel;
    }

    public void setTelefoonnummersModel(TelefoonnummersModel telefoonnummersModel) {
        this.telefoonnummersModel = telefoonnummersModel;
    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isNotReadOnly() {
        return notReadOnly;
    }

    public void setNotReadOnly(boolean notReadOnly) {
        this.notReadOnly = notReadOnly;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonBedrijf)) {
            return false;
        }

        JsonBedrijf that = (JsonBedrijf) o;

        return new EqualsBuilder().append(isReadOnly(), that.isReadOnly()).append(isNotReadOnly(), that.isNotReadOnly()).append(getId(), that.getId()).append(getNaam(), that.getNaam()).append(getKvk(), that.getKvk()).append(getRechtsvorm(), that.getRechtsvorm()).append(getEmail(), that.getEmail()).append(getInternetadres(), that.getInternetadres()).append(getHoedanigheid(), that.getHoedanigheid()).append(getcAoVerplichtingen(), that.getcAoVerplichtingen()).append(getTelefoonnummers(), that.getTelefoonnummers()).append(getIdDiv(), that.getIdDiv()).append(getIdDivLink(), that.getIdDivLink()).append(getSoortEntiteit(), that.getSoortEntiteit()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getNaam()).append(getKvk()).append(getRechtsvorm()).append(getEmail()).append(getInternetadres()).append(getHoedanigheid()).append(getcAoVerplichtingen()).append(getTelefoonnummers()).append(getIdDiv()).append(getIdDivLink()).append(getSoortEntiteit()).append(isReadOnly()).append(isNotReadOnly()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("isReadOnly()", this.isReadOnly()).append("isNotReadOnly()", isNotReadOnly()).append("getId()", getId()).append("getNaam()", getNaam()).append("getKvk()", getKvk()).append("getRechtsvorm()", getRechtsvorm()).append("getEmail()", getEmail()).append("getInternetadres()", getInternetadres()).append("getHoedanigheid()", getHoedanigheid()).append("getcAoVerplichtingen()", getcAoVerplichtingen()).append("getTelefoonnummers()", getTelefoonnummers()).append("getIdDiv()", getIdDiv()).append("getIdDivLink()", getIdDivLink()).append("getSoortEntiteit()", getSoortEntiteit()).toString();
    }
}
