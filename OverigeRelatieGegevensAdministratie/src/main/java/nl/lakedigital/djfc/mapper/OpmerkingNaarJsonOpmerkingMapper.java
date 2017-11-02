package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.domain.Opmerking;
import org.springframework.stereotype.Component;

@Component
public class OpmerkingNaarJsonOpmerkingMapper extends AbstractMapper<Opmerking, JsonOpmerking> implements JsonMapper {
    @Override
    public JsonOpmerking map(Opmerking opmerking, Object parent, Object bestaandOjbect) {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();

        jsonOpmerking.setId(opmerking.getId());
        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setTijd(opmerking.getTijd().toString("dd-MM-yyyy HH:mm"));
        jsonOpmerking.setMedewerkerId(opmerking.getMedewerker());

        return jsonOpmerking;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof Opmerking;
    }
}
