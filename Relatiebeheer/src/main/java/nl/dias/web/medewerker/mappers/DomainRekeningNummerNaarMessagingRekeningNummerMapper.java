package nl.dias.web.medewerker.mappers;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.response.RekeningNummer;

import java.util.function.Function;

public class DomainRekeningNummerNaarMessagingRekeningNummerMapper implements Function<RekeningNummer, nl.lakedigital.djfc.commons.domain.RekeningNummer> {
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;

    public DomainRekeningNummerNaarMessagingRekeningNummerMapper(Long entiteitId, SoortEntiteit soortEntiteit) {
        this.entiteitId = entiteitId;
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public nl.lakedigital.djfc.commons.domain.RekeningNummer apply(RekeningNummer rekeningNummer) {
        nl.lakedigital.djfc.commons.domain.RekeningNummer jsonRekeningNummer = new nl.lakedigital.djfc.commons.domain.RekeningNummer();

        jsonRekeningNummer.setBic(rekeningNummer.getBic());
        jsonRekeningNummer.setRekeningnummer(rekeningNummer.getRekeningnummer());
        jsonRekeningNummer.setEntiteitId(entiteitId);
        jsonRekeningNummer.setSoortEntiteit(soortEntiteit);
        jsonRekeningNummer.setIdentificatie(rekeningNummer.getIdentificatie());

        return jsonRekeningNummer;
    }
}
