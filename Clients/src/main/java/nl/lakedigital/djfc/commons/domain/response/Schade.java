package nl.lakedigital.djfc.commons.domain.response;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Schade {
    private String identificatie;
    private String schadeNummerMaatschappij;
    private String schadeNummerTussenPersoon;
    private String soortSchade;
    private String locatie;
    private String statusSchade;
    private String datumSchade;
    private String datumMelding;
    private String datumAfgehandeld;
    private String eigenRisico;
    private String omschrijving;
    private String parentIdentificatie;
    private String polis;

    private List<Bijlage> bijlages = newArrayList();
    private List<GroepBijlages> groepBijlages = newArrayList();
    private List<Opmerking> opmerkingen = newArrayList();
    private List<Taak> taken = newArrayList();

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
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

    public String getDatumSchade() {
        return datumSchade;
    }

    public void setDatumSchade(String datumSchade) {
        this.datumSchade = datumSchade;
    }

    public String getDatumMelding() {
        return datumMelding;
    }

    public void setDatumMelding(String datumMelding) {
        this.datumMelding = datumMelding;
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

    public String getParentIdentificatie() {
        return parentIdentificatie;
    }

    public void setParentIdentificatie(String parentIdentificatie) {
        this.parentIdentificatie = parentIdentificatie;
    }

    public String getPolis() {
        return polis;
    }

    public void setPolis(String polis) {
        this.polis = polis;
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

    public List<GroepBijlages> getGroepBijlages() {
        if (groepBijlages == null) {
            groepBijlages = new ArrayList<>();
        }
        return groepBijlages;
    }

    public void setGroepBijlages(List<GroepBijlages> groepBijlages) {
        this.groepBijlages = groepBijlages;
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

    public List<Taak> getTaken() {
        return taken;
    }

    public void setTaken(List<Taak> taken) {
        this.taken = taken;
    }
}
