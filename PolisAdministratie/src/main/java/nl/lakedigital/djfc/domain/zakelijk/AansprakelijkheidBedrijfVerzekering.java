package nl.lakedigital.djfc.domain.zakelijk;

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
@DiscriminatorValue(value = "AB")
public class AansprakelijkheidBedrijfVerzekering extends Polis {
    public AansprakelijkheidBedrijfVerzekering() {//Hibernate wil deze, maar SonarQube niet
    }

    public AansprakelijkheidBedrijfVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return "AansprakelijkheidBedrijf";
    }

    @Override
    public AansprakelijkheidBedrijfVerzekering nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new AansprakelijkheidBedrijfVerzekering(soortEntiteit,entiteitId);
    }
}
