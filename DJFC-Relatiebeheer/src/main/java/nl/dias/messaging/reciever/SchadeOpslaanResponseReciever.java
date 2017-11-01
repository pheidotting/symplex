package nl.dias.messaging.reciever;

import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.as.messaging.request.SchadeOpslaanRequest;
import nl.lakedigital.as.messaging.response.SchadeOpslaanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class SchadeOpslaanResponseReciever extends AbstractReciever<SchadeOpslaanResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeOpslaanResponseReciever.class);

    public SchadeOpslaanResponseReciever() {
        super(SchadeOpslaanResponse.class, LOGGER);
    }

    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;

    @Override
    public void verwerkMessage(SchadeOpslaanResponse schadeOpslaanResponse) {
        SchadeOpslaanRequest schadeOpslaanRequest = (SchadeOpslaanRequest) schadeOpslaanResponse.getAntwoordOp();

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        schadeOpslaanRequest.getSchades().stream().forEach(schade -> {
            if (!schade.getOpmerkingen().isEmpty()) {
                schade.getOpmerkingen().stream().forEach(opmerking -> {
                    opmerking.setEntiteitId(schade.getId());
                    opmerking.setSoortEntiteit(SoortEntiteit.SCHADE);

                    opslaanEntiteitenRequest.getLijst().add(opmerking);
                });
            }
        });

        if (!opslaanEntiteitenRequest.getLijst().isEmpty()) {
            opslaanEntiteitenRequestSender.send(opslaanEntiteitenRequest);
        }
    }
}
