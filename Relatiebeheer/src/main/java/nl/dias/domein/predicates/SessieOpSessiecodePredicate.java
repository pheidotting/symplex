package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.Sessie;

/**
 * Created by patrickheidotting on 14-10-15.
 */
public class SessieOpSessiecodePredicate implements Predicate<Sessie> {
    private String sessieCode;

    public SessieOpSessiecodePredicate(String sessieCode) {
        this.sessieCode = sessieCode;
    }

    @Override
    public boolean apply(Sessie sessie) {
        if (sessie == null || sessie.getCookieCode() == null) {
            return false;
        }
        return sessie.getSessie().equals(sessieCode);
    }
}
