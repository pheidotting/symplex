package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.GroepBijlages;
import nl.lakedigital.djfc.service.BijlageService;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonBijlageNaarBijlageMapper extends AbstractMapper<JsonBijlage, Bijlage> implements JsonMapper {
    @Inject
    private BijlageService bijlageService;

    @Override
    public Bijlage map(JsonBijlage jsonBijlage, Object parent, Object bestaandObject) {
        Bijlage bijlage = new Bijlage();
        if (jsonBijlage.getId() != null) {
            bijlage = bijlageService.lees(jsonBijlage.getId());
        }

        bijlage.setOmschrijving("".equals(jsonBijlage.getOmschrijving()) ? null : jsonBijlage.getOmschrijving());
        bijlage.setUploadMoment(LocalDateTime.now());
        bijlage.setBestandsNaam(jsonBijlage.getOmschrijvingOfBestandsNaam());
        bijlage.setS3Identificatie(jsonBijlage.getS3Identificatie());

        if (jsonBijlage.getGroepBijlages() != null) {
            GroepBijlages groepBijlages = bijlageService.leesGroepBijlages(jsonBijlage.getGroepBijlages());
            bijlage.setGroepBijlages(groepBijlages);
        }

        return bijlage;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonBijlage;
    }
}
