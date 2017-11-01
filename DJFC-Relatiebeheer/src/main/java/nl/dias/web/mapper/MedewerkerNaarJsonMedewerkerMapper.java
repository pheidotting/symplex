//package nl.dias.web.mapper;
//
//import com.google.common.base.Function;
//import nl.dias.domein.Medewerker;
//import nl.lakedigital.djfc.commons.json.JsonMedewerker;
//import nl.lakedigital.djfc.commons.json.JsonRelatie;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MedewerkerNaarJsonMedewerkerMapper implements Function<Medewerker,JsonMedewerker> {
//    @Override
//    public JsonMedewerker apply(Medewerker medewerker) {
//        JsonMedewerker jsonMedewerker = new JsonMedewerker();
//
//        jsonMedewerker.setId(medewerker.getId());
//        jsonMedewerker.setVoornaam(medewerker.getVoornaam());
//        if (medewerker.getTussenvoegsel() != null) {
//            jsonMedewerker.setTussenvoegsel(medewerker.getTussenvoegsel());
//        } else {
//            jsonMedewerker.setTussenvoegsel("");
//        }
//        jsonMedewerker.setAchternaam(medewerker.getAchternaam());
//        jsonMedewerker.setEmailadres(medewerker.getEmailadres());
//
//        return jsonMedewerker;
//    }
//}
