package nl.dias.web.mapper;

import nl.dias.domein.Bedrijf;
import nl.dias.web.mapper.dozer.BedrijfDozerMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonBedrijfMapper extends Mapper<Bedrijf, nl.lakedigital.djfc.commons.domain.response.Bedrijf> {

    @Override
    public Bedrijf mapVanJson(nl.lakedigital.djfc.commons.domain.response.Bedrijf json) {
        return new BedrijfDozerMapper().convertFrom(json, null);

    }

    @Override
    public nl.lakedigital.djfc.commons.domain.response.Bedrijf mapNaarJson(Bedrijf object) {
        return new BedrijfDozerMapper().convertTo(object, null);
    }
}