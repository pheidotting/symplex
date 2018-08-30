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
@DiscriminatorValue(value = "SP")
public class SpaarVerzekering extends Polis {
    public SpaarVerzekering() {
    }

    public SpaarVerzekering(Pakket pakket) {
        super(pakket);
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
    public SpaarVerzekering nieuweInstantie(Pakket pakket) {
        return new SpaarVerzekering(pakket);
    }
}

