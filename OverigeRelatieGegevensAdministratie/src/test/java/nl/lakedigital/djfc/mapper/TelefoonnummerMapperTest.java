package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.domain.TelefoonnummerSoort;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.easymock.Mock;
import org.springframework.test.util.ReflectionTestUtils;

public class TelefoonnummerMapperTest extends AbstractMapperTest<Telefoonnummer, JsonTelefoonnummer> {
    private TelefoonnummerNaarJsonTelefoonnummerMapper naarJsonMapper = new TelefoonnummerNaarJsonTelefoonnummerMapper();
    private JsonTelefoonnummerNaarTelefoonnummerMapper vanJsonMapper = new JsonTelefoonnummerNaarTelefoonnummerMapper();

    @Mock
    private TelefoonnummerService telefoonnummerService;

    @Override
    public AbstractService getService() {
        ReflectionTestUtils.setField(vanJsonMapper, "telefoonnummerService", telefoonnummerService);

        return telefoonnummerService;
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
        return Telefoonnummer.class;
    }

    @Override
    public Class setJsonType() {
        return JsonTelefoonnummer.class;
    }

    @Override
    public Telefoonnummer maakEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        Telefoonnummer telefoonnummer = new Telefoonnummer();

        telefoonnummer.setTelefoonnummer("telefoonnummer");
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL);
        telefoonnummer.setOmschrijving("omschrijving");

        telefoonnummer.setSoortEntiteit(soortEntiteit);
        telefoonnummer.setEntiteitId(entiteitId);

        return telefoonnummer;
    }

    @Override
    public JsonTelefoonnummer maakJsonEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        JsonTelefoonnummer telefoonnummer = new JsonTelefoonnummer();

        telefoonnummer.setTelefoonnummer("telefoonnummer");
        telefoonnummer.setSoort(TelefoonnummerSoort.MOBIEL.name());
        telefoonnummer.setOmschrijving("omschrijving");

        telefoonnummer.setSoortEntiteit(soortEntiteit.name());
        telefoonnummer.setEntiteitId(entiteitId);

        return telefoonnummer;
    }
}
