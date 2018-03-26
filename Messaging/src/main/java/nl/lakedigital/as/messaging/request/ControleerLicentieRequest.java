package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "controleerLicentieRequest")
public class ControleerLicentieRequest extends AbstractMessage {
    private Long kantoorId;

    public ControleerLicentieRequest() {
    }

    public ControleerLicentieRequest(Long kantoorId) {
        this.kantoorId = kantoorId;
    }

    public Long getKantoorId() {
        return kantoorId;
    }

    public void setKantoorId(Long kantoorId) {
        this.kantoorId = kantoorId;
    }
}
