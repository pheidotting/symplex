package nl.lakedigital.djfc.domain.zakelijk;

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
@DiscriminatorValue(value = "AV")
public class AanhangerVerzekering extends Polis {
    public AanhangerVerzekering() {
    }

    public AanhangerVerzekering(Pakket pakket) {
        super(pakket);
    }

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return this.getSchermNaamDefault(this.getClass().getCanonicalName());
    }

    @Override
    public AanhangerVerzekering nieuweInstantie(Pakket pakket) {
        return new AanhangerVerzekering(pakket);
    }
}
