package nl.dias.messaging;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;

public class SoortEntiteitEnEntiteitId {
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
