package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.taak.WijzigingTaakOpslaanRequest;
import nl.lakedigital.as.messaging.request.taak.WijzigingTaakOpslaanResponse;
import nl.lakedigital.djfc.commons.domain.Taak;
import nl.lakedigital.djfc.commons.domain.TaakStatus;
import nl.lakedigital.djfc.messaging.sender.WijzigingTaakOpslaanResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class WijzigingTaakOpslaanRequestReciever extends AbstractReciever<WijzigingTaakOpslaanRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WijzigingTaakOpslaanRequestReciever.class);

    @Inject
    private TaakService taakService;
    @Inject
    private MetricsService metricsService;
    @Inject
    private WijzigingTaakOpslaanResponseSender wijzigingTaakOpslaanResponseSender;

    public WijzigingTaakOpslaanRequestReciever() {
        super(WijzigingTaakOpslaanRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(WijzigingTaakOpslaanRequest wijzigingTaakOpslaanRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", WijzigingTaakOpslaanRequestReciever.class);

        Taak taak = taakService.lees(wijzigingTaakOpslaanRequest.getTaak());
        Long wijzigingTaakId = taakService.wijzig(taak, TaakStatus.valueOf(wijzigingTaakOpslaanRequest.getTaakStatus()), wijzigingTaakOpslaanRequest.getToegewezenAan());

        WijzigingTaakOpslaanResponse wijzigingTaakOpslaanResponse = new WijzigingTaakOpslaanResponse();
        wijzigingTaakOpslaanResponse.setId(wijzigingTaakId);
        wijzigingTaakOpslaanResponse.setAntwoordOp(wijzigingTaakOpslaanRequest);

        wijzigingTaakOpslaanResponseSender.send(wijzigingTaakOpslaanResponse);

        metricsService.stop(context);
    }
}
