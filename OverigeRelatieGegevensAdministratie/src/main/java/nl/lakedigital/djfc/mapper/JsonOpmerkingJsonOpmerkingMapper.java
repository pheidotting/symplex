package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.service.OpmerkingService;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonOpmerkingJsonOpmerkingMapper extends AbstractMapper<JsonOpmerking, Opmerking> implements JsonMapper {
    @Inject
    private OpmerkingService opmerkingService;


    @Override
    public Opmerking map(JsonOpmerking jsonOpmerking, Object parent, Object bestaandOjbect) {
        Opmerking opmerking = new Opmerking();

        if (jsonOpmerking.getId() != null) {
            opmerking = opmerkingService.lees(jsonOpmerking.getId());
        }

        opmerking.setOpmerking(jsonOpmerking.getOpmerking());
        opmerking.setMedewerker(jsonOpmerking.getMedewerkerId());

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-YYYY HH:mm");

        if(jsonOpmerking.getTijd()!=null) {
            opmerking.setTijd(LocalDateTime.parse(jsonOpmerking.getTijd(), dateTimeFormatter));
        }

        return opmerking;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonOpmerking;
    }
}
