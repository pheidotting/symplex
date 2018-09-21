package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.RekeningNummer;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

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
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.REKENINGNUMMER);

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        opslaanEntiteitenRequest.getLijst().add(new RekeningNummer(SoortEntiteit.RELATIE, 1L, null, rekeningNummer.getBic(), rekeningNummer.getRekeningnummer()));

        uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;
    }
}
