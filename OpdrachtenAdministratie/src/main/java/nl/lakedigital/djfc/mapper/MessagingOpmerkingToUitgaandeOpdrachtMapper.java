package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class MessagingOpmerkingToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanEntiteitenRequest> implements Consumer<Opmerking> {
    private final static Logger LOGGER = LoggerFactory.getLogger(MessagingOpmerkingToUitgaandeOpdrachtMapper.class);
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;
    private SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId;
    private OpslaanEntiteitenRequest opslaanEntiteitenRequest;

    public MessagingOpmerkingToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp, SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
        this.soortEntiteitEnEntiteitId = soortEntiteitEnEntiteitId;

        opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();
        setMDC(opslaanEntiteitenRequest);
        opslaanEntiteitenRequest.setEntiteitId(this.soortEntiteitEnEntiteitId.getEntiteitId());
        opslaanEntiteitenRequest.setSoortEntiteit(this.soortEntiteitEnEntiteitId.getSoortEntiteit());
    }

    public UitgaandeOpdracht finish() {
        UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
        uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.OPMERKING);
        opslaanEntiteitenRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());

        uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
        uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

        return uitgaandeOpdracht;

    }

    @Override
    public void accept(Opmerking opmerking) {
        opslaanEntiteitenRequest.getLijst().add(new Opmerking(this.soortEntiteitEnEntiteitId.getSoortEntiteit(), this.soortEntiteitEnEntiteitId.getEntiteitId(), null, opmerking.getTijd(), opmerking.getOpmerking(), null, opmerking.getMedewerkerId()));
    }
}
