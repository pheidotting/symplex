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
@DiscriminatorValue(value = "BS")
public class BromSnorfietsVerzekering extends Polis {
    public BromSnorfietsVerzekering() {
    }

    public BromSnorfietsVerzekering(nl.lakedigital.djfc.domain.Pakket pakket) {
        super(pakket);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    public String getSchermNaam() {
        return this.getSchermNaamDefault(this.getClass().getCanonicalName());
    }

    @Override
    public BromSnorfietsVerzekering nieuweInstantie(Pakket pakket) {
        return new BromSnorfietsVerzekering(pakket);
    }
}
