package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanPolisOpdracht;
import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.djfc.commons.domain.Pakket;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

import java.util.function.Function;

import static com.google.common.collect.Lists.newArrayList;

public class MessagingPolisToUitgaandeOpdrachtMapper extends AbstractMapper<PolisOpslaanRequest> implements Function<Pakket, UitgaandeOpdracht> {
    public MessagingPolisToUitgaandeOpdrachtMapper() {
        super(OpslaanPolisOpdracht.class);
    }

    @Override
    public UitgaandeOpdracht apply(Pakket pakket) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.POLIS);

        PolisOpslaanRequest polisOpslaanRequest = new PolisOpslaanRequest();
        polisOpslaanRequest.setPokketten(newArrayList(pakket));
        polisOpslaanRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());
        setMDC(polisOpslaanRequest);

        uitgaandeOpdracht.setBericht(marshall(polisOpslaanRequest));

        return uitgaandeOpdracht;
    }

}
