package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.domain.SoortEntiteit;
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
    private MetricsService metricsService;

    public OpslaanTaakRequestReciever() {
        super(OpslaanTaakRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(OpslaanTaakRequest nieuweTaakRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", OpslaanTaakRequestReciever.class);

        LOGGER.info("Verwerken NieuweTaakRequest");
        Long taakId = taakService.nieuweTaak(nieuweTaakRequest.getDeadline(), nieuweTaakRequest.getTitel(), nieuweTaakRequest.getOmschrijving(), nieuweTaakRequest.getEntiteitId(), SoortEntiteit.valueOf(nieuweTaakRequest.getSoortEntiteit().name()), nieuweTaakRequest.getToegewezenAan());

        nieuweTaakResponseSender.send(taakId);

        metricsService.stop(context);
    }
}
