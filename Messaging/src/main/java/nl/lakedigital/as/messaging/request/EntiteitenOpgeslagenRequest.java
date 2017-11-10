package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "entiteitenOpgeslagenRequest")
public class EntiteitenOpgeslagenRequest extends AbstractMessage {
    private List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds;

    public List<SoortEntiteitEnEntiteitId> getSoortEntiteitEnEntiteitIds() {
        if (soortEntiteitEnEntiteitIds == null) {
            soortEntiteitEnEntiteitIds = new ArrayList<>();
        }

        return soortEntiteitEnEntiteitIds;
    }

    public void setSoortEntiteitEnEntiteitIds(List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds) {
        this.soortEntiteitEnEntiteitIds = soortEntiteitEnEntiteitIds;
    }
}
