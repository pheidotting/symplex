package nl.lakedigital.djfc.predicates;

import nl.lakedigital.djfc.domain.Polis;

import java.util.function.Predicate;

public class JPolisOpSchermNaamPredicate implements Predicate<Polis> {
    private String schermNaam;

    public JPolisOpSchermNaamPredicate(String schermNaam) {
        this.schermNaam = schermNaam;
    }

    @Override
    public boolean test(Polis polis) {
        return polis.getSchermNaam().equals(schermNaam);
    }
}
