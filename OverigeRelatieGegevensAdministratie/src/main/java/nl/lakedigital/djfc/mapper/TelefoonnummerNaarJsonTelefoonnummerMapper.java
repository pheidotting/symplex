package nl.lakedigital.djfc.mapper;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TelefoonnummerNaarJsonTelefoonnummerMapper extends AbstractMapper<Telefoonnummer, JsonTelefoonnummer> implements JsonMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelefoonnummerNaarJsonTelefoonnummerMapper.class);

    @Override
    public JsonTelefoonnummer map(Telefoonnummer object, Object parent, Object bestaandOjbect) {
        if (object == null) {
            return null;
        }
        LOGGER.debug("Mappen : {}", ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE));

        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

        jsonTelefoonnummer.setId(object.getId());
        jsonTelefoonnummer.setOmschrijving(object.getOmschrijving());
        jsonTelefoonnummer.setSoort(object.getSoort().toString());
        jsonTelefoonnummer.setTelefoonnummer(object.getTelefoonnummer());

        return jsonTelefoonnummer;
    }

    @Override
    public boolean isVoorMij(Object object) {
        return object instanceof Telefoonnummer;
    }
}
