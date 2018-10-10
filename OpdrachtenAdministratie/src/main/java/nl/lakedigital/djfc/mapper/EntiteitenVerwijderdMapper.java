package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

public class EntiteitenVerwijderdMapper extends AbstractMapper<VerwijderEntiteitenRequest> {
    public EntiteitenVerwijderdMapper() {
        super(VerwijderEntiteitenRequest.class);
    }

    public UitgaandeOpdracht maakVerwijderEntiteitenRequest(UitgaandeOpdracht uitgaandeOpdrachtHoofd, Long id) {
        VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();
        setMDC(verwijderEntiteitenRequest);
        verwijderEntiteitenRequest.setSoortEntiteit(uitgaandeOpdrachtHoofd.getSoortEntiteit());
        verwijderEntiteitenRequest.setEntiteitId(id);

        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.ENTITEITVERWIJDERD);
        verwijderEntiteitenRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());

        uitgaandeOpdracht.setBericht(marshall(verwijderEntiteitenRequest));

        return uitgaandeOpdracht;
    }
}
