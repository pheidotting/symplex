package nl.dias.mapper;

import nl.dias.domein.ContactPersoon;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonContactPersoon;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ContactPersoonNaarJsonContactPersoonMapper extends AbstractMapper<ContactPersoon, JsonContactPersoon> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactPersoonNaarJsonContactPersoonMapper.class);

@Inject
private IdentificatieClient identificatieClient;

    @Override
    public JsonContactPersoon map(ContactPersoon object, Object parent, Object bestaandObject) {
        LOGGER.debug("Mappen {}", ReflectionToStringBuilder.toString(object, ToStringStyle.SHORT_PREFIX_STYLE));

        JsonContactPersoon jsonContactPersoon = new JsonContactPersoon();

        Identificatie identificatie=identificatieClient.zoekIdentificatie("CONTACTPERSOON",object.getId());
        Identificatie bedrijfIdentificatie=identificatieClient.zoekIdentificatie("Bedrijf",object.getBedrijf());

        jsonContactPersoon.setIdentificatie(identificatie.getIdentificatie());
        jsonContactPersoon.setBedrijf(String.valueOf(bedrijfIdentificatie.getEntiteitId()));
        jsonContactPersoon.setAchternaam(object.getAchternaam());
        jsonContactPersoon.setEmailadres(object.getEmailadres());
        jsonContactPersoon.setFunctie(object.getFunctie());
        jsonContactPersoon.setTussenvoegsel(object.getTussenvoegsel());
        jsonContactPersoon.setVoornaam(object.getVoornaam());

        LOGGER.debug("Gemapt naar {}", ReflectionToStringBuilder.toString(jsonContactPersoon, ToStringStyle.SHORT_PREFIX_STYLE));

        return jsonContactPersoon;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof ContactPersoon;
    }
}
