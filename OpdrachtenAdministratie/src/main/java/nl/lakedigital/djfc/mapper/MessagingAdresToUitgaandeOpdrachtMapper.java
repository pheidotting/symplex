package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.entities.Adres;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;

import java.util.function.Function;

public class MessagingAdresToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanEntiteitenRequest> implements Function<Adres, UitgaandeOpdracht> {
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;

    public MessagingAdresToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
    }

    @Override
    public UitgaandeOpdracht apply(Adres adres) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        opslaanEntiteitenRequest.getLijst().add(new nl.lakedigital.as.messaging.domain.Adres(SoortEntiteit.RELATIE, 1L, null, adres.getStraat(), adres.getHuisnummer(), adres.getToevoeging(), adres.getPostcode(), adres.getPlaats(), adres.getSoortAdres()));

        uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;
    }
}
