package nl.dias.web.mapper.dozer;

import nl.dias.domein.Bedrijf;
import org.dozer.DozerBeanMapper;
import org.dozer.DozerConverter;

public class BedrijfDozerMapper extends DozerConverter<Bedrijf, nl.lakedigital.djfc.commons.domain.response.Bedrijf> {
    public BedrijfDozerMapper() {
        super(Bedrijf.class, nl.lakedigital.djfc.commons.domain.response.Bedrijf.class);
    }

    @Override
    public nl.lakedigital.djfc.commons.domain.response.Bedrijf convertTo(Bedrijf bedrijf, nl.lakedigital.djfc.commons.domain.response.Bedrijf jsonBedrijf) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        return mapper.map(bedrijf, nl.lakedigital.djfc.commons.domain.response.Bedrijf.class);
    }

    @Override
    public Bedrijf convertFrom(nl.lakedigital.djfc.commons.domain.response.Bedrijf jsonBedrijf, Bedrijf bedrijf) {
        Bedrijf result = new Bedrijf();
        result.setKvk(jsonBedrijf.getKvk());
        result.setNaam(jsonBedrijf.getNaam());
        result.setEmail(jsonBedrijf.getEmail());
        result.setcAoVerplichtingen(jsonBedrijf.getcAoVerplichtingen());
        result.setHoedanigheid(jsonBedrijf.getHoedanigheid());
        result.setInternetadres(jsonBedrijf.getInternetadres());
        result.setRechtsvorm(jsonBedrijf.getRechtsvorm());
        if (jsonBedrijf.getId() != null) {
            result.setId(Long.valueOf(jsonBedrijf.getId()));
        }

        return result;
    }
}
