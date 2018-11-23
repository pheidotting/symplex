package nl.lakedigital.djfc.commons.json;

public class WijzigingTaak {
    private Long id;
    private String identificatie;
    private String taakStatus;
    private String toegewezenAan;
    private String tijdstip;

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

    public String getTaakStatus() {
        return taakStatus;
    }

    public void setTaakStatus(String taakStatus) {
        this.taakStatus = taakStatus;
    }

    public String getToegewezenAan() {
        return toegewezenAan;
    }

    public void setToegewezenAan(String toegewezenAan) {
        this.toegewezenAan = toegewezenAan;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }
}
