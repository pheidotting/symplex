package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.AdresService;
import org.easymock.Mock;
import org.springframework.test.util.ReflectionTestUtils;

public class AdresMapperTest extends AbstractMapperTest<Adres, JsonAdres> {
    private AdresNaarJsonAdresMapper naarJsonMapper = new AdresNaarJsonAdresMapper();
    private JsonAdresNaarAdresMapper vanJsonMapper = new JsonAdresNaarAdresMapper();

    @Mock
    private AdresService adresService;

    @Override
    public AbstractService getService() {
        ReflectionTestUtils.setField(vanJsonMapper, "adresService", adresService);

        return adresService;
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
        return Adres.class;
    }

    @Override
    public Class setJsonType() {
        return JsonAdres.class;
    }

    @Override
    public Adres maakEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        Adres adres = new Adres();
        adres.setPostcode("1234AA");
        adres.setSoortAdres(Adres.SoortAdres.POSTADRES);
        adres.setHuisnummer(1L);
        adres.setPlaats("plaats");
        adres.setStraat("straat");
        adres.setToevoeging("toevoeging");

        adres.setSoortEntiteit(soortEntiteit);
        adres.setEntiteitId(entiteitId);

        return adres;
    }

    @Override
    public JsonAdres maakJsonEntiteit(SoortEntiteit soortEntiteit, Long entiteitId) {
        JsonAdres adres = new JsonAdres();
        adres.setPostcode("1234AA");
        adres.setSoortAdres(Adres.SoortAdres.POSTADRES.name());
        adres.setHuisnummer(1L);
        adres.setPlaats("plaats");
        adres.setStraat("straat");
        adres.setToevoeging("toevoeging");

        adres.setSoortEntiteit(soortEntiteit.name());
        adres.setEntiteitId(entiteitId);

        return adres;
    }

}
