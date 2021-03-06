package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.commons.xml.OpvragenOpmerkingenResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpmerkingClient extends AbstractOgaClient<JsonOpmerking, OpvragenOpmerkingenResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpmerkingClient.class);

    private final String URL_LIJST = "/rest/opmerking/alles";
    private final String URL_VERWIJDEREN = "/rest/opmerking/verwijderen";
    private final String URL_VERWIJDER = "/rest/opmerking/verwijder";
    private final String URL_ZOEKEN = "/rest/opmerking/zoeken";

    private MetricsService metricsService;

    public OpmerkingClient(String basisUrl) {
        super(basisUrl);
    }

    public OpmerkingClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonOpmerking>>() {
        }.getType();
    }

    @Override
    public List<JsonOpmerking> zoeken(String zoekterm) {
        List<JsonOpmerking> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenOpmerkingenResponse.class, LOGGER, metricsService, "zoeken", OpmerkingClient.class, zoekterm).getOpmerkingen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }

        return result;
    }

    @Override
    public List<JsonOpmerking> lijst(String soortEntiteit, Long entiteitId) {
        return lijst(soortEntiteit, entiteitId, false);
    }

    public List<JsonOpmerking> lijst(String soortEntiteit, Long entiteitId, boolean retry) {
        List<JsonOpmerking> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenOpmerkingenResponse.class, LOGGER, metricsService, "lijst", OpmerkingClient.class, soortEntiteit, String.valueOf(entiteitId)).getOpmerkingen();
        } catch (IOException e) {
            if (!retry) {
                return lijst(soortEntiteit, entiteitId, true);
            } else {
                throw new LeesFoutException("Fout bij lezen " + URL_LIJST + "/" + soortEntiteit + "/" + entiteitId, e);
            }
        }

        if (result.isEmpty() && !retry) {
            return lijst(soortEntiteit, entiteitId, true);
        } else {
            return result;
        }
    }

    @Override
    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }

    public void verwijder(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_VERWIJDER, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }
}
