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
@DiscriminatorValue(value = "LN")
public class LandbouwMaterieelParticulierVerzekering extends Polis {
    public LandbouwMaterieelParticulierVerzekering() {
    }

    public LandbouwMaterieelParticulierVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return "Landbouw Materieel";
    }

    @Override
    public Polis nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new LandbouwMaterieelParticulierVerzekering(soortEntiteit, entiteitId);
    }
}
