package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "verwijderEntiteitRequest")
public class VerwijderEntiteitRequest extends AbstractMessage {
    private SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId;

    public VerwijderEntiteitRequest() {
    }

    public VerwijderEntiteitRequest(SoortEntiteit soortEntiteit, Long id) {
        this.soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
        this.soortEntiteitEnEntiteitId.setEntiteitId(id);
        this.soortEntiteitEnEntiteitId.setSoortEntiteit(soortEntiteit);
    }

    public SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId() {
        return soortEntiteitEnEntiteitId;
    }

    public void setSoortEntiteitEnEntiteitId(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        this.soortEntiteitEnEntiteitId = soortEntiteitEnEntiteitId;
    }
}
