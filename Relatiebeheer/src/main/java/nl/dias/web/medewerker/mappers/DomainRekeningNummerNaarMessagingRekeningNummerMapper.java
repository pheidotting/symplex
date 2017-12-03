package nl.dias.web.medewerker.mappers;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.response.RekeningNummer;

import java.util.function.Function;

public class DomainRekeningNummerNaarMessagingRekeningNummerMapper implements Function<RekeningNummer, nl.lakedigital.as.messaging.domain.RekeningNummer> {
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;

    public DomainRekeningNummerNaarMessagingRekeningNummerMapper(Long entiteitId, SoortEntiteit soortEntiteit) {
        this.entiteitId = entiteitId;
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public nl.lakedigital.as.messaging.domain.RekeningNummer apply(RekeningNummer rekeningNummer) {
        nl.lakedigital.as.messaging.domain.RekeningNummer jsonRekeningNummer = new nl.lakedigital.as.messaging.domain.RekeningNummer();

        jsonRekeningNummer.setBic(rekeningNummer.getBic());
        jsonRekeningNummer.setRekeningnummer(rekeningNummer.getRekeningnummer());
        jsonRekeningNummer.setEntiteitId(entiteitId);
        jsonRekeningNummer.setSoortEntiteit(soortEntiteit);
        jsonRekeningNummer.setIdentificatie(rekeningNummer.getIdentificatie());

        return jsonRekeningNummer;
    }
}
