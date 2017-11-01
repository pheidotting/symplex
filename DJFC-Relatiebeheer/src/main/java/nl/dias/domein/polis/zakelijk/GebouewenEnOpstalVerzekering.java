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
@DiscriminatorValue(value = "GO")
public class GebouewenEnOpstalVerzekering extends Polis {
    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.ZAKELIJK;
    }

    @Override
    public String getSchermNaam() {
        return this.getSchermNaamDefault(this.getClass().getCanonicalName());
    }

    @Override
    public Polis nieuweInstantie() {
        return new GebouewenEnOpstalVerzekering();
    }
}
