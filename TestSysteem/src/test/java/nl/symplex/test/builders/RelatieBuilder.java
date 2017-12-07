package nl.symplex.test.builders;

import nl.lakedigital.djfc.domain.response.Relatie;

public class RelatieBuilder {
    private Relatie relatie;

    public RelatieBuilder() {
        this.relatie = new Relatie();
    }

    public RelatieBuilder defaultRelatie() {
        relatie.setAchternaam("Achternaam");
        relatie.setTussenvoegsel("Tussenvoegsel");
        relatie.setVoornaam("Voornaam");
        relatie.setBsn("bsn");
        relatie.setBurgerlijkeStaat("Ongehuwd");
        relatie.setEmailadres("info@symplex.nl");
        relatie.setGeboorteDatum("1979-09-06");
        relatie.setGeslacht("M");
        relatie.setRoepnaam("Henk");

        return this;
    }

    public Relatie build() {
        return this.relatie;
    }
}
