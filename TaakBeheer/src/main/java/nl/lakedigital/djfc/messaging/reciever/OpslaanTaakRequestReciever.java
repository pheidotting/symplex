package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.TaakStatus;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.messaging.sender.OpslaanTaakResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

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

    public OpslaanTaakRequestReciever() {
        super(OpslaanTaakRequest.class, LOGGER);
    }

    @Override
    //    @Transactional
    public void verwerkMessage(OpslaanTaakRequest opslaanTaakRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", OpslaanTaakRequestReciever.class);

        LOGGER.info("Verwerken NieuweTaakRequest");

        if (opslaanTaakRequest.getId() == null) {
            Long taakId = taakService.nieuweTaak(opslaanTaakRequest.getDeadline(), opslaanTaakRequest.getTitel(), opslaanTaakRequest.getOmschrijving(), opslaanTaakRequest.getEntiteitId(), SoortEntiteit.valueOf(opslaanTaakRequest.getSoortEntiteit().name()), opslaanTaakRequest.getToegewezenAan());

            nieuweTaakResponseSender.send(taakId);
            //            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
            //            soortEntiteitEnEntiteitId.setEntiteitId(taakId);
            //            soortEntiteitEnEntiteitId.setSoortEntiteit(nl.lakedigital.as.messaging.domain.SoortEntiteit.TAAK);
            //            entiteitenOpgeslagenRequestSender.send(newArrayList(soortEntiteitEnEntiteitId));
        } else {
            taakService.wijzig(taakService.lees(opslaanTaakRequest.getId()), TaakStatus.valueOf(opslaanTaakRequest.getStatus()), opslaanTaakRequest.getToegewezenAan());
        }

        metricsService.stop(context);
    }
}
