package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.polis.Polis;
import nl.dias.domein.polis.SoortVerzekering;

/**
 * Created by patrickheidotting on 15-10-15.
 */
public class PolissenOpSoortPredicate implements Predicate<Polis> {
    private SoortVerzekering soortVerzekering;

    public PolissenOpSoortPredicate(SoortVerzekering soortVerzekering) {
        this.soortVerzekering = soortVerzekering;
    }

    @Override
    public boolean apply(Polis polis) {
        return polis.getSoortVerzekering().equals(soortVerzekering);
    }
}
