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
@DiscriminatorValue(value = "MA")
public class MobieleApparatuurVerzekering extends Polis {
    public MobieleApparatuurVerzekering() {//Hibernate wil deze, maar SonarQube niet
    }

    public MobieleApparatuurVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
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
    public MobieleApparatuurVerzekering nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new MobieleApparatuurVerzekering(soortEntiteit,entiteitId);
    }
}
