package nl.dias.mapper;

import nl.dias.domein.Medewerker;
import nl.lakedigital.djfc.commons.json.JsonMedewerker;
import org.springframework.stereotype.Component;

@Component
public class MedewerkerNaarJsonMedewerkerMapper extends AbstractMapper<Medewerker, JsonMedewerker> {
    @Override
    public JsonMedewerker map(Medewerker medewerker, Object parent, Object bestaandObject) {
        JsonMedewerker jsonMedewerker = new JsonMedewerker();
        jsonMedewerker.setEmailadres(medewerker.getEmailadres());
        jsonMedewerker.setAchternaam(medewerker.getAchternaam());
        jsonMedewerker.setTussenvoegsel(medewerker.getTussenvoegsel());
        jsonMedewerker.setVoornaam(medewerker.getVoornaam());
        jsonMedewerker.setKantoor(medewerker.getKantoor().getId());

        return jsonMedewerker;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof Medewerker;
    }
}
