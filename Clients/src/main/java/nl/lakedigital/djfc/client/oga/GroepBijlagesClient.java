package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.JsonGroepBijlages;
import nl.lakedigital.djfc.commons.xml.OpvragenGroepBijlagesResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GroepBijlagesClient extends AbstractOgaClient<JsonBijlage, OpvragenGroepBijlagesResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(GroepBijlagesClient.class);

    private final String URL_LIJST_GROEPEN = "/rest/bijlage/alleGroepen";
    private final String URL_OPSLAAN_GROEP = "/rest/bijlage/opslaanGroep";

    private MetricsService metricsService;

    public GroepBijlagesClient(String basisUrl) {
        super(basisUrl);
    }

    public GroepBijlagesClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonGroepBijlages>>() {
        }.getType();
    }

    public List<JsonGroepBijlages> lijstGroepen(String soortEntiteit, Long entiteitId) {
        String url = basisUrl + URL_LIJST_GROEPEN;

        List<JsonGroepBijlages> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenGroepBijlagesResponse.class, LOGGER, metricsService, "lijstGroepen", GroepBijlagesClient.class, soortEntiteit, String.valueOf(entiteitId)).getBijlages();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }

    @Deprecated
    public String opslaan(JsonGroepBijlages groepBijlages, Long ingelogdeGebruiker, String trackAndTraceId) {
        return aanroepenUrlPost(URL_OPSLAAN_GROEP, groepBijlages, ingelogdeGebruiker, trackAndTraceId, LOGGER);
    }

    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {

    }

    @Override
    public List<JsonBijlage> zoeken(String zoekterm) {
        return null;
    }

    @Override
    public List<JsonBijlage> lijst(String soortEntiteit, Long entiteitId) {
        return null;
    }

}
