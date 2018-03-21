package nl.lakedigital.djfc.client.licentie;

import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.Licentie;
import nl.lakedigital.djfc.commons.json.LicentieResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

public class LicentieClient extends AbstractClient<LicentieResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(LicentieClient.class);
    private MetricsService metricsService;

    public LicentieClient(String basisUrl) {
        super(basisUrl);
    }

    public LicentieClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return null;
    }

    public Licentie eindDatumLicentie(Long kantoorId) {
        if (metricsService != null) {
            metricsService.addMetric("zoekIdentificatie", LicentieClient.class, null, null);
        }
        List<Licentie> lijst = getXML("/rest/licentie/actievelicentie", LicentieResponse.class, false, LOGGER, false, metricsService, "eindDatumLicentie", LicentieClient.class, String.valueOf(kantoorId)).getLicenties();

        if (!lijst.isEmpty()) {
            return lijst.get(0);
        }
        return null;
    }
}
