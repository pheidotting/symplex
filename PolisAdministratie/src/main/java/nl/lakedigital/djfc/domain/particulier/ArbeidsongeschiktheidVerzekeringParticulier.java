package nl.lakedigital.djfc.domain.particulier;

import nl.lakedigital.djfc.domain.Pakket;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import org.hibernate.envers.Audited;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Audited

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "AG")
public class ArbeidsongeschiktheidVerzekeringParticulier extends Polis {
    public ArbeidsongeschiktheidVerzekeringParticulier() {
    }

    public ArbeidsongeschiktheidVerzekeringParticulier(nl.lakedigital.djfc.domain.Pakket pakket) {
        super(pakket);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        return this.getSchermNaamDefault(this.getClass().getCanonicalName());
    }

    @Override
    public Polis nieuweInstantie(Pakket pakket) {
        return new ArbeidsongeschiktheidVerzekeringParticulier(pakket);
    }
}
