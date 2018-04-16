package nl.lakedigital.as.messaging.request.licentie;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "licentieGekochtRequest")
public class LicentieGekochtRequest extends AbstractMessage {
    private String licentieType;
    private Long kantoor;

    public String getLicentieType() {
        return licentieType;
    }

    public void setLicentieType(String licentieType) {
        this.licentieType = licentieType;
    }

    public Long getKantoor() {
        return kantoor;
    }

    public void setKantoor(Long kantoor) {
        this.kantoor = kantoor;
    }
}
