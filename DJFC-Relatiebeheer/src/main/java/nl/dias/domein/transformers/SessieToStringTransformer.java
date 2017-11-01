package nl.dias.domein.transformers;

import com.google.common.base.Function;
import nl.dias.domein.Sessie;

/**
 * Created by patrickheidotting on 06-10-15.
 */
public class SessieToStringTransformer implements Function<Sessie, String> {
    @Override
    public String apply(Sessie sessie) {
        String id = null;
        if (sessie != null) {
            id = sessie.getId().toString();
        }
        return id;
    }
}
