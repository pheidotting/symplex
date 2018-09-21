package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "verwijderEntiteitenRequest")
public class VerwijderEntiteitenRequest extends AbstractMessage {
    private SoortEntiteit soortEntiteit;
    private Long entiteitId;

    public SoortEntiteit getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(SoortEntiteit soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }
}
