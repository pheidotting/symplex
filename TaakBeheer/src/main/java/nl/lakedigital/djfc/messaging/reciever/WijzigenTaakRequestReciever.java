package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.taak.WijzigenTaakRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.Taak;
import nl.lakedigital.djfc.domain.TaakStatus;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class WijzigenTaakRequestReciever extends AbstractReciever<WijzigenTaakRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WijzigenTaakRequestReciever.class);

    @Inject
    private TaakService taakService;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;

    public WijzigenTaakRequestReciever() {
        super(WijzigenTaakRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(WijzigenTaakRequest wijzigenTaakRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", WijzigenTaakRequestReciever.class);

        LOGGER.info("Verwerken WijzigenTaakRequest");
        Identificatie identificatie = identificatieClient.zoekIdentificatieCode(wijzigenTaakRequest.getIdentificatie());
        Taak taak = taakService.lees(identificatie.getEntiteitId());
        taakService.wijzig(taak, TaakStatus.valueOf(wijzigenTaakRequest.getTaakStatus()), wijzigenTaakRequest.getToegewezenAan());

        metricsService.stop(context);
    }
}
