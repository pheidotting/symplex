package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonVerzekeringsMaatschappij;
import nl.lakedigital.djfc.commons.json.OpvragenJsonVerzekeringsMaatschappijenResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VerzekeringsMaatschappijClient extends AbstractClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerzekeringsMaatschappijClient.class);

    private final String URL_LIJST_VERZEKERINGSMAATSCHAPPIJEN = "/rest/verzekeringsmaatschappij/lijstVerzekeringsMaatschappijen";

    private MetricsService metricsService;

    public VerzekeringsMaatschappijClient(String basisUrl) {
        super(basisUrl);
    }

    public VerzekeringsMaatschappijClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    public List<JsonVerzekeringsMaatschappij> lijstVerzekeringsMaatschappijen() {
        return ((OpvragenJsonVerzekeringsMaatschappijenResponse) getXML(URL_LIJST_VERZEKERINGSMAATSCHAPPIJEN, OpvragenJsonVerzekeringsMaatschappijenResponse.class, false, LOGGER, false, metricsService, "alleParticulierePolisSoorten", VerzekeringsMaatschappijClient.class)).getJsonVerzekeringsMaatschappijs();
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonVerzekeringsMaatschappij>>() {
        }.getType();
    }
}
