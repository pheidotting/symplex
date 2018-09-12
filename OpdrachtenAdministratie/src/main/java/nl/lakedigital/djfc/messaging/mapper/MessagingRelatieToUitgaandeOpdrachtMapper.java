package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.as.messaging.entities.Relatie;
import nl.lakedigital.as.messaging.request.relatie.OpslaanRelatieRequest;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;

import java.util.function.Function;

public class MessagingRelatieToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanRelatieRequest> implements Function<Relatie, UitgaandeOpdracht> {
    public MessagingRelatieToUitgaandeOpdrachtMapper() {
        super(OpslaanRelatieRequest.class);
    }

    @Override
    public UitgaandeOpdracht apply(Relatie relatie) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();

        OpslaanRelatieRequest opslaanRelatieRequest = new OpslaanRelatieRequest();
        opslaanRelatieRequest.setRelatie(relatie);

        uitgaandeOpdracht.setBericht(marshall(opslaanRelatieRequest));

        return uitgaandeOpdracht;
    }

}
