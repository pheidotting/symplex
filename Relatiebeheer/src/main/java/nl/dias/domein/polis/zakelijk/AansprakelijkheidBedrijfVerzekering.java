package nl.dias.domein.polis.zakelijk;

import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;
import org.hibernate.envers.Audited;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Audited

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "AB")
public class AansprakelijkheidBedrijfVerzekering extends Polis {

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return "AansprakelijkheidBedrijf";
    }

    @Override
    public AansprakelijkheidBedrijfVerzekering nieuweInstantie() {
        return new AansprakelijkheidBedrijfVerzekering();
    }
}
