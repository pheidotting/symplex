package nl.dias.web.mapper;

import nl.dias.domein.ContactPersoon;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.TelefoonnummerClient;
import nl.lakedigital.djfc.commons.json.Identificatie;

import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainToDtoContactPersoonMapper implements Function<ContactPersoon, nl.lakedigital.djfc.commons.domain.response.ContactPersoon> {
    private IdentificatieClient identificatieClient;
    private TelefoonnummerClient telefoonnummerClient;

    public DomainToDtoContactPersoonMapper(IdentificatieClient identificatieClient, TelefoonnummerClient telefoonnummerClient) {
        this.identificatieClient = identificatieClient;
        this.telefoonnummerClient = telefoonnummerClient;
    }

    @Override
    public nl.lakedigital.djfc.commons.domain.response.ContactPersoon apply(ContactPersoon contactPersoon) {
        nl.lakedigital.djfc.commons.domain.response.ContactPersoon cp = new nl.lakedigital.djfc.commons.domain.response.ContactPersoon();

        Identificatie identificatie = identificatieClient.zoekIdentificatie("CONTACTPERSOON", contactPersoon.getId());

        cp.setIdentificatie(identificatie.getIdentificatie());
        cp.setAchternaam(contactPersoon.getAchternaam());
        cp.setEmailadres(contactPersoon.getEmailadres());
        cp.setFunctie(contactPersoon.getFunctie());
        cp.setTussenvoegsel(contactPersoon.getTussenvoegsel());
        cp.setVoornaam(contactPersoon.getVoornaam());

        cp.setTelefoonnummers(telefoonnummerClient.lijst("CONTACTPERSOON", contactPersoon.getId()).stream().map(new JsonToDtoTelefoonnummerMapper(identificatieClient)).collect(Collectors.toList()));

        return cp;
    }
}
