package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonSoortSchade;
import nl.lakedigital.djfc.domain.SoortSchade;
import org.springframework.stereotype.Component;

@Component
public class SoortSchadeNaarJsonSoortSchadeMapper extends AbstractMapper<SoortSchade, JsonSoortSchade> implements JsonMapper {
    @Override
    public JsonSoortSchade map(SoortSchade object, Object parent, Object bestaandObject) {
        return new JsonSoortSchade(object.getOmschrijving());
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof SoortSchade;
    }
}
