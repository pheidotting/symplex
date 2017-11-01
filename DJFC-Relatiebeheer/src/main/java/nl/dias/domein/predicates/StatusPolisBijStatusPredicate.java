package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.StatusPolis;

/**
 * Created by patrickheidotting on 15-10-15.
 */
public class StatusPolisBijStatusPredicate implements Predicate<StatusPolis> {
    private String omschrijving;

    public StatusPolisBijStatusPredicate(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    @Override
    public boolean apply(StatusPolis statusPolis) {
        if (omschrijving.equals(statusPolis.getOmschrijving())) {
            return true;
        }

        return false;
    }
}
