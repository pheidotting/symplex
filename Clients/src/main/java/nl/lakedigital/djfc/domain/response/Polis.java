package nl.lakedigital.djfc.domain.response;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Polis {
    private String identificatie;
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
    private String soortEntiteit;
    private Long entiteitId;
    private String parentIdentificatie;

    private List<Schade> schades;

    private List<Bijlage> bijlages = newArrayList();
    private List<GroepBijlages> groepBijlages = newArrayList();
    private List<Opmerking> opmerkingen = newArrayList();

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
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

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
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

    public List<Schade> getSchades() {
        if (schades == null) {
            schades = new ArrayList<>();
        }
        return schades;
    }

    public void setSchades(List<Schade> schades) {
        this.schades = schades;
    }

    public List<Bijlage> getBijlages() {
        return bijlages;
    }

    public void setBijlages(List<Bijlage> bijlages) {
        this.bijlages = bijlages;
    }

    public List<GroepBijlages> getGroepBijlages() {
        return groepBijlages;
    }

    public void setGroepBijlages(List<GroepBijlages> groepBijlages) {
        this.groepBijlages = groepBijlages;
    }

    public List<Opmerking> getOpmerkingen() {
        return opmerkingen;
    }

    public void setOpmerkingen(List<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}