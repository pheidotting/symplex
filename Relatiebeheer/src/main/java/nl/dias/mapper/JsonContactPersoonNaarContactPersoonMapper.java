package nl.dias.mapper;

import nl.dias.domein.ContactPersoon;
import nl.dias.service.GebruikerService;
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
public class JsonContactPersoonNaarContactPersoonMapper extends AbstractMapper<JsonContactPersoon, ContactPersoon> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonContactPersoonNaarContactPersoonMapper.class);

    @Inject
    private GebruikerService gebruikerService;
    @Inject
    private IdentificatieClient identificatieClient;

    @Override
    public ContactPersoon map(JsonContactPersoon jsonContactPersoon, Object parent, Object bestaandOjbect) {
        if (jsonContactPersoon == null) {
            return null;
        }
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("##################");
        LOGGER.debug("Mappen {}", ReflectionToStringBuilder.toString(jsonContactPersoon, ToStringStyle.SHORT_PREFIX_STYLE));

        Long cpId = null;
        ContactPersoon contactPersoon = new ContactPersoon();
        LOGGER.debug("Identificatie : {}", jsonContactPersoon.getIdentificatie());
        if (jsonContactPersoon.getIdentificatie() != null) {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(jsonContactPersoon.getIdentificatie());
            LOGGER.debug("Opgehaald : {}", ReflectionToStringBuilder.toString(identificatie));
            cpId = identificatie.getEntiteitId();
            contactPersoon = (ContactPersoon) gebruikerService.lees(cpId);
        }

        contactPersoon.setId(cpId);
        contactPersoon.setAchternaam(jsonContactPersoon.getAchternaam());
        contactPersoon.setEmailadres(jsonContactPersoon.getEmailadres());
        contactPersoon.setFunctie(jsonContactPersoon.getFunctie());
        contactPersoon.setTussenvoegsel(jsonContactPersoon.getTussenvoegsel());
        contactPersoon.setVoornaam(jsonContactPersoon.getVoornaam());

        Identificatie bedrijfIdentificatie = identificatieClient.zoekIdentificatieCode(jsonContactPersoon.getBedrijf());

        contactPersoon.setBedrijf(bedrijfIdentificatie.getEntiteitId());


        //        contactPersoon.setTelefoonnummers(jsonTelefoonnummerNaarTelefoonnummerMapper.mapAllNaarSet(jsonContactPersoon.getTelefoonnummers(), jsonContactPersoon));

        return contactPersoon;
    }

    @Override
    boolean isVoorMij(Object object) {
        return object instanceof JsonContactPersoon;
    }
}
