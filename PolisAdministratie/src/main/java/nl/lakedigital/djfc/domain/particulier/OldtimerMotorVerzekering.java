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
@DiscriminatorValue(value = "OM")
public class OldtimerMotorVerzekering extends Polis {
    public OldtimerMotorVerzekering() {
    }

    public OldtimerMotorVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        String pakket = this.getClass().getPackage().toString().replace("package ", "") + ".";
        return this.getClass().getCanonicalName().replace("Verzekering", "").replace(pakket, "");
    }

    @Override
    public OldtimerMotorVerzekering nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new OldtimerMotorVerzekering(soortEntiteit,entiteitId);
    }
}
