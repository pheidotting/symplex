package nl.dias.domein.predicates;

import com.google.common.base.Predicate;
import nl.dias.domein.polis.Polis;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolisOpSchermNaamPredicate implements Predicate<Polis> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpSchermNaamPredicate.class);
    private String schermNaam;

    public PolisOpSchermNaamPredicate(String schermNaam) {
        this.schermNaam = schermNaam;
    }

    @Override
    public boolean apply(Polis polis) {
        boolean klopt = polis.getSchermNaam().equals(schermNaam);

        LOGGER.trace("{} {}", klopt, ReflectionToStringBuilder.toString(polis, ToStringStyle.SHORT_PREFIX_STYLE));

        return klopt;
    }
}
