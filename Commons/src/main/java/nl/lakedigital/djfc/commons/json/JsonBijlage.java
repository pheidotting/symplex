package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class JsonBijlage extends AbstracteJsonEntiteitMetSoortEnId implements Comparable<JsonBijlage> {
    private Long id;
    private String bestandsNaam;
    private String url;
    private String tonen;
    private String omschrijving;
    private String datumUpload;
    private String omschrijvingOfBestandsNaam;
    private String omschrijvingOfBestandsNaamBackup;
    private String s3Identificatie;
    private Long groepBijlages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsNaam() {
        return bestandsNaam;
    }

    public void setBestandsNaam(String bestandsNaam) {
        this.bestandsNaam = bestandsNaam;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTonen() {
        return tonen;
    }

    public void setTonen(String tonen) {
        this.tonen = tonen;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getDatumUpload() {
        return datumUpload;
    }

    public void setDatumUpload(String datumUpload) {
        this.datumUpload = datumUpload;
    }

    public String getOmschrijvingOfBestandsNaam() {
        return omschrijvingOfBestandsNaam;
    }

    public void setOmschrijvingOfBestandsNaam(String omschrijvingOfBestandsNaam) {
        this.omschrijvingOfBestandsNaam = omschrijvingOfBestandsNaam;
    }

    public String getOmschrijvingOfBestandsNaamBackup() {
        return omschrijvingOfBestandsNaamBackup;
    }

    public void setOmschrijvingOfBestandsNaamBackup(String omschrijvingOfBestandsNaamBackup) {
        this.omschrijvingOfBestandsNaamBackup = omschrijvingOfBestandsNaamBackup;
    }

    public String getS3Identificatie() {
        return s3Identificatie;
    }

    public void setS3Identificatie(String s3Identificatie) {
        this.s3Identificatie = s3Identificatie;
    }

    public Long getGroepBijlages() {
        return groepBijlages;
    }

    public void setGroepBijlages(Long groepBijlages) {
        this.groepBijlages = groepBijlages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonBijlage)) {
            return false;
        }

        JsonBijlage that = (JsonBijlage) o;

        return new EqualsBuilder().append(getId(), that.getId()).append(getBestandsNaam(), that.getBestandsNaam()).append(getOmschrijving(), that.getOmschrijving()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getBestandsNaam()).append(getOmschrijving()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("bestandsNaam", bestandsNaam).append("url", url).append("tonen", tonen).append("omschrijving", omschrijving).append("datumUpload", datumUpload).append("omschrijvingOfBestandsNaam", omschrijvingOfBestandsNaam).append("omschrijvingOfBestandsNaamBackup", omschrijvingOfBestandsNaamBackup).toString();
    }

    @Override
    public int compareTo(JsonBijlage o) {
        if (this.getSoortEntiteit() == o.getSoortEntiteit()) {
            return this.bestandsNaam.compareTo(o.bestandsNaam);
        } else {
            return 0;
        }
    }
}
