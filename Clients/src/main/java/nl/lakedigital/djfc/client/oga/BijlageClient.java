package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.json.WijzigenOmschrijvingBijlage;
import nl.lakedigital.djfc.commons.xml.OpvragenBijlagesResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BijlageClient extends AbstractOgaClient<JsonBijlage, OpvragenBijlagesResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijlageClient.class);

    private final String URL_LIJST = "/rest/bijlage/alles";
    private final String URL_LEES = "/rest/bijlage/lees";
    private final String URL_OPSLAAN = "/rest/bijlage/opslaan";
    private final String URL_VERWIJDEREN = "/rest/bijlage/verwijderen";
    private final String URL_VERWIJDER = "/rest/bijlage/verwijder";
    private final String URL_ZOEKEN = "/rest/bijlage/zoeken";
    private final String URL_BESTANDSNAAM = "/rest/bijlage/genereerBestandsnaam";
    private final String URL_UPLOADPAD = "/rest/bijlage/getUploadPad";
    private final String URL_WIJZIG_OMSCHRIJVING_BIJLAGE = "/rest/bijlage/wijzigOmschrijvingBijlage";

    private MetricsService metricsService;

    public BijlageClient(String basisUrl) {
        super(basisUrl);
    }

    public BijlageClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonBijlage>>() {
        }.getType();
    }

    public JsonBijlage lees(Long id) {
        LOGGER.debug("Aanroepen {}", basisUrl + URL_LEES);

        List<JsonBijlage> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(basisUrl + URL_LEES, OpvragenBijlagesResponse.class, LOGGER, metricsService, "lees", BijlageClient.class, String.valueOf(id)).getBijlages();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + basisUrl + URL_LEES, e);
        }

        return result.get(0);
    }

    @Override
    public List<JsonBijlage> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        List<JsonBijlage> result;

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenBijlagesResponse.class, LOGGER, metricsService, "zoeken", BijlageClient.class, zoekterm).getBijlages();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }

        return result;
    }

    @Override
    public List<JsonBijlage> lijst(String soortEntiteit, Long entiteitId) {
        System.out.println("Aanroepen " + URL_LIJST);

        List<JsonBijlage> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(basisUrl + URL_LIJST, OpvragenBijlagesResponse.class, LOGGER, metricsService, "lijst", BijlageClient.class, soortEntiteit, String.valueOf(entiteitId)).getBijlages();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_LIJST, e);
        }

        return result;
    }

    @Deprecated
    public String opslaan(List<JsonBijlage> bijlages, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, bijlages, ingelogdeGebruiker, trackAndTraceId, LOGGER);
    }

    @Deprecated
    public String opslaan(JsonBijlage bijlage, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, bijlage, ingelogdeGebruiker, trackAndTraceId, LOGGER);
    }

    @Override
    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }

    public void verwijder(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_VERWIJDER);

        aanroepenUrlPostZonderBody(URL_VERWIJDER, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }

    public String genereerBestandsnaam() {
        return (String) uitvoerenGet(URL_BESTANDSNAAM, String.class, LOGGER);
    }

    public String getUploadPad() {
        return (String) uitvoerenGet(URL_UPLOADPAD, String.class, LOGGER);
    }

    public void wijzigOmschrijvingBijlage(WijzigenOmschrijvingBijlage wijzigenOmschrijvingBijlage, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPost(URL_WIJZIG_OMSCHRIJVING_BIJLAGE, wijzigenOmschrijvingBijlage, ingelogdeGebruiker, trackAndTraceId, LOGGER);
    }
}
