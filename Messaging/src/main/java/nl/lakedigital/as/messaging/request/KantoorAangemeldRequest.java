package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Kantoor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "kantoorAangemeldRequest")
public class KantoorAangemeldRequest extends AbstractMessage {
    private Kantoor kantoor;

    public KantoorAangemeldRequest() {
        this.kantoor = new Kantoor();
    }

    public KantoorAangemeldRequest(Long id, String identificatie, String naam, String emailadres, String straat, Long huisnummer, String toevoeging, String postcode, String plaats) {
        this.kantoor = new Kantoor(id, identificatie, naam, emailadres, straat, huisnummer, toevoeging, postcode, plaats);
    }

    public Kantoor getKantoor() {
        return kantoor;
    }

    public void setKantoor(Kantoor kantoor) {
        this.kantoor = kantoor;
    }
}
