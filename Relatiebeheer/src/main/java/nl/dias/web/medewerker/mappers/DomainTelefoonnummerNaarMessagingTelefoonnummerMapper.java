package nl.dias.web.medewerker.mappers;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.response.Telefoonnummer;

import java.util.function.Function;

public class DomainTelefoonnummerNaarMessagingTelefoonnummerMapper implements Function<Telefoonnummer, nl.lakedigital.djfc.commons.domain.Telefoonnummer> {
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;

    public DomainTelefoonnummerNaarMessagingTelefoonnummerMapper(Long entiteitId, SoortEntiteit soortEntiteit) {
        this.entiteitId = entiteitId;
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public nl.lakedigital.djfc.commons.domain.Telefoonnummer apply(Telefoonnummer telefoonnummer) {
        nl.lakedigital.djfc.commons.domain.Telefoonnummer jsonTelefoonnummer = new nl.lakedigital.djfc.commons.domain.Telefoonnummer();

        jsonTelefoonnummer.setOmschrijving(telefoonnummer.getOmschrijving());
        jsonTelefoonnummer.setSoort(telefoonnummer.getSoort());
        jsonTelefoonnummer.setTelefoonnummer(telefoonnummer.getTelefoonnummer());
        jsonTelefoonnummer.setEntiteitId(entiteitId);
        jsonTelefoonnummer.setSoortEntiteit(SoortEntiteit.valueOf(soortEntiteit.name()));
        jsonTelefoonnummer.setIdentificatie(telefoonnummer.getIdentificatie());

        return jsonTelefoonnummer;
    }
}
