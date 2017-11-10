package nl.dias.mapper;

import nl.dias.domein.Bijlage;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import org.springframework.stereotype.Component;

@Component
public class BijlageNaarJsonBijlageMapper extends AbstractMapper<Bijlage, JsonBijlage> {
    @Override
    public JsonBijlage map(Bijlage bijlage, Object parent, Object bestaandObject) {
        JsonBijlage json = new JsonBijlage();
        json.setId(bijlage.getId());
        if (bijlage.getOmschrijving() != null) {
            json.setOmschrijvingOfBestandsNaam(bijlage.getOmschrijving());
        } else {
            json.setOmschrijvingOfBestandsNaam(bijlage.getBestandsNaam());
        }
        json.setDatumUpload(bijlage.getUploadMoment().toString("dd-MM-yyyy HH:mm"));
        json.setBestandsNaam(bijlage.getBestandsNaam());
        json.setEntiteitId(bijlage.getEntiteitId());
        json.setSoortEntiteit(bijlage.getSoortBijlage().toString().toUpperCase());
        json.setS3Identificatie(bijlage.getS3Identificatie());

        return json;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Bijlage;
    }
}
