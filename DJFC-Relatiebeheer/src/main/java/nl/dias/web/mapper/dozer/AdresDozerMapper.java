package nl.dias.web.mapper.dozer;

import nl.dias.domein.Bedrijf;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;

public class AdresDozerMapper extends DozerConverter<Bedrijf, JsonBedrijf> {
    public AdresDozerMapper() {
        super(Bedrijf.class, JsonBedrijf.class);
    }

    @Override
    public JsonBedrijf convertTo(Bedrijf bedrijf, JsonBedrijf jsonBedrijf) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        JsonBedrijf result = mapper.map(bedrijf, JsonBedrijf.class);

        return result;
    }

    @Override
    public Bedrijf convertFrom(JsonBedrijf jsonBedrijf, Bedrijf bedrijf) {
        Bedrijf result = new Bedrijf();
        result.setKvk(jsonBedrijf.getKvk());
        result.setNaam(jsonBedrijf.getNaam());
        if (jsonBedrijf.getId() != null) {
            result.setId(Long.valueOf(jsonBedrijf.getId()));
        }

        return result;
    }
}
