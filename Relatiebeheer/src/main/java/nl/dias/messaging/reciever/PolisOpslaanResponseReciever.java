package nl.dias.messaging.reciever;

import nl.dias.messaging.sender.OpslaanEntiteitenRequestSender;
import nl.dias.service.MetricsService;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class PolisOpslaanResponseReciever extends AbstractReciever<PolisOpslaanResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanResponseReciever.class);

    @Inject
    private OpslaanEntiteitenRequestSender opslaanEntiteitenRequestSender;

    @Inject
    private IdentificatieClient identificatieClient;

    @Inject
    private MetricsService metricsService;

    public PolisOpslaanResponseReciever() {
        super(PolisOpslaanResponse.class);
    }

    @Override
    public void verwerkMessage(PolisOpslaanResponse polisOpslaanResponse) {
        PolisOpslaanRequest polisOpslaanRequest = (PolisOpslaanRequest) polisOpslaanResponse.getAntwoordOp();

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();

        polisOpslaanRequest.getPolissen().stream().forEach(polis -> {

            LOGGER.debug("Opgeslagen polis : {}", ReflectionToStringBuilder.toString(polis));
            if (!polis.getOpmerkingen().isEmpty()) {
                LOGGER.debug("Opmerkingen aanwezig, opslaan dus..");
                polis.getOpmerkingen().stream().forEach(opmerking -> {
                    opmerking.setEntiteitId(polis.getId());
                    opmerking.setSoortEntiteit(SoortEntiteit.POLIS);

                    opslaanEntiteitenRequest.getLijst().add(opmerking);
                });
            }
        });

        if (!opslaanEntiteitenRequest.getLijst().isEmpty()) {
            opslaanEntiteitenRequestSender.send(opslaanEntiteitenRequest, LOGGER);
        }
    }
}
