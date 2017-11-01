package nl.dias.mapper;

import nl.dias.domein.Bedrijf;
import nl.dias.service.BedrijfService;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonBedrijfNaarBedrijfMapper extends AbstractMapper<JsonBedrijf, Bedrijf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonBedrijfNaarBedrijfMapper.class);

    @Inject
    private BedrijfService bedrijfService;

    @Override
    public Bedrijf map(JsonBedrijf jsonBedrijf, Object parent, Object bestaandOjbect) {
        LOGGER.debug("Mappen: ", ReflectionToStringBuilder.toString(jsonBedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        Bedrijf bedrijf;
        if (jsonBedrijf.getId() == null || "0".equals(jsonBedrijf.getId())) {
            bedrijf = new Bedrijf();
        } else {
            bedrijf = bedrijfService.lees(Long.valueOf(jsonBedrijf.getId()));
        }

        bedrijf.setNaam(jsonBedrijf.getNaam());
        bedrijf.setKvk(jsonBedrijf.getKvk());
        bedrijf.setcAoVerplichtingen(jsonBedrijf.getcAoVerplichtingen());
        bedrijf.setEmail(jsonBedrijf.getEmail());
        bedrijf.setHoedanigheid(jsonBedrijf.getHoedanigheid());
        bedrijf.setInternetadres(jsonBedrijf.getInternetadres());
        bedrijf.setRechtsvorm(jsonBedrijf.getRechtsvorm());

        return bedrijf;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonBedrijf;
    }
}
