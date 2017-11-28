package nl.dias.web.medewerker.mappers;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.response.Opmerking;

import java.util.function.Function;

public class DomainOpmerkingNaarMessagingOpmerkingMapper implements Function<Opmerking, nl.lakedigital.as.messaging.domain.Opmerking> {
    private Long bedrijfId;

    public DomainOpmerkingNaarMessagingOpmerkingMapper(Long bedrijfId) {
        this.bedrijfId = bedrijfId;
    }

    @Override
    public nl.lakedigital.as.messaging.domain.Opmerking apply(Opmerking opmerking) {
        nl.lakedigital.as.messaging.domain.Opmerking jsonOpmerking = new nl.lakedigital.as.messaging.domain.Opmerking();

        jsonOpmerking.setTekst(opmerking.getOpmerking());
        jsonOpmerking.setEntiteitId(bedrijfId);
        jsonOpmerking.setSoortEntiteit(SoortEntiteit.BEDRIJF);
        jsonOpmerking.setMedewerker(opmerking.getMedewerkerId());
        jsonOpmerking.setIdentificatie(opmerking.getIdentificatie());
        jsonOpmerking.setTijdstip(opmerking.getTijd());

        return jsonOpmerking;
    }
}
