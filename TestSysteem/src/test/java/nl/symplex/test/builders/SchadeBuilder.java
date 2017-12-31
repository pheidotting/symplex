package nl.symplex.test.builders;

import nl.lakedigital.djfc.domain.response.Opmerking;
import nl.lakedigital.djfc.domain.response.Polis;
import nl.lakedigital.djfc.domain.response.Schade;

public class SchadeBuilder {
    private Schade schade;

    public SchadeBuilder() {
        this.schade = new Schade();
    }

    public SchadeBuilder defaultSchade() {
        schade.setDatumTijdMelding("2017-07-31T17:21");
        schade.setDatumTijdSchade("2017-07-31T17:21");
        schade.setSchadeNummerMaatschappij("SCHADE12345");
        schade.setStatusSchade("In behandeling maatschappij");

        return this;
    }

    public SchadeBuilder metPolis(Polis polis) {
        schade.setParentIdentificatie(polis.getIdentificatie());

        return this;
    }

    public SchadeBuilder metOpmerking(Opmerking opmerking) {
        schade.getOpmerkingen().add(opmerking);

        return this;
    }

    public Schade build() {
        return schade;
    }
}
