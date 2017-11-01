package nl.dias.mapper;

import nl.dias.domein.JaarCijfers;
import nl.lakedigital.djfc.commons.json.JsonJaarCijfers;
import org.springframework.stereotype.Component;

@Component
public class JaarCijfersNaarJsonJaarCijfersMapper extends AbstractMapper<JaarCijfers, JsonJaarCijfers> {
    @Override
    public JsonJaarCijfers map(JaarCijfers object, Object parent, Object bestaandObject) {
        JsonJaarCijfers json = new JsonJaarCijfers();

        json.setJaar(object.getJaar());
        if (object.getBedrijf() != null) {
            json.setBedrijf(object.getBedrijf().getId());
        }
        json.setId(object.getId());

        return json;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JaarCijfers;
    }
}
