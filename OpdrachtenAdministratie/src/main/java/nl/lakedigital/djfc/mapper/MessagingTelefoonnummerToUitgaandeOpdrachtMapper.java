package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.Telefoonnummer;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

import java.util.function.Function;

public class MessagingTelefoonnummerToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanEntiteitenRequest> implements Function<Telefoonnummer, UitgaandeOpdracht> {
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;

    public MessagingTelefoonnummerToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
    }

    @Override
    public UitgaandeOpdracht apply(Telefoonnummer telefoonnummer) {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.TELEFOONNUMMER);

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();
        setMDC(opslaanEntiteitenRequest);

        opslaanEntiteitenRequest.getLijst().add(new Telefoonnummer(SoortEntiteit.RELATIE, 1L, null, telefoonnummer.getTelefoonnummer(), telefoonnummer.getSoort(), telefoonnummer.getOmschrijving(), null));

        uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;
    }
}
