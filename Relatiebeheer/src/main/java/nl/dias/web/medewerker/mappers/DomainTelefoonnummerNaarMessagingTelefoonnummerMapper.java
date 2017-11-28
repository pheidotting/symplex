package nl.dias.web.medewerker.mappers;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.response.Telefoonnummer;

import java.util.function.Function;

public class DomainTelefoonnummerNaarMessagingTelefoonnummerMapper implements Function<Telefoonnummer, nl.lakedigital.as.messaging.domain.Telefoonnummer> {
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;

    public DomainTelefoonnummerNaarMessagingTelefoonnummerMapper(Long entiteitId, SoortEntiteit soortEntiteit) {
        this.entiteitId = entiteitId;
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public nl.lakedigital.as.messaging.domain.Telefoonnummer apply(Telefoonnummer telefoonnummer) {
        nl.lakedigital.as.messaging.domain.Telefoonnummer jsonTelefoonnummer = new nl.lakedigital.as.messaging.domain.Telefoonnummer();

        jsonTelefoonnummer.setOmschrijving(telefoonnummer.getOmschrijving());
        jsonTelefoonnummer.setSoort(telefoonnummer.getSoort());
        jsonTelefoonnummer.setTelefoonnummer(telefoonnummer.getTelefoonnummer());
        jsonTelefoonnummer.setEntiteitId(entiteitId);
        jsonTelefoonnummer.setSoortEntiteit(soortEntiteit);
        jsonTelefoonnummer.setIdentificatie(telefoonnummer.getIdentificatie());

        return jsonTelefoonnummer;
    }
}
