package nl.lakedigital.djfc.client.opdrachtenadministratie;

import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.xml.OpvragenOpdrachtenStatusResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

public class OpdrachtenClient extends AbstractClient<OpvragenOpdrachtenStatusResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificatieClient.class);
    private MetricsService metricsService;

    public OpdrachtenClient(String basisUrl) {
        super(basisUrl);
    }

    public OpdrachtenClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return null;
    }

    public boolean isOpdrachtKlaar(String trackAndTraceId) {
        if (metricsService != null) {
            metricsService.addMetric("zoekIdentificatieCode", OpdrachtenClient.class, null, null);
        }
        OpvragenOpdrachtenStatusResponse response = getXML("/rest/opdracht/status/", OpvragenOpdrachtenStatusResponse.class, false, LOGGER, false, metricsService, "isOpdrachtKlaar", OpdrachtenClient.class, trackAndTraceId);

        return response.getStatus() == OpvragenOpdrachtenStatusResponse.Status.KLAAR;
    }
}
