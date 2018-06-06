package nl.lakedigital.as.messaging.request.taak;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import org.joda.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "opslaanTaakRequest")
public class OpslaanTaakRequest extends AbstractMessage {
    private String identificatie;
    private LocalDateTime deadline;
    private String titel;
    private String omschrijving;
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;
    private Long toegewezenAan;
    private String status;

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
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

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getToegewezenAan() {
        return toegewezenAan;
    }

    public void setToegewezenAan(Long toegewezenAan) {
        this.toegewezenAan = toegewezenAan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}