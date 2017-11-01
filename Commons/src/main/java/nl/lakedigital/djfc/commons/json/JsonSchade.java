package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class JsonSchade {
    private Long id;
    private String polis;
    private String schadeNummerMaatschappij;
    private String schadeNummerTussenPersoon;
    private String soortSchade;
    private String locatie;
    private String statusSchade;
    private String datumTijdSchade;
    private String datumTijdMelding;
    private String datumAfgehandeld;
    private String eigenRisico;
    private String omschrijving;
    private List<JsonOpmerking> opmerkingen;
    private String relatie;
    private Long bedrijf;
    private String idDiv;
    private String idDivLink;
    private String titel;
    private List<String> errors;
    private String soortEntiteit;
    private String readOnly;
    private String notReadOnly;
    private String identificatie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolis() {
        return polis;
    }

    public void setPolis(String polis) {
        this.polis = polis;
    }

    public String getSchadeNummerMaatschappij() {
        return schadeNummerMaatschappij;
    }

    public void setSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        this.schadeNummerMaatschappij = schadeNummerMaatschappij;
    }

    public String getSchadeNummerTussenPersoon() {
        return schadeNummerTussenPersoon;
    }

    public void setSchadeNummerTussenPersoon(String schadeNummerTussenPersoon) {
        this.schadeNummerTussenPersoon = schadeNummerTussenPersoon;
    }

    public String getSoortSchade() {
        return soortSchade;
    }

    public void setSoortSchade(String soortSchade) {
        this.soortSchade = soortSchade;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getStatusSchade() {
        return statusSchade;
    }

    public void setStatusSchade(String statusSchade) {
        this.statusSchade = statusSchade;
    }

    public String getDatumTijdSchade() {
        return datumTijdSchade;
    }

    public void setDatumTijdSchade(String datumTijdSchade) {
        this.datumTijdSchade = datumTijdSchade;
    }

    public String getDatumTijdMelding() {
        return datumTijdMelding;
    }

    public void setDatumTijdMelding(String datumTijdMelding) {
        this.datumTijdMelding = datumTijdMelding;
    }

    public String getDatumAfgehandeld() {
        return datumAfgehandeld;
    }

    public void setDatumAfgehandeld(String datumAfgehandeld) {
        this.datumAfgehandeld = datumAfgehandeld;
    }

    public String getEigenRisico() {
        return eigenRisico;
    }

    public void setEigenRisico(String eigenRisico) {
        this.eigenRisico = eigenRisico;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<JsonOpmerking>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
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

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getRelatie() {
        return relatie;
    }

    public void setRelatie(String relatie) {
        this.relatie = relatie;
    }

    public Long getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Long bedrijf) {
        this.bedrijf = bedrijf;
    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
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

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JsonSchade)) {
            return false;
        }
        JsonSchade rhs = (JsonSchade) object;
        return new EqualsBuilder().append(this.soortSchade, rhs.soortSchade).append(this.datumTijdMelding, rhs.datumTijdMelding).append(this.locatie, rhs.locatie)
                .append(this.schadeNummerMaatschappij, rhs.schadeNummerMaatschappij).append(this.datumTijdSchade, rhs.datumTijdSchade).append(this.statusSchade, rhs.statusSchade)
                .append(this.id, rhs.id).append(this.schadeNummerTussenPersoon, rhs.schadeNummerTussenPersoon).append(this.eigenRisico, rhs.eigenRisico).append(this.omschrijving, rhs.omschrijving)
                .append(this.datumAfgehandeld, rhs.datumAfgehandeld).isEquals();
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.soortSchade).append(this.datumTijdMelding).append(this.locatie).append(this.schadeNummerMaatschappij).append(this.datumTijdSchade)
                .append(this.statusSchade).append(this.id).append(this.schadeNummerTussenPersoon).append(this.eigenRisico).append(this.omschrijving).append(this.datumAfgehandeld).toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JsonSchade \n[id=");
        builder.append(id);
        builder.append(", schadeNummerMaatschappij=");
        builder.append(schadeNummerMaatschappij);
        builder.append(", schadeNummerTussenPersoon=");
        builder.append(schadeNummerTussenPersoon);
        builder.append(", soortSchade=");
        builder.append(soortSchade);
        builder.append(", locatie=");
        builder.append(locatie);
        builder.append(", statusSchade=");
        builder.append(statusSchade);
        builder.append(", datumTijdSchade=");
        builder.append(datumTijdSchade);
        builder.append(", datumTijdMelding=");
        builder.append(datumTijdMelding);
        builder.append(", datumAfgehandeld=");
        builder.append(datumAfgehandeld);
        builder.append(", eigenRisico=");
        builder.append(eigenRisico);
        builder.append(", omschrijving=");
        builder.append(omschrijving);
        builder.append(", opmerkingen=");
        builder.append(opmerkingen);
        builder.append("]");
        return builder.toString();
    }
}
