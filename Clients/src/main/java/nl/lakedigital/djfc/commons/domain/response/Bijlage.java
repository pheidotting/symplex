package nl.lakedigital.djfc.commons.domain.response;

public class Bijlage {
    private String identificatie;
    private String bestandsNaam;
    private String url;
    private String tonen;
    private String omschrijving;
    private String datumUpload;
    private Long groepBijlages;

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
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

    public Long getGroepBijlages() {
        return groepBijlages;
    }

    public void setGroepBijlages(Long groepBijlages) {
        this.groepBijlages = groepBijlages;
    }
}
