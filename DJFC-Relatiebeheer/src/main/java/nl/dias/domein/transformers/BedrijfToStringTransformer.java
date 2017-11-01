package nl.dias.domein.transformers;

import com.google.common.base.Function;
import nl.dias.domein.Bedrijf;

/**
 * Created by patrickheidotting on 06-10-15.
 */
public class BedrijfToStringTransformer implements Function<Bedrijf, String> {
    @Override
    public String apply(Bedrijf bedrijf) {
        String id = null;
        if (bedrijf != null && bedrijf.getId() != null) {
            id = bedrijf.getId().toString();
        }
        return id;
    }
}
