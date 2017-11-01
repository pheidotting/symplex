package nl.lakedigital.as.messaging.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Schade {
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
    private List<Opmerking> opmerkingen;
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

    public List<Opmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = new ArrayList<>();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<Opmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }
}
