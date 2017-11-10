package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.AbstracteEntiteitMetSoortEnId;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "opslaanEntiteitenRequest")
public class OpslaanEntiteitenRequest extends AbstractMessage {
    private List<AbstracteEntiteitMetSoortEnId> lijst;

    public List<AbstracteEntiteitMetSoortEnId> getLijst() {
        if (lijst == null) {
            lijst = new ArrayList<>();
        }
        return lijst;
    }

    public void setLijst(List<AbstracteEntiteitMetSoortEnId> lijst) {
        this.lijst = lijst;
    }
}
