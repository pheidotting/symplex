package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonPakket;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.xml.OpvragenPolisSoortenResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenPolissenResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PolisClient extends AbstractClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisClient.class);

    private final String URL_ALLE_PARTICULIERE_POLIS_SOORTEN = "/rest/polis/alleParticulierePolisSoorten";
    private final String URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN = "/rest/polis/alleZakelijkePolisSoorten";
    private final String URL_LEES = "/rest/polis/lees";
    private final String URL_LIJST = "/rest/polis/lijst";
    private final String URL_LIJST_BEDRIJF = "/rest/polis/lijstBijBedrijf";
    private final String URL_ZOEK_OP_POLISNUMMER = "/rest/polis/zoekOpPolisNummer";

    private MetricsService metricsService;

    public PolisClient(String basisUrl) {
        super(basisUrl);
    }

    public PolisClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    public Map<String, String> alleParticulierePolisSoorten() {
        return ((OpvragenPolisSoortenResponse) getXML(URL_ALLE_PARTICULIERE_POLIS_SOORTEN, OpvragenPolisSoortenResponse.class, false, LOGGER, false, metricsService, "alleParticulierePolisSoorten", PolisClient.class)).getPolisSoorten();
    }

    public Map<String, String> alleZakelijkePolisSoorten() {
        return ((OpvragenPolisSoortenResponse) getXML(URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN, OpvragenPolisSoortenResponse.class, false, LOGGER, false, metricsService, "alleZakelijkePolisSoorten", PolisClient.class)).getPolisSoorten();
    }

    public JsonPakket lees(Long id, boolean opPolis) {
        JsonPakket polis = null;
        OpvragenPolissenResponse response = ((OpvragenPolissenResponse) getXML(URL_LEES, OpvragenPolissenResponse.class, false, LOGGER, false, metricsService, "lees", PolisClient.class, String.valueOf(id), opPolis ? "true" : "false"));
        if (!response.getPakketten().isEmpty()) {
            polis = response.getPakketten().get(0);
        }

        return polis;
    }

    public List<JsonPakket> lijst(String relatieId) {
        return ((OpvragenPolissenResponse) getXML(URL_LIJST, OpvragenPolissenResponse.class, false, LOGGER, false, metricsService, "lijst", PolisClient.class, relatieId)).getPakketten();
    }

    public List<JsonPakket> lijstBijBedrijf(Long bedrijfId) {
        return ((OpvragenPolissenResponse) getXML(URL_LIJST_BEDRIJF, OpvragenPolissenResponse.class, false, LOGGER, false, metricsService, "lijstBijBedrijf", PolisClient.class, String.valueOf(bedrijfId))).getPakketten();
    }

    public List<JsonPakket> zoekOpPolisNummer(String polisNummer) {
        OpvragenPolissenResponse response = ((OpvragenPolissenResponse) getXML(URL_ZOEK_OP_POLISNUMMER, OpvragenPolissenResponse.class, false, LOGGER, false, metricsService, "zoekOpPolisNummer", PolisClient.class, polisNummer));

        return response.getPakketten();
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonPolis>>() {
        }.getType();
    }
}
