package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.SchadeVerwijderenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SchadeVerwijderRequestMapper extends AbstractMapper<SchadeVerwijderenRequest> {
    public SchadeVerwijderRequestMapper() {
        super(SchadeVerwijderenRequest.class);
    }

    public List<UitgaandeOpdracht> map(Long id) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.SCHADE);

        SchadeVerwijderenRequest schadeVerwijderenRequest = new SchadeVerwijderenRequest();
        schadeVerwijderenRequest.getIds().add(id);
        schadeVerwijderenRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());
        setMDC(schadeVerwijderenRequest, uitgaandeOpdracht);

        uitgaandeOpdracht.setBericht(marshall(schadeVerwijderenRequest));

        return newArrayList(uitgaandeOpdracht, new EntiteitenVerwijderdMapper().maakVerwijderEntiteitenRequest(uitgaandeOpdracht, id));
    }
}
