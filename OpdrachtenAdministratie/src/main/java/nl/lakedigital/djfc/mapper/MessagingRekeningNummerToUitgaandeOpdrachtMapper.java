package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.entities.RekeningNummer;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;

import java.util.function.Function;

public class MessagingRekeningNummerToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanEntiteitenRequest> implements Function<RekeningNummer, UitgaandeOpdracht> {
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;

    public MessagingRekeningNummerToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
    }

    @Override
    public UitgaandeOpdracht apply(RekeningNummer rekeningNummer) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        opslaanEntiteitenRequest.getLijst().add(new nl.lakedigital.as.messaging.domain.RekeningNummer(SoortEntiteit.RELATIE, 1L, null, rekeningNummer.getBic(), rekeningNummer.getRekeningnummer()));

        uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;
    }
}
