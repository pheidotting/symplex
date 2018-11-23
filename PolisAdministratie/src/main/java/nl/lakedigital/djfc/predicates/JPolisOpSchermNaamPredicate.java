package nl.lakedigital.djfc.predicates;

import nl.lakedigital.djfc.domain.Polis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

public class JPolisOpSchermNaamPredicate implements Predicate<Polis> {
    private final static Logger LOGGER = LoggerFactory.getLogger(JPolisOpSchermNaamPredicate.class);
    private String schermNaam;

    public JPolisOpSchermNaamPredicate(String schermNaam) {
        this.schermNaam = schermNaam;
    }

    @Override
    public boolean test(Polis polis) {
        return polis.getSchermNaam().replace(" ", "").equals(schermNaam);
    }
}
