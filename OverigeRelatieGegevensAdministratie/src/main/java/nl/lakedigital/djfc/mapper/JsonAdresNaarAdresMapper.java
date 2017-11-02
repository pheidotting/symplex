package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.service.AdresService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonAdresNaarAdresMapper extends AbstractMapper<JsonAdres, Adres> implements JsonMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonAdresNaarAdresMapper.class);

    @Inject
    private AdresService adresService;

    @Override
    public Adres map(JsonAdres jsonAdres, Object parent, Object bestaandObject) {
        Adres adres = new Adres();

        if (jsonAdres.getId() != null) {
            adres = adresService.lees(jsonAdres.getId());

            LOGGER.debug(ReflectionToStringBuilder.toString(adres));
        }

        adres.setId(jsonAdres.getId());
        adres.setHuisnummer(jsonAdres.getHuisnummer());
        adres.setPlaats("".equals(jsonAdres.getPlaats()) ? null : jsonAdres.getPlaats());
        adres.setPostcode("".equals(jsonAdres.getPostcode()) ? null : jsonAdres.getPostcode());
        adres.setStraat("".equals(jsonAdres.getStraat()) ? null : jsonAdres.getStraat());
        adres.setToevoeging("".equals(jsonAdres.getToevoeging()) ? null : jsonAdres.getToevoeging());
        if (jsonAdres.getSoortAdres() != null) {
            adres.setSoortAdres(Adres.SoortAdres.valueOf(jsonAdres.getSoortAdres()));
        }

        return adres;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonAdres;
    }
}
