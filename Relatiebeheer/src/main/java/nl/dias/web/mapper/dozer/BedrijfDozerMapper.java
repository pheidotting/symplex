package nl.dias.web.mapper.dozer;

import nl.dias.domein.Bedrijf;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;

public class BedrijfDozerMapper extends DozerConverter<Bedrijf, nl.lakedigital.djfc.domain.response.Bedrijf> {
    public BedrijfDozerMapper() {
        super(Bedrijf.class, nl.lakedigital.djfc.domain.response.Bedrijf.class);
    }

    @Override
    public nl.lakedigital.djfc.domain.response.Bedrijf convertTo(Bedrijf bedrijf, nl.lakedigital.djfc.domain.response.Bedrijf jsonBedrijf) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        nl.lakedigital.djfc.domain.response.Bedrijf result = mapper.map(bedrijf, nl.lakedigital.djfc.domain.response.Bedrijf.class);

        return result;
    }

    @Override
    public Bedrijf convertFrom(nl.lakedigital.djfc.domain.response.Bedrijf jsonBedrijf, Bedrijf bedrijf) {
        Bedrijf result = new Bedrijf();
        result.setKvk(jsonBedrijf.getKvk());
        result.setNaam(jsonBedrijf.getNaam());
        if (jsonBedrijf.getId() != null) {
            result.setId(Long.valueOf(jsonBedrijf.getId()));
        }

        return result;
    }
}
