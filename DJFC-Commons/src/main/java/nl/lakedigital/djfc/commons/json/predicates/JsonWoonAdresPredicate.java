package nl.lakedigital.djfc.commons.json.predicates;

import com.google.common.base.Predicate;
import nl.lakedigital.djfc.commons.json.JsonAdres;

public class JsonWoonAdresPredicate implements Predicate<JsonAdres> {
    @Override
    public boolean apply(JsonAdres adres) {
        return "WOONADRES".equals(adres.getSoortAdres());
    }
}
