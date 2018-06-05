package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.TaakStatus;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.messaging.sender.OpslaanTaakResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static com.google.common.collect.Lists.newArrayList;

public class OpslaanTaakRequestReciever extends AbstractReciever<OpslaanTaakRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanTaakRequestReciever.class);

    @Inject
    private TaakService taakService;
    @Inject
    private OpslaanTaakResponseSender nieuweTaakResponseSender;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Inject
    private MetricsService metricsService;
    @Inject
    private IdentificatieClient identificatieClient;

    public OpslaanTaakRequestReciever() {
        super(OpslaanTaakRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(OpslaanTaakRequest opslaanTaakRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", OpslaanTaakRequestReciever.class);

        LOGGER.info("Verwerken NieuweTaakRequest");

        if (opslaanTaakRequest.getIdentificatie() == null || "".equals(opslaanTaakRequest.getIdentificatie())) {
            Long taakId = taakService.nieuweTaak(opslaanTaakRequest.getDeadline(), opslaanTaakRequest.getTitel(), opslaanTaakRequest.getOmschrijving(), opslaanTaakRequest.getEntiteitId(), SoortEntiteit.valueOf(opslaanTaakRequest.getSoortEntiteit().name()), opslaanTaakRequest.getToegewezenAan());

            nieuweTaakResponseSender.send(taakId);
            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
            soortEntiteitEnEntiteitId.setEntiteitId(taakId);
            soortEntiteitEnEntiteitId.setSoortEntiteit(nl.lakedigital.as.messaging.domain.SoortEntiteit.TAAK);
            entiteitenOpgeslagenRequestSender.send(newArrayList(soortEntiteitEnEntiteitId));
        } else {
            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(opslaanTaakRequest.getIdentificatie());
            taakService.wijzig(taakService.lees(identificatie.getEntiteitId()), TaakStatus.valueOf(opslaanTaakRequest.getStatus()), opslaanTaakRequest.getToegewezenAan());
        }


        metricsService.stop(context);
    }
}
