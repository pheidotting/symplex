package nl.lakedigital.djfc.domain;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Audited
public abstract class AbstracteEntiteitMetSoortEnId {
    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "SOORTENTITEIT")
    private SoortEntiteit soortEntiteit;
    @Column(name = "ENTITEITID")
    private Long entiteitId;

    public abstract Long getId();

    public abstract void setId(Long id);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstracteEntiteitMetSoortEnId)) {
            return false;
        }
        AbstracteEntiteitMetSoortEnId that = (AbstracteEntiteitMetSoortEnId) o;
        return getSoortEntiteit() == that.getSoortEntiteit() && Objects.equals(getEntiteitId(), that.getEntiteitId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSoortEntiteit(), getEntiteitId());
    }
}
