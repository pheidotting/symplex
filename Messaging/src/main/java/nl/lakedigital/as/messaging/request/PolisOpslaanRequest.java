package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Pakket;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "polisOpslaanRequest")
public class PolisOpslaanRequest extends AbstractMessage {
    private List<Pakket> pakketten;

    public List<Pakket> getPokketten() {
        if (pakketten == null) {
            pakketten = new ArrayList<>();
        }
        return pakketten;
    }

    public void setPokketten(List<Pakket> pakketten) {
        this.pakketten = pakketten;
    }
}
