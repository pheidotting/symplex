package nl.dias.web.medewerker.mappers;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.response.Opmerking;

import java.util.function.Function;

public class DomainOpmerkingNaarMessagingOpmerkingMapper implements Function<Opmerking, nl.lakedigital.djfc.commons.domain.Opmerking> {
    private Long entiteitId;
    private SoortEntiteit soortEntiteit;

    public DomainOpmerkingNaarMessagingOpmerkingMapper(Long entiteitId, SoortEntiteit soortEntiteit) {
        this.entiteitId = entiteitId;
        this.soortEntiteit = soortEntiteit;
    }

    @Override
    public nl.lakedigital.djfc.commons.domain.Opmerking apply(Opmerking opmerking) {
        nl.lakedigital.djfc.commons.domain.Opmerking jsonOpmerking = new nl.lakedigital.djfc.commons.domain.Opmerking();

        jsonOpmerking.setOpmerking(opmerking.getOpmerking());
        jsonOpmerking.setEntiteitId(entiteitId);
        jsonOpmerking.setSoortEntiteit(soortEntiteit);
        jsonOpmerking.setMedewerker(opmerking.getMedewerkerId().toString());
        jsonOpmerking.setIdentificatie(opmerking.getIdentificatie());
        jsonOpmerking.setTijd(opmerking.getTijd());

        return jsonOpmerking;
    }
}
