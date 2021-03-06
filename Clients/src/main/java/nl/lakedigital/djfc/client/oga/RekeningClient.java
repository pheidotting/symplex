package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.xml.OpvragenRekeningNummersResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class RekeningClient extends AbstractOgaClient<JsonRekeningNummer, OpvragenRekeningNummersResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RekeningClient.class);

    private final String URL_LIJST = "/rest/rekeningnummer/alles";
    private final String URL_VERWIJDEREN = "/rest/rekeningnummer/verwijderen";
    private final String URL_ZOEKEN = "/rest/rekeningnummer/zoeken";

    private MetricsService metricsService;

    public RekeningClient(String basisUrl) {
        super(basisUrl);
    }

    public RekeningClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonRekeningNummer>>() {
        }.getType();
    }

    @Override
    public List<JsonRekeningNummer> zoeken(String zoekterm) {
        List<JsonRekeningNummer> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenRekeningNummersResponse.class, LOGGER, metricsService, "zoeken", RekeningClient.class, zoekterm).getRekeningNummers();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }

        return result;
    }

    @Override
    public List<JsonRekeningNummer> lijst(String soortEntiteit, Long entiteitId) {
        return lijst(soortEntiteit, entiteitId, false);
    }

    public List<JsonRekeningNummer> lijst(String soortEntiteit, Long entiteitId, boolean retry) {
        List<JsonRekeningNummer> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenRekeningNummersResponse.class, LOGGER, metricsService, "lijst", RekeningClient.class, soortEntiteit, String.valueOf(entiteitId)).getRekeningNummers();
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
}
