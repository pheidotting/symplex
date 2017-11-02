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
@DiscriminatorValue(value = "AA")
public class AanhangerParticulierVerzekering extends Polis {
    public AanhangerParticulierVerzekering() {
    }

    public AanhangerParticulierVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        return "AanhangerParticulier";
    }

    @Override
    public AanhangerParticulierVerzekering nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new AanhangerParticulierVerzekering(soortEntiteit, entiteitId);
    }
}
