package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.PolisVerwijderenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PolisVerwijderRequestMapper extends AbstractMapper<PolisVerwijderenRequest> {
    public PolisVerwijderRequestMapper() {
        super(PolisVerwijderenRequest.class);
    }

    public List<UitgaandeOpdracht> map(Long id) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.PAKKET);

        PolisVerwijderenRequest polisOpslaanRequest = new PolisVerwijderenRequest();
        polisOpslaanRequest.getIds().add(id);
        polisOpslaanRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());
        setMDC(polisOpslaanRequest, uitgaandeOpdracht);

        uitgaandeOpdracht.setBericht(marshall(polisOpslaanRequest));

        return newArrayList(uitgaandeOpdracht, new EntiteitenVerwijderdMapper().maakVerwijderEntiteitenRequest(uitgaandeOpdracht, id));
    }
}
