package nl.lakedigital.as.messaging.request.licentie;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Kantoor;
import nl.lakedigital.as.messaging.domain.Medewerker;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "licentieVerwijderd")
public class LicentieVerwijderdRequest extends AbstractMessage {
    private Kantoor kantoor;
    private Medewerker medwerker;

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
