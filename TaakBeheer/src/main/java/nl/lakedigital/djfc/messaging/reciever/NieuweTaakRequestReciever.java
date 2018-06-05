package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.taak.NieuweTaakRequest;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.messaging.sender.NieuweTaakResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class NieuweTaakRequestReciever extends AbstractReciever<NieuweTaakRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NieuweTaakRequestReciever.class);

    @Inject
    private TaakService taakService;
    @Inject
    private NieuweTaakResponseSender nieuweTaakResponseSender;
    @Inject
    private MetricsService metricsService;

    public NieuweTaakRequestReciever() {
        super(NieuweTaakRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(NieuweTaakRequest nieuweTaakRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", NieuweTaakRequestReciever.class);

        LOGGER.info("Verwerken NieuweTaakRequest");
        Long taakId = taakService.nieuweTaak(nieuweTaakRequest.getDeadline(), nieuweTaakRequest.getTitel(), nieuweTaakRequest.getOmschrijving(), nieuweTaakRequest.getEntiteitId(), SoortEntiteit.valueOf(nieuweTaakRequest.getSoortEntiteit().name()), nieuweTaakRequest.getToegewezenAan());

        nieuweTaakResponseSender.send(taakId);

        metricsService.stop(context);
    }
}
