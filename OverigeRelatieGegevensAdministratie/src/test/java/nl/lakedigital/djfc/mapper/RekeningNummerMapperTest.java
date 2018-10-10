package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.RekeningNummerService;
import org.easymock.Mock;
import org.springframework.test.util.ReflectionTestUtils;

public class RekeningNummerMapperTest extends AbstractMapperTest<RekeningNummer, JsonRekeningNummer> {
    private RekeningNummerNaarJsonRekeningNummerMapper naarJsonMapper = new RekeningNummerNaarJsonRekeningNummerMapper();
    private JsonRekeningNummerNaarRekeningNummerMapper vanJsonMapper = new JsonRekeningNummerNaarRekeningNummerMapper();

    @Mock
    private RekeningNummerService rekeningNummerService;

    @Override
    public AbstractService getService() {
        ReflectionTestUtils.setField(vanJsonMapper, "rekeningNummerService", rekeningNummerService);

        return rekeningNummerService;
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
        return RekeningNummer.class;
    }

    @Override
    public Class setJsonType() {
        return JsonRekeningNummer.class;
    }

    @Override
    public RekeningNummer maakEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        RekeningNummer rekeningNummer = new RekeningNummer();

        rekeningNummer.setRekeningnummer("rekeningnummer");

        rekeningNummer.setSoortEntiteit(soortEntiteit);
        rekeningNummer.setEntiteitId(entiteitId);

        return rekeningNummer;
    }

    @Override
    public JsonRekeningNummer maakJsonEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        JsonRekeningNummer rekeningNummer = new JsonRekeningNummer();

        rekeningNummer.setRekeningnummer("rekeningnummer");

        rekeningNummer.setSoortEntiteit(soortEntiteit.name());
        rekeningNummer.setEntiteitId(entiteitId);

        return rekeningNummer;
    }
}
