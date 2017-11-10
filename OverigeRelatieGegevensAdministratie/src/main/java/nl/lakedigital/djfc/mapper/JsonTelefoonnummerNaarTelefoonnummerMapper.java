package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.domain.TelefoonnummerSoort;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class JsonTelefoonnummerNaarTelefoonnummerMapper extends AbstractMapper<JsonTelefoonnummer, Telefoonnummer> implements JsonMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonTelefoonnummerNaarTelefoonnummerMapper.class);

    @Inject
    private TelefoonnummerService telefoonnummerService;

    @Override
    public Telefoonnummer map(final JsonTelefoonnummer jsonTelefoonnummer, Object parent, Object bestaandOjbect) {
        LOGGER.debug("Mappen : {}", ReflectionToStringBuilder.toString(jsonTelefoonnummer, ToStringStyle.SHORT_PREFIX_STYLE));

        Telefoonnummer telefoonnummer = new Telefoonnummer();

        if (jsonTelefoonnummer.getId() != null) {
            telefoonnummer = telefoonnummerService.lees(jsonTelefoonnummer.getId());
        }
        telefoonnummer.setId(jsonTelefoonnummer.getId());
        telefoonnummer.setOmschrijving("".equals(jsonTelefoonnummer.getOmschrijving()) ? null : jsonTelefoonnummer.getOmschrijving());
        telefoonnummer.setSoort(TelefoonnummerSoort.valueOf(jsonTelefoonnummer.getSoort()));
        telefoonnummer.setTelefoonnummer(jsonTelefoonnummer.getTelefoonnummer());

        return telefoonnummer;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof JsonTelefoonnummer;
    }
}
