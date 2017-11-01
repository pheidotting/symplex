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
@DiscriminatorValue(value = "AA")
public class AanhangerParticulierVerzekering extends Polis {

    @Override
    public SoortVerzekering getSoortVerzekering() {
        return SoortVerzekering.PARTICULIER;
    }

    @Override
    public String getSchermNaam() {
        return "AanhangerParticulier";
    }

    @Override
    public AanhangerParticulierVerzekering nieuweInstantie() {
        return new AanhangerParticulierVerzekering();
    }
}
