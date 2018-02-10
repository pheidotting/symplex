package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.commons.xml.OpvragenRekeningNummersResponse;
import nl.lakedigital.djfc.interfaces.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class RekeningClient extends AbstractOgaClient<JsonRekeningNummer, OpvragenRekeningNummersResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(RekeningClient.class);

    private final String URL_LIJST = "/rest/rekeningnummer/alles";
    private final String URL_OPSLAAN = "/rest/rekeningnummer/opslaan";
    private final String URL_VERWIJDEREN = "/rest/rekeningnummer/verwijderen";
    private final String URL_ZOEKEN = "/rest/rekeningnummer/zoeken";

    private Metrics metrics;

    public RekeningClient(String basisUrl) {
        super(basisUrl);
    }

    public RekeningClient() {
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonRekeningNummer>>() {
        }.getType();
    }

    @Override
    public List<JsonRekeningNummer> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        List<JsonRekeningNummer> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenRekeningNummersResponse.class, LOGGER, metrics, "zoeken", RekeningClient.class, zoekterm).getRekeningNummers();
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
        System.out.println("Aanroepen " + URL_LIJST);

        List<JsonRekeningNummer> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenRekeningNummersResponse.class, LOGGER, metrics, "lijst", RekeningClient.class, soortEntiteit, String.valueOf(entiteitId)).getRekeningNummers();
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
