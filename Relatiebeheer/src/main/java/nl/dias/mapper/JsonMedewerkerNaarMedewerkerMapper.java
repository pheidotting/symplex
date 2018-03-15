package nl.dias.mapper;

import nl.dias.domein.Medewerker;
import nl.lakedigital.djfc.commons.json.JsonMedewerker;
import org.springframework.stereotype.Component;

@Component
public class JsonMedewerkerNaarMedewerkerMapper extends AbstractMapper<JsonMedewerker, Medewerker> {
    @Override
    public Medewerker map(JsonMedewerker jsonMedewerker, Object parent, Object bestaandObject) {
        Medewerker medewerker = new Medewerker();

        if (bestaandObject != null) {
            medewerker = (Medewerker) bestaandObject;
        }
        medewerker.setEmailadres(jsonMedewerker.getEmailadres());
        medewerker.setAchternaam(jsonMedewerker.getAchternaam());
        medewerker.setTussenvoegsel(jsonMedewerker.getTussenvoegsel());
        medewerker.setVoornaam(jsonMedewerker.getVoornaam());

        return medewerker;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonMedewerker;
    }
}
