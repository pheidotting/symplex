package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanSchadeOpdracht;
import nl.lakedigital.as.messaging.request.SchadeOpslaanRequest;
import nl.lakedigital.djfc.commons.domain.Schade;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class MessagingSchadeToUitgaandeOpdrachtMapper extends AbstractMapper<SchadeOpslaanRequest> {
    public MessagingSchadeToUitgaandeOpdrachtMapper() {
        super(OpslaanSchadeOpdracht.class);
    }

    public List<UitgaandeOpdracht> map(Schade schade) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.SCHADE);

        SchadeOpslaanRequest schadeOpslaanRequest = new SchadeOpslaanRequest();
        schadeOpslaanRequest.setSchades(newArrayList(schade));
        schadeOpslaanRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());
        setMDC(schadeOpslaanRequest);

        uitgaandeOpdracht.setBericht(marshall(schadeOpslaanRequest));

        return newArrayList(uitgaandeOpdracht, new EntiteitenOpgeslagenMapper().maakEntiteitenOpgeslagenRequest(uitgaandeOpdracht));
    }

}
