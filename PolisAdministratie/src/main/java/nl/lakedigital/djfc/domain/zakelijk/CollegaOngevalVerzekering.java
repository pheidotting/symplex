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
@DiscriminatorValue(value = "CO")
public class CollegaOngevalVerzekering extends Polis {
    public CollegaOngevalVerzekering() {
    }

    public CollegaOngevalVerzekering(Pakket pakket) {
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
    public Polis nieuweInstantie(Pakket pakket) {
        return new CollegaOngevalVerzekering(pakket);
    }
}
