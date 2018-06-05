package nl.lakedigital.as.messaging.request.taak;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wijzigenTaakRequest")
public class WijzigenTaakRequest extends AbstractMessage {
    private String identificatie;
    private String taakStatus;
    private Long toegewezenAan;

    public WijzigenTaakRequest() {
    }

    public WijzigenTaakRequest(String identificatie, String taakStatus, Long toegewezenAan) {
        this.identificatie = identificatie;
        this.taakStatus = taakStatus;
        this.toegewezenAan = toegewezenAan;
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

    public Long getToegewezenAan() {
        return toegewezenAan;
    }

    public void setToegewezenAan(Long toegewezenAan) {
        this.toegewezenAan = toegewezenAan;
    }
}
