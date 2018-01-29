package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.xml.OpvragenAdressenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;
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

    public AdresClient(String basisUrl) {
        super(basisUrl);
    }

    public AdresClient() {
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
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenAdressenResponse.class, LOGGER, zoekterm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }

        return result;
    }

    public JsonAdres lees(Long id) {

        LOGGER.debug("Aanroepen {}", URL_LEES);

        OpvragenAdressenResponse opvragenAdressenResponse;
        try {
            opvragenAdressenResponse = getXMLVoorLijstOGA(basisUrl + URL_LEES, OpvragenAdressenResponse.class, LOGGER, String.valueOf(id));
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }
        return opvragenAdressenResponse.getAdressen().get(0);
    }

    public List<JsonAdres> lijst(String soortEntiteit, Long entiteitId) {
        LOGGER.debug("Aanroepen {}", URL_LIJST + "/" + soortEntiteit + "/" + entiteitId);

        List<JsonAdres> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenAdressenResponse.class, LOGGER, soortEntiteit, String.valueOf(entiteitId)).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_LIJST + "/" + soortEntiteit + "/" + entiteitId, e);
        }

        return result;
    }

    public void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId) {
        LOGGER.debug("Aanroepen {}", URL_VERWIJDEREN);

        aanroepenUrlPostZonderBody(URL_VERWIJDEREN, ingelogdeGebruiker, trackAndTraceId, soortEntiteit, entiteitId.toString());
    }

    public JsonAdres ophalenAdresOpPostcode(String postcode, String huisnummer, boolean toggle) {
        String toggleString = toggle ? "true" : "false";

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(basisUrl + URL_ADRES_BIJ_POSTCODE, OpvragenAdressenResponse.class, LOGGER, postcode, huisnummer, toggleString).getAdressen();
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
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }

    public List<JsonAdres> zoekOpAdres( String zoekTerm) {
        String url = basisUrl + URL_ZOEK_OP_ADRES;

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER, zoekTerm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }
    public List<JsonAdres> zoekOpPostcode( String zoekTerm) {
        String url = basisUrl + URL_ZOEK_OP_POSTCODE;

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER, zoekTerm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }
    public List<JsonAdres> zoekOpPlaats( String zoekTerm) {
        String url = basisUrl + URL_ZOEK_OP_PLAATS;

        List<JsonAdres> result;

        try {
            result = getXMLVoorLijstOGAZonderEncode(url, OpvragenAdressenResponse.class, LOGGER, zoekTerm).getAdressen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + url, e);
        }

        return result;
    }
}
