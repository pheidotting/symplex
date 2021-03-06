package nl.lakedigital.djfc.commons.domain;

public class SoortEntiteitEnEntiteitId {
    private SoortEntiteit soortEntiteit;
    private Long entiteitId;

    public SoortEntiteitEnEntiteitId() {
    }

    public SoortEntiteitEnEntiteitId(SoortEntiteit soortEntiteit, Long entiteitId) {
        this.soortEntiteit = soortEntiteit;
        this.entiteitId = entiteitId;
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
