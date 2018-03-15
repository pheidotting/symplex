package nl.lakedigital.as.messaging.request.communicatie;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public abstract class AbstractCommunicatieRequest extends AbstractMessage {
    private Afzender afzender;
    private List<Geadresseerde> geadresseerden;

    public AbstractCommunicatieRequest() {
        this.geadresseerden = new ArrayList<>();
    }

    public AbstractCommunicatieRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String kantoorNaam, String kantoorEmail) {
        if (kantoorNaam != null && kantoorEmail != null) {
            this.afzender = new Afzender(kantoorNaam, kantoorEmail);
        }
        this.geadresseerden = new ArrayList<>();
        this.geadresseerden.add(new Geadresseerde(gebruikerId, email, voornaam, tussenvoegsel, achternaam));
    }

    public Afzender getAfzender() {
        return afzender;
    }

    public void setAfzender(Afzender afzender) {
        this.afzender = afzender;
    }

    public List<Geadresseerde> getGeadresseerden() {
        return geadresseerden;
    }

    public void setGeadresseerden(List<Geadresseerde> geadresseerden) {
        this.geadresseerden = geadresseerden;
    }

    public void addGeadresseerde(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam) {
        this.geadresseerden.add(new Geadresseerde(gebruikerId, email, voornaam, tussenvoegsel, achternaam));
    }
}
