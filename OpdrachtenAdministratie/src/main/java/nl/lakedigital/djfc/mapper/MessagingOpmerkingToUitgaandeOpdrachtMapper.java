package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

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
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.OPMERKING);

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        opslaanEntiteitenRequest.getLijst().add(new Opmerking(SoortEntiteit.RELATIE, 1L, null, opmerking.getTijd(), opmerking.getOpmerking(), null, opmerking.getMedewerkerId()));

        uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;
    }
}
