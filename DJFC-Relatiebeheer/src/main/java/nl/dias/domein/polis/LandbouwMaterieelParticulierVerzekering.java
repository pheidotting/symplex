package nl.dias.domein.polis;

import org.hibernate.envers.Audited;
import org.springframework.stereotype.Component;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Audited

@Component
@Entity
@Table(name = "POLIS")
@DiscriminatorValue(value = "LN")
public class LandbouwMaterieelParticulierVerzekering extends Polis {
    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return "Landbouw Materieel";
    }

    @Override
    public Polis nieuweInstantie() {
        return new LandbouwMaterieelParticulierVerzekering();
    }
}
