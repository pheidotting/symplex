package nl.lakedigital.as.messaging.request.relatie;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.entities.Relatie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "opslaanRelatieRequest")
public class OpslaanRelatieRequest extends AbstractMessage {
    private Relatie relatie;

    public Relatie getRelatie() {
        return relatie;
    }

    public void setRelatie(Relatie relatie) {
        this.relatie = relatie;
    }
}
