package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

public class EntiteitenOpgeslagenMapper extends AbstractMapper<EntiteitenOpgeslagenRequest> {
    public EntiteitenOpgeslagenMapper() {
        super(EntiteitenOpgeslagenRequest.class);
    }

    public UitgaandeOpdracht maakEntiteitenOpgeslagenRequest(UitgaandeOpdracht uitgaandeOpdrachtHoofd) {
        EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();
        setMDC(entiteitenOpgeslagenRequest);
        entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().add(new SoortEntiteitEnEntiteitId(uitgaandeOpdrachtHoofd.getSoortEntiteit(), 0L));

        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.ENTITEITOPGESLAGEN);
        entiteitenOpgeslagenRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());

        uitgaandeOpdracht.setBericht(marshall(entiteitenOpgeslagenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtHoofd);

        return uitgaandeOpdracht;
    }
}
