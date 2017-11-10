package nl.lakedigital.as.messaging.response;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.Schade;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "schadeOpslaanResponse")
public class SchadeOpslaanResponse extends AbstractMessage {
    List<Schade> schades;

    public List<Schade> getSchades() {
        if (schades == null) {
            schades = new ArrayList<>();
        }
        return schades;
    }

    public void setSchades(List<Schade> schades) {
        this.schades = schades;
    }

}
