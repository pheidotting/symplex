package nl.dias.messaging.reciever;

import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.lakedigital.as.messaging.domain.Opmerking;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.as.messaging.request.taak.WijzigingTaakOpslaanRequest;
import nl.lakedigital.as.messaging.request.taak.WijzigingTaakOpslaanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class WijzigingTaakOpslaanResponseReciever extends AbstractReciever<WijzigingTaakOpslaanResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WijzigingTaakOpslaanResponseReciever.class);

    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;

    public WijzigingTaakOpslaanResponseReciever() {
        super(WijzigingTaakOpslaanResponse.class);
    }

    @Override
    public void verwerkMessage(WijzigingTaakOpslaanResponse wijzigingTaakOpslaanResponse) {
        WijzigingTaakOpslaanRequest wijzigingTaakOpslaanRequest = (WijzigingTaakOpslaanRequest) wijzigingTaakOpslaanResponse.getAntwoordOp();

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        Opmerking opmerking = new Opmerking();
        opmerking.setEntiteitId(wijzigingTaakOpslaanResponse.getId());
        opmerking.setSoortEntiteit(SoortEntiteit.WIJZIGINGTAAK);
        opmerking.setTekst(wijzigingTaakOpslaanRequest.getOpmerking());
        opmerking.setMedewerker(wijzigingTaakOpslaanResponse.getIngelogdeGebruiker());
        opslaanEntiteitenRequest.getLijst().add(opmerking);

        opslaanEntiteitenRequest.setEntiteitId(wijzigingTaakOpslaanResponse.getId());
        opslaanEntiteitenRequest.setSoortEntiteit(SoortEntiteit.WIJZIGINGTAAK);

        opslaanEntiteitenRequestSender.send(opslaanEntiteitenRequest);

    }
}
