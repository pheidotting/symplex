package nl.lakedigital.as.messaging.request.taak;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wijzigingTaakOpslaanRequest")
public class WijzigingTaakOpslaanRequest extends AbstractMessage {
    private String taakStatus;
    private Long toegewezenAan;
    private String opmerking;
    private Long taak;

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

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public Long getTaak() {
        return taak;
    }

    public void setTaak(Long taak) {
        this.taak = taak;
    }
}
