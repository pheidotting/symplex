package nl.lakedigital.djfc.commons.json;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Taak {
    private Long id;
    private String identificatie;
    private String tijdstipCreatie;
    private String deadline;
    private String tijdstipAfgehandeld;
    private String titel;
    private String omschrijving;
    private Long entiteitId;
    private String soortEntiteit;
    private Long toegewezenAan;
    private List<WijzigingTaak> wijzigingTaaks;

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

    public String getTijdstipCreatie() {
        return tijdstipCreatie;
    }

    public void setTijdstipCreatie(String tijdstipCreatie) {
        this.tijdstipCreatie = tijdstipCreatie;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTijdstipAfgehandeld() {
        return tijdstipAfgehandeld;
    }

    public void setTijdstipAfgehandeld(String tijdstipAfgehandeld) {
        this.tijdstipAfgehandeld = tijdstipAfgehandeld;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getToegewezenAan() {
        return toegewezenAan;
    }

    public void setToegewezenAan(Long toegewezenAan) {
        this.toegewezenAan = toegewezenAan;
    }


    public List<WijzigingTaak> getWijzigingTaaks() {
        if (wijzigingTaaks == null) {
            wijzigingTaaks = newArrayList();
        }
        return wijzigingTaaks;
    }

    public void setWijzigingTaaks(List<WijzigingTaak> wijzigingTaaks) {
        this.wijzigingTaaks = wijzigingTaaks;
    }
}
