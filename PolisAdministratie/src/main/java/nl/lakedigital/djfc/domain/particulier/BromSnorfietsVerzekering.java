package nl.lakedigital.djfc.domain.particulier;

import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

//@Audited

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "BS")
public class BromSnorfietsVerzekering extends Polis {
    public BromSnorfietsVerzekering() {//Hibernate wil deze, maar SonarQube niet
    }

    public BromSnorfietsVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    public String getSchermNaam() {
        return this.getSchermNaamDefault(this.getClass().getCanonicalName());
    }

    @Override
    public BromSnorfietsVerzekering nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new BromSnorfietsVerzekering(soortEntiteit,entiteitId);
    }
}
