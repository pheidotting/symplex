package nl.lakedigital.djfc.domain.zakelijk;

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
@DiscriminatorValue(value = "IM")
public class InstallatieMontageVerzekering extends Polis {
    public InstallatieMontageVerzekering() {
    }

    public InstallatieMontageVerzekering(SoortEntiteit soortEntiteit, Long entiteitId) {
        super(soortEntiteit, entiteitId);
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
    public Polis nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
        return new InstallatieMontageVerzekering(soortEntiteit,entiteitId);
    }
}
