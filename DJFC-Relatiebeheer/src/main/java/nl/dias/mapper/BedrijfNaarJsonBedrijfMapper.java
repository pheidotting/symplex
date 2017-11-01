package nl.dias.mapper;

import nl.dias.domein.Bedrijf;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BedrijfNaarJsonBedrijfMapper extends AbstractMapper<Bedrijf, JsonBedrijf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BedrijfNaarJsonBedrijfMapper.class);

    @Override
    public JsonBedrijf map(Bedrijf bedrijf, Object parent, Object bestaandObject) {
        JsonBedrijf jsonBedrijf = new JsonBedrijf();

        LOGGER.debug("Map naar JSON {}", ReflectionToStringBuilder.toString(bedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        jsonBedrijf.setId(bedrijf.getId().toString());
        jsonBedrijf.setKvk(bedrijf.getKvk());
        jsonBedrijf.setNaam(bedrijf.getNaam());
        jsonBedrijf.setcAoVerplichtingen(bedrijf.getcAoVerplichtingen());
        jsonBedrijf.setEmail(bedrijf.getEmail());
        jsonBedrijf.setHoedanigheid(bedrijf.getHoedanigheid());
        jsonBedrijf.setRechtsvorm(bedrijf.getRechtsvorm());
        jsonBedrijf.setInternetadres(bedrijf.getInternetadres());

        LOGGER.debug("Gemapt naar {}", ReflectionToStringBuilder.toString(jsonBedrijf, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonBedrijf;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof Bedrijf;
    }
}
