package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "opvragenPersoonRequest")
public class OpvragenPersoonSOfBedrijfsGegevensRequest extends AbstractMessage {
    private Long entiteitId;
    private String soortEntiteit;

    public Long getEntiteitId() {
        return entiteitId;
    }

    public void setEntiteitId(Long entiteitId) {
        this.entiteitId = entiteitId;
    }

    public String getSoortEntiteit() {
        return soortEntiteit;
    }

    public void setSoortEntiteit(String soortEntiteit) {
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("entiteitId", entiteitId).append("soortEntiteit", soortEntiteit).toString();
    }
}
