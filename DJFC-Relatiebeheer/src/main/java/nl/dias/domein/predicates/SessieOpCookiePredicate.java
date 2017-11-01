package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.Sessie;

/**
 * Created by patrickheidotting on 14-10-15.
 */
public class SessieOpCookiePredicate implements Predicate<Sessie> {
    private String cookieCode;

    public SessieOpCookiePredicate(String cookieCode) {
        this.cookieCode = cookieCode;
    }

    @Override
    public boolean apply(Sessie sessie) {
        if (sessie == null || sessie.getCookieCode() == null) {
            return false;
        }
        return sessie.getCookieCode().equals(cookieCode);
    }
}
