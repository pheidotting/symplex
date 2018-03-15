package nl.lakedigital.as.messaging.request.licentie;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Kantoor;
import nl.lakedigital.as.messaging.domain.Medewerker;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "licentieToegevoegd")
public class LicentieToegevoegdRequest extends AbstractMessage {
    private String licentieType;
    private Kantoor kantoor;
    private Medewerker medwerker;

    public String getLicentieType() {
        return licentieType;
    }

    public void setLicentieType(String licentieType) {
        this.licentieType = licentieType;
    }

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }

    public Medewerker getMedwerker() {
        return medwerker;
    }

    public void setMedwerker(Medewerker medwerker) {
        this.medwerker = medwerker;
    }
}
