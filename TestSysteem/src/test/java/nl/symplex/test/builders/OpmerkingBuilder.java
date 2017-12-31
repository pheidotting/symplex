package nl.symplex.test.builders;

import nl.lakedigital.djfc.domain.response.Opmerking;

import java.util.UUID;

public class OpmerkingBuilder {
    private Opmerking opmerking;

    public OpmerkingBuilder() {
        this.opmerking = new Opmerking();
    }

    public OpmerkingBuilder metTekst() {
        this.opmerking.setOpmerking(UUID.randomUUID().toString());
        return this;
    }

    public Opmerking build() {
        return this.opmerking;
    }
}
