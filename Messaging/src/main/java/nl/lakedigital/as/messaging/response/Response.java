package nl.lakedigital.as.messaging.response;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "response")
public class Response extends AbstractMessage {
    private List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds;

    public Response() {
    }

    public Response(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        getSoortEntiteitEnEntiteitIds().add(soortEntiteitEnEntiteitId);
    }

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
