package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.BijlageService;
import org.easymock.Mock;
import org.springframework.test.util.ReflectionTestUtils;

public class BijlageMapperTest extends AbstractMapperTest<Bijlage, JsonBijlage> {
    private BijlageNaarJsonBijlageMapper naarJsonMapper = new BijlageNaarJsonBijlageMapper();
    private JsonBijlageNaarBijlageMapper vanJsonMapper = new JsonBijlageNaarBijlageMapper();

    @Mock
    private BijlageService bijlageService;

    @Override
    public AbstractService getService() {
        ReflectionTestUtils.setField(vanJsonMapper, "bijlageService", bijlageService);

        return bijlageService;
    }

    @Override
    public AbstractMapper naarJsonMapper() {
        return naarJsonMapper;
    }

    @Override
    public AbstractMapper vanJsonMapper() {
        return vanJsonMapper;
    }

    @Override
    public Class setType() {
        return Bijlage.class;
    }

    @Override
    public Class setJsonType() {
        return JsonBijlage.class;
    }

    @Override
    public Bijlage maakEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        Bijlage bijlage = new Bijlage();

        bijlage.setOmschrijving("omschrijving");
        bijlage.setBestandsNaam("bestandsnaam");

        bijlage.setSoortEntiteit(soortEntiteit);
        bijlage.setEntiteitId(entiteitId);

        return bijlage;
    }

    @Override
    public JsonBijlage maakJsonEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        JsonBijlage bijlage = new JsonBijlage();

        bijlage.setOmschrijving("omschrijving");
        bijlage.setBestandsNaam("bestandsnaam");
        bijlage.setOmschrijvingOfBestandsNaam("bestandsnaam");

        bijlage.setSoortEntiteit(soortEntiteit.name());
        bijlage.setEntiteitId(entiteitId);

        return bijlage;
    }
}
