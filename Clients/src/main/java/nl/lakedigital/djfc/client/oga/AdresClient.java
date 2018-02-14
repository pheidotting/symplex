package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.xml.OpvragenAdressenResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class AdresClient extends AbstractOgaClient<JsonAdres, OpvragenAdressenResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdresClient.class);

    private final String URL_LIJST = "/rest/adres/alles";
    private final String URL_OPSLAAN = "/rest/adres/opslaan";
    private final String URL_VERWIJDEREN = "/rest/adres/verwijderen";
    private final String URL_LEES = "/rest/adres/lees";
    private final String URL_ADRES_BIJ_POSTCODE = "/rest/adres/ophalenAdresOpPostcode";
    private final String URL_ZOEKEN = "/rest/adres/zoeken";
    private final String URL_ALLES_BIJ_ENTITEITEN = "/rest/adres/alleAdressenBijLijstMetEntiteiten";

    private final String URL_ZOEK_OP_POSTCODE = "/rest/adres/zoekOpPostcode";
    private final String URL_ZOEK_OP_ADRES = "/rest/adres/zoekOpAdres";
    private final String URL_ZOEK_OP_PLAATS = "/rest/adres/zoekOpPlaats";

    private MetricsService metricsService;

    public AdresClient(String basisUrl) {
        super(basisUrl);
    }

    public AdresClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonAdres>>() {
        }.getType();
    }

    @Override
    public List<JsonAdres> zoeken(String zoekterm) {

        LOGGER.debug("Aanroepen {}", URL_ZOEKEN);

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenAdressenResponse.class, LOGGER, metricsService, "zoeken", AdresClient.class, zoekterm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }

        return result;
    }

    public JsonAdres lees(Long id) {

        LOGGER.debug("Aanroepen {}", URL_LEES);

        OpvragenAdressenResponse opvragenAdressenResponse;
        try {
            opvragenAdressenResponse = getXMLVoorLijstOGA(basisUrl + URL_LEES, OpvragenAdressenResponse.class, LOGGER, metricsService, "lees", AdresClient.class, String.valueOf(id));
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }
        return opvragenAdressenResponse.getAdressen().get(0);
    }

    public List<JsonAdres> lijst(String soortEntiteit, Long entiteitId) {
        return lijst(soortEntiteit, entiteitId, false);
    }

    public List<JsonAdres> lijst(String soortEntiteit, Long entiteitId, boolean retry) {
        LOGGER.debug("Aanroepen {}", URL_LIJST + "/" + soortEntiteit + "/" + entiteitId);

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenAdressenResponse.class, LOGGER, metricsService, "lijst", AdresClient.class, soortEntiteit, String.valueOf(entiteitId)).getAdressen();
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

    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {
        LOGGER.debug("Aanroepen {}", URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }

    public JsonAdres ophalenAdresOpPostcode(String postcode, String huisnummer, boolean toggle) {
        String toggleString = toggle ? "true" : "false";

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(basisUrl + URL_ADRES_BIJ_POSTCODE, OpvragenAdressenResponse.class, LOGGER, metricsService, "ophalenAdresOpPostcode", AdresClient.class, postcode, huisnummer, toggleString).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + basisUrl + URL_ADRES_BIJ_POSTCODE, e);
        }

        return result.isEmpty() ? new JsonAdres() : result.get(0);
    }

    public List<JsonAdres> alleAdressenBijLijstMetEntiteiten(List<Long> ids, String soortEntiteit) {
        String idsString = join("&lijst=", ids.stream().map(aLong -> String.valueOf(aLong)).collect(Collectors.toList()));

        String url = basisUrl + URL_ALLES_BIJ_ENTITEITEN + "?soortEntiteit=" + soortEntiteit + "&lijst=" + idsString;

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER, metricsService, "alleAdressenBijLijstMetEntiteiten", AdresClient.class).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }

    public List<JsonAdres> zoekOpAdres( String zoekTerm) {
        String url = basisUrl + URL_ZOEK_OP_ADRES;

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER, metricsService, "zoekOpAdres", AdresClient.class, zoekTerm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }
    public List<JsonAdres> zoekOpPostcode( String zoekTerm) {
        String url = basisUrl + URL_ZOEK_OP_POSTCODE;

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER, metricsService, "zoekOpPostcode", AdresClient.class, zoekTerm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }
    public List<JsonAdres> zoekOpPlaats( String zoekTerm) {
        String url = basisUrl + URL_ZOEK_OP_PLAATS;

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER, metricsService, "zoekOpPlaats", AdresClient.class, zoekTerm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }
}
