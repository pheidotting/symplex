package nl.dias.web.medewerker.mappers;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.response.Telefoonnummer;

import java.util.function.Function;

public class DomainTelefoonnummerNaarMessagingTelefoonnummerMapper implements Function<Telefoonnummer, nl.lakedigital.as.messaging.domain.Telefoonnummer> {
    private Long bedrijfId;

    public DomainTelefoonnummerNaarMessagingTelefoonnummerMapper(Long bedrijfId) {
        this.bedrijfId = bedrijfId;
    }

    @Override
    public nl.lakedigital.as.messaging.domain.Telefoonnummer apply(Telefoonnummer telefoonnummer) {
        nl.lakedigital.as.messaging.domain.Telefoonnummer jsonTelefoonnummer = new nl.lakedigital.as.messaging.domain.Telefoonnummer();

        jsonTelefoonnummer.setOmschrijving(telefoonnummer.getOmschrijving());
        jsonTelefoonnummer.setSoort(telefoonnummer.getSoort());
        jsonTelefoonnummer.setTelefoonnummer(telefoonnummer.getTelefoonnummer());
        jsonTelefoonnummer.setEntiteitId(bedrijfId);
        jsonTelefoonnummer.setSoortEntiteit(SoortEntiteit.BEDRIJF);
        jsonTelefoonnummer.setIdentificatie(telefoonnummer.getIdentificatie());

        return jsonTelefoonnummer;
    }
}
