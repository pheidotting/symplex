package nl.lakedigital.djfc.domain.response;

import nl.lakedigital.djfc.commons.json.JsonOpmerking;

public class WijzigingTaak {
    private String identificatie;
    private String taakStatus;
    private String toegewezenAan;
    private String tijdstip;
    private JsonOpmerking jsonOpmerking;

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

    public JsonOpmerking getJsonOpmerking() {
        return jsonOpmerking;
    }

    public void setJsonOpmerking(JsonOpmerking jsonOpmerking) {
        this.jsonOpmerking = jsonOpmerking;
    }
}
