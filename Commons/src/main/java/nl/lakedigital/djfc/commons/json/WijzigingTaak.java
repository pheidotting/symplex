package nl.lakedigital.djfc.commons.json;

public class WijzigingTaak {
    private Long id;
    private String taakStatus;
    private Long toegewezenAan;
    private String tijdstip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaakStatus() {
        return taakStatus;
    }

    public void setTaakStatus(String taakStatus) {
        this.taakStatus = taakStatus;
    }

    public Long getToegewezenAan() {
        return toegewezenAan;
    }

    public void setToegewezenAan(Long toegewezenAan) {
        this.toegewezenAan = toegewezenAan;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }
}
