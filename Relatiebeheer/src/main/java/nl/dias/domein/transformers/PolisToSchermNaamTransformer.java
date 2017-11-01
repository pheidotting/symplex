package nl.dias.domein.transformers;

import com.google.common.base.Function;
import nl.dias.domein.polis.Polis;

public class PolisToSchermNaamTransformer implements Function<Polis, String> {
    @Override
    public String apply(Polis polis) {
        return polis.getSchermNaam();
    }
}
