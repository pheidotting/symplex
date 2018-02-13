package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.commons.xml.OpvragenTelefoonnummersResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TelefoonnummerClient extends AbstractOgaClient<JsonTelefoonnummer, OpvragenTelefoonnummersResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelefoonnummerClient.class);

    private final String URL_LIJST = "/rest/telefoonnummer/alles";
    private final String URL_OPSLAAN = "/rest/telefoonnummer/opslaan";
    private final String URL_VERWIJDEREN = "/rest/telefoonnummer/verwijderen";
    private final String URL_ZOEKEN = "/rest/telefoonnummer/zoeken";

    private MetricsService metricsService;

    public TelefoonnummerClient(String basisUrl) {
        super(basisUrl);
    }

    public TelefoonnummerClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonTelefoonnummer>>() {
        }.getType();
    }

    @Override
    public List<JsonTelefoonnummer> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        List<JsonTelefoonnummer> result;

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenTelefoonnummersResponse.class, LOGGER, metricsService, "zoeken", TelefoonnummerClient.class, zoekterm).getTelefoonnummers();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }

        return result;
    }

    @Override
    public List<JsonTelefoonnummer> lijst(String soortEntiteit, Long entiteitId) {
        return lijst(soortEntiteit, entiteitId, false);
    }

    public List<JsonTelefoonnummer> lijst(String soortEntiteit, Long entiteitId, boolean retry) {
        System.out.println("Aanroepen " + URL_LIJST);

        List<JsonTelefoonnummer> result;

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenTelefoonnummersResponse.class, LOGGER, metricsService, "lijst", TelefoonnummerClient.class, soortEntiteit, String.valueOf(entiteitId)).getTelefoonnummers();
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

        System.out.println("Aanroepen " + URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }
}
