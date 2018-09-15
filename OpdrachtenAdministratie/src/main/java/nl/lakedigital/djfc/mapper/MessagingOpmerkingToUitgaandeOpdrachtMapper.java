package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.entities.Opmerking;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;

import java.util.function.Function;

public class MessagingOpmerkingToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanEntiteitenRequest> implements Function<Opmerking, UitgaandeOpdracht> {
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;

    public MessagingOpmerkingToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
    }

    @Override
    public UitgaandeOpdracht apply(Opmerking opmerking) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        opslaanEntiteitenRequest.getLijst().add(new nl.lakedigital.as.messaging.domain.Opmerking(SoortEntiteit.RELATIE, 1L, null, opmerking.getMedewerkerId(), opmerking.getTijd(), opmerking.getOpmerking()));

        uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;
    }
}
