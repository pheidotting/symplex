package nl.lakedigital.djfc.commons.json;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;

public class JsonPolis {
    private Long id;
    private String status;
    private String polisNummer;
    private String kenmerk;
    private String ingangsDatum;
    private String eindDatum;
    private String premie;
    private String wijzigingsDatum;
    private String prolongatieDatum;
    private String betaalfrequentie;
    private String dekking;
    private String verzekerdeZaak;
    private String maatschappij;
    private String soort;
    private String idDiv;
    private String idDivLink;
    private String className;
    private String titel;
    private String omschrijvingVerzekering;
    private List<String> errors;
    private String soortEntiteit;
    private Long entiteitId;
    private String parentIdentificatie;
    private String readOnly;
    private String notReadOnly;
    private List<JsonOpmerking> opmerkingen;
    private String identificatie;
    private List<JsonSchade> schades;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPolisNummer() {
        return polisNummer;
    }

    public void setPolisNummer(String polisNummer) {
        this.polisNummer = polisNummer;
    }

    public String getKenmerk() {
        return kenmerk;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public String getIngangsDatum() {
        return ingangsDatum;
    }

    public void setIngangsDatum(String ingangsDatum) {
        this.ingangsDatum = ingangsDatum;
    }

    public String getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(String eindDatum) {
        this.eindDatum = eindDatum;
    }

    public String getPremie() {
        return premie;
    }

    public void setPremie(String premie) {
        this.premie = premie;
    }

    public String getWijzigingsDatum() {
        return wijzigingsDatum;
    }

    public void setWijzigingsDatum(String wijzigingsDatum) {
        this.wijzigingsDatum = wijzigingsDatum;
    }

    public String getProlongatieDatum() {
        return prolongatieDatum;
    }

    public void setProlongatieDatum(String prolongatieDatum) {
        this.prolongatieDatum = prolongatieDatum;
    }

    public String getBetaalfrequentie() {
        return betaalfrequentie;
    }

    public void setBetaalfrequentie(String betaalfrequentie) {
        this.betaalfrequentie = betaalfrequentie;
    }

    public String getDekking() {
        return dekking;
    }

    public void setDekking(String dekking) {
        this.dekking = dekking;
    }

    public String getVerzekerdeZaak() {
        return verzekerdeZaak;
    }

    public void setVerzekerdeZaak(String verzekerdeZaak) {
        this.verzekerdeZaak = verzekerdeZaak;
    }

    public String getMaatschappij() {
        return maatschappij;
    }

    public void setMaatschappij(String maatschappij) {
        this.maatschappij = maatschappij;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getOmschrijvingVerzekering() {
        return omschrijvingVerzekering;
    }

    public void setOmschrijvingVerzekering(String omschrijvingVerzekering) {
        this.omschrijvingVerzekering = omschrijvingVerzekering;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
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

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public String getParentIdentificatie() {
        return parentIdentificatie;
    }

    public void setParentIdentificatie(String parentIdentificatie) {
        this.parentIdentificatie = parentIdentificatie;
    }

    public List<JsonOpmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public List<JsonSchade> getSchades() {
        if (schades == null) {
            schades = new ArrayList<>();
        }
        return schades;
    }

    public void setSchades(List<JsonSchade> schades) {
        this.schades = schades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JsonPolis)) {
            return false;
        }

        JsonPolis jsonPolis = (JsonPolis) o;

        return new EqualsBuilder().append(getId(), jsonPolis.getId()).append(getStatus(), jsonPolis.getStatus()).append(getPolisNummer(), jsonPolis.getPolisNummer()).append(getKenmerk(), jsonPolis.getKenmerk()).append(getIngangsDatum(), jsonPolis.getIngangsDatum()).append(getEindDatum(), jsonPolis.getEindDatum()).append(getPremie(), jsonPolis.getPremie()).append(getWijzigingsDatum(), jsonPolis.getWijzigingsDatum()).append(getProlongatieDatum(), jsonPolis.getProlongatieDatum()).append(getBetaalfrequentie(), jsonPolis.getBetaalfrequentie()).append(getDekking(), jsonPolis.getDekking()).append(getVerzekerdeZaak(), jsonPolis.getVerzekerdeZaak()).append(getMaatschappij(), jsonPolis.getMaatschappij()).append(getSoort(), jsonPolis.getSoort()).append(getClassName(), jsonPolis.getClassName()).append(getTitel(), jsonPolis.getTitel()).append(getOmschrijvingVerzekering(), jsonPolis.getOmschrijvingVerzekering()).append(getSoortEntiteit(), jsonPolis.getSoortEntiteit()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getStatus()).append(getPolisNummer()).append(getKenmerk()).append(getIngangsDatum()).append(getEindDatum()).append(getPremie()).append(getWijzigingsDatum()).append(getProlongatieDatum()).append(getBetaalfrequentie()).append(getDekking()).append(getVerzekerdeZaak()).append(getMaatschappij()).append(getSoort()).append(getClassName()).append(getTitel()).append(getOmschrijvingVerzekering()).append(getSoortEntiteit()).toHashCode();
    }
}