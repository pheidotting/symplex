package nl.dias.builders;

import nl.dias.domein.Belastingzaken;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;

public class BelastingzakenBuilder {
    private Belastingzaken belastingzaken;

    public BelastingzakenBuilder() {
        this.belastingzaken = new Belastingzaken();
    }

    public BelastingzakenBuilder metRelatie(Long id) {
        belastingzaken.setSoortEntiteit(SoortEntiteit.RELATIE);
        belastingzaken.setEntiteitId(id);

        return this;
    }

    public BelastingzakenBuilder metBedrijf(Long id) {
        belastingzaken.setSoortEntiteit(SoortEntiteit.BEDRIJF);
        belastingzaken.setEntiteitId(id);

        return this;
    }

    public BelastingzakenBuilder metJaar(int jaar) {
        belastingzaken.setJaar(jaar);

        return this;
    }

    public BelastingzakenBuilder metSoort(Belastingzaken.SoortBelastingzaak soortBelastingzaak) {
        belastingzaken.setSoort(soortBelastingzaak);

        return this;
    }

    public Belastingzaken build() {
        return this.belastingzaken;
    }
}
