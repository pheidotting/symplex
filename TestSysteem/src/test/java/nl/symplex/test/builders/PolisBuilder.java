package nl.symplex.test.builders;

import nl.lakedigital.djfc.domain.response.Opmerking;
import nl.lakedigital.djfc.domain.response.Polis;

public class PolisBuilder {
    private Polis polis;

    public PolisBuilder() {
        this.polis = new Polis();
    }

    public PolisBuilder defaultPolis() {
        polis.setPolisNummer("ABCD12345");
        polis.setIngangsDatum("2017-07-31");
        polis.setSoort("Auto");
        polis.setMaatschappij("2");

        return this;
    }

    public PolisBuilder metRelatie(String identificatie) {
        polis.setParentIdentificatie(identificatie);
        polis.setSoortEntiteit("RELATIE");

        return this;
    }

    public PolisBuilder metOpmerking(Opmerking opmerking) {
        polis.getOpmerkingen().add(opmerking);

        return this;
    }

    public Polis build() {
        return polis;
    }
}
