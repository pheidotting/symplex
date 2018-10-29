package nl.lakedigital.djfc.mapper;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MessagingOpmerkingToUitgaandeOpdrachtMapper extends AbstractMapper<OpslaanEntiteitenRequest> implements Consumer<Opmerking> {
    private UitgaandeOpdracht uitgaandeOpdrachtWachtenOp;
    private SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId;
    private OpslaanEntiteitenRequest opslaanEntiteitenRequest;

    public MessagingOpmerkingToUitgaandeOpdrachtMapper(UitgaandeOpdracht uitgaandeOpdrachtWachtenOp, SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
        super(OpslaanEntiteitenRequest.class);
        this.uitgaandeOpdrachtWachtenOp = uitgaandeOpdrachtWachtenOp;
        this.soortEntiteitEnEntiteitId = soortEntiteitEnEntiteitId;

        opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();
        opslaanEntiteitenRequest.setEntiteitId(this.soortEntiteitEnEntiteitId.getEntiteitId());
        opslaanEntiteitenRequest.setSoortEntiteit(this.soortEntiteitEnEntiteitId.getSoortEntiteit());
    }

    public List<UitgaandeOpdracht> finish() {
        List<UitgaandeOpdracht> uitgaandeOpdrachten = new ArrayList<>();
        if (!opslaanEntiteitenRequest.getLijst().isEmpty()) {
            UitgaandeOpdracht uitgaandeOpdracht = new UitgaandeOpdracht();
            uitgaandeOpdracht.setSoortEntiteit(SoortEntiteit.OPMERKING);
            opslaanEntiteitenRequest.setHoofdSoortEntiteit(uitgaandeOpdracht.getSoortEntiteit());
            setMDC(opslaanEntiteitenRequest, uitgaandeOpdracht);

            uitgaandeOpdracht.setBericht(marshall(opslaanEntiteitenRequest));
            uitgaandeOpdracht.setWachtenOp(uitgaandeOpdrachtWachtenOp);

            uitgaandeOpdrachten.add(uitgaandeOpdracht);

            opslaanEntiteitenRequest.getLijst().stream().forEach(abstracteEntiteitMetSoortEnId -> uitgaandeOpdrachten.add(new EntiteitenOpgeslagenMapper().maakEntiteitenOpgeslagenRequest(uitgaandeOpdracht)));
        }

        return uitgaandeOpdrachten;

    }

    @Override
    public void accept(Opmerking opmerking) {
        if (opmerking.getOpmerking() != null && !"".equals(opmerking.getOpmerking())) {
            opslaanEntiteitenRequest.getLijst().add(new Opmerking(this.soortEntiteitEnEntiteitId.getSoortEntiteit(), this.soortEntiteitEnEntiteitId.getEntiteitId(), null, opmerking.getTijd(), opmerking.getOpmerking(), null, opmerking.getMedewerkerId()));
        }
    }
}
