package nl.lakedigital.as.messaging.request.licentie;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "licentieGekochtResponse")
public class LicentieGekochtResponse extends AbstractMessage {
    private String licentieType;
    private Double prijs;
    private Long kantoor;

    public String getLicentieType() {
        return licentieType;
    }

    public void setLicentieType(String licentieType) {
        this.licentieType = licentieType;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }

    public Long getKantoor() {
        return kantoor;
    }

    public void setKantoor(Long kantoor) {
        this.kantoor = kantoor;
    }
}
