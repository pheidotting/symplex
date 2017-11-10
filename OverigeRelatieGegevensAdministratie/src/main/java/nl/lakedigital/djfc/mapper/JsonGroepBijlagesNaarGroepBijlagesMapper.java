package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonGroepBijlagesNaarGroepBijlagesMapper extends AbstractMapper<JsonGroepBijlages, GroepBijlages> implements JsonMapper {
    @Inject
    private JsonBijlageNaarBijlageMapper jsonBijlageNaarBijlageMapper;

    @Override
    public GroepBijlages map(JsonGroepBijlages object, Object parent, Object bestaandObject) {
        GroepBijlages groepBijlages = new GroepBijlages();

        groepBijlages.setNaam(object.getNaam());
        groepBijlages.setId(object.getId());

        for (JsonBijlage bijlage : object.getBijlages()) {
            Bijlage b = jsonBijlageNaarBijlageMapper.map(bijlage, null, Bijlage.class);
            b.setGroepBijlages(groepBijlages);

            groepBijlages.getBijlages().add(b);
        }

        return groepBijlages;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonGroepBijlages;
    }
}
