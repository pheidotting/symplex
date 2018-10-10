package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.commons.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "opslaanEntiteitenRequest")
public class OpslaanEntiteitenRequest extends AbstractMessage {
    private List<AbstracteEntiteitMetSoortEnId> lijst;
    private SoortEntiteit soortEntiteit;
    private Long entiteitId;

    public List<AbstracteEntiteitMetSoortEnId> getLijst() {
        if (lijst == null) {
            lijst = new ArrayList<>();
        }
        return lijst;
    }

    public void setLijst(List<AbstracteEntiteitMetSoortEnId> lijst) {
        this.lijst = lijst;
    }

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
