package nl.lakedigital.djfc.client.taak;

import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.client.licentie.LicentieClient;
import nl.lakedigital.djfc.commons.json.Taak;
import nl.lakedigital.djfc.commons.xml.OpvragenTakenResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;

public class TaakClient extends AbstractClient<OpvragenTakenResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LicentieClient.class);

    private final String URL_PING = "/rest/zabbix/checkDatabase";

    private MetricsService metricsService;

    public TaakClient(String basisUrl) {
        super(basisUrl);
    }

    public TaakClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return null;
    }

    public Taak lees(Long id) {
        if (metricsService != null) {
            metricsService.addMetric("lees", TaakClient.class, null, null);
        }
        List<Taak> lijst = getXML("/rest/taak/lees", OpvragenTakenResponse.class, false, LOGGER, false, metricsService, "lees", TaakClient.class, String.valueOf(id)).getTaken();

        return lijst.get(0);
    }


    public List<Taak> alles(String soortentiteit, Long parentid) {
        if (metricsService != null) {
            metricsService.addMetric("alles", TaakClient.class, null, null);
        }
        List<Taak> lijst = getXML("/rest/taak/alles", OpvragenTakenResponse.class, false, LOGGER, false, metricsService, "alles", TaakClient.class, soortentiteit, String.valueOf(parentid)).getTaken();

        return lijst;
    }

    public String ping() {
        return uitvoerenGetAlsString(URL_PING, LOGGER);
    }
}
