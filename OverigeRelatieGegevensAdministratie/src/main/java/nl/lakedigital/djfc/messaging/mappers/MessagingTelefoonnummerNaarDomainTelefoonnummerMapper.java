package nl.lakedigital.djfc.messaging.mappers;

import nl.lakedigital.as.messaging.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.as.messaging.domain.Telefoonnummer;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.TelefoonnummerSoort;
import nl.lakedigital.djfc.service.TelefoonnummerService;

import java.util.function.Function;

public class MessagingTelefoonnummerNaarDomainTelefoonnummerMapper implements Function<AbstracteEntiteitMetSoortEnId, nl.lakedigital.djfc.domain.Telefoonnummer> {
    private IdentificatieClient identificatieClient;
    private TelefoonnummerService telefoonnummerService;

    public MessagingTelefoonnummerNaarDomainTelefoonnummerMapper(IdentificatieClient identificatieClient, TelefoonnummerService telefoonnummerService) {
        this.identificatieClient = identificatieClient;
        this.telefoonnummerService = telefoonnummerService;
    }

    @Override
    public nl.lakedigital.djfc.domain.Telefoonnummer apply(AbstracteEntiteitMetSoortEnId abstracteEntiteitMetSoortEnId) {
        Telefoonnummer tel = (Telefoonnummer) abstracteEntiteitMetSoortEnId;

        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(tel.getIdentificatie());

        nl.lakedigital.djfc.domain.Telefoonnummer telefoonnummer;
        if (identificatie != null && identificatie.getEntiteitId() != null) {
            telefoonnummer = telefoonnummerService.lees(identificatie.getEntiteitId());
        } else {
            telefoonnummer = new nl.lakedigital.djfc.domain.Telefoonnummer();
        }

        telefoonnummer.setOmschrijving(tel.getOmschrijving());
        telefoonnummer.setSoort(TelefoonnummerSoort.valueOf(tel.getSoort()));
        telefoonnummer.setTelefoonnummer(tel.getTelefoonnummer());
        telefoonnummer.setSoortEntiteit(SoortEntiteit.valueOf(tel.getSoortEntiteit().name()));
        telefoonnummer.setEntiteitId(tel.getEntiteitId());

        return telefoonnummer;
    }
}
