package nl.lakedigital.as.messaging.response;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Pakket;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "polisOpslaanResponse")
public class PolisOpslaanResponse extends AbstractMessage {
    private List<Pakket> pakketten;

    public List<Pakket> getPakketten() {
        if (pakketten == null) {
            pakketten = new ArrayList<>();
        }
        return pakketten;
    }

    public void setPakketten(List<Pakket> pakketten) {
        this.pakketten = pakketten;
    }
}
