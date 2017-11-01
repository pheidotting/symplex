package nl.lakedigital.as.messaging.response;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Polis;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "polisOpslaanResponse")
public class PolisOpslaanResponse extends AbstractMessage {
    private List<Polis> polissen;

    public List<Polis> getPolissen() {
        if (polissen == null) {
            polissen = new ArrayList<>();
        }
        return polissen;
    }

    public void setPolissen(List<Polis> polissen) {
        this.polissen = polissen;
    }
}
