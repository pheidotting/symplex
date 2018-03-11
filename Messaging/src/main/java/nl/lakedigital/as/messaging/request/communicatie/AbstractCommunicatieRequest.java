package nl.lakedigital.as.messaging.request.communicatie;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class AbstractCommunicatieRequest extends AbstractMessage {
    private Afzender afzender;
    private Geadresseerde geadresseerde;

    public AbstractCommunicatieRequest() {
    }

    public AbstractCommunicatieRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String kantoorNaam, String kantoorEmail) {
        this.afzender = new Afzender(kantoorNaam, kantoorEmail);
        this.geadresseerde = new Geadresseerde(gebruikerId, email, voornaam, tussenvoegsel, achternaam);
    }

    public Afzender getAfzender() {
        return afzender;
    }

    public void setAfzender(Afzender afzender) {
        this.afzender = afzender;
    }

    public Geadresseerde getGeadresseerde() {
        return geadresseerde;
    }

    public void setGeadresseerde(Geadresseerde geadresseerde) {
        this.geadresseerde = geadresseerde;
    }
}
