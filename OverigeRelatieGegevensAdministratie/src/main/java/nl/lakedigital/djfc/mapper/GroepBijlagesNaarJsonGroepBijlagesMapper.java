package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class GroepBijlagesNaarJsonGroepBijlagesMapper extends AbstractMapper<GroepBijlages, JsonGroepBijlages> implements JsonMapper {
    @Inject
    private BijlageNaarJsonBijlageMapper bijlageNaarJsonBijlageMapper;

    @Override
    public JsonGroepBijlages map(GroepBijlages object, Object parent, Object bestaandObject) {
        JsonGroepBijlages jsonGroepBijlages = new JsonGroepBijlages();

        jsonGroepBijlages.setNaam(object.getNaam());
        jsonGroepBijlages.setId(object.getId());

        for (Bijlage bijlage : object.getBijlages()) {
            jsonGroepBijlages.getBijlages().add(bijlageNaarJsonBijlageMapper.map(bijlage, null, JsonBijlage.class));
        }

        return jsonGroepBijlages;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof GroepBijlages;
    }
}
