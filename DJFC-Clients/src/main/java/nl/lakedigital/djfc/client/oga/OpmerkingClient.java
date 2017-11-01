package nl.lakedigital.djfc.client.oga;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.commons.xml.OpvragenOpmerkingenResponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpmerkingClient extends AbstractOgaClient<JsonOpmerking, OpvragenOpmerkingenResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpmerkingClient.class);

    private final String URL_LIJST = "/rest/opmerking/alles";
    private final String URL_OPSLAAN = "/rest/opmerking/opslaan";
    private final String URL_VERWIJDEREN = "/rest/opmerking/verwijderen";
    private final String URL_VERWIJDER = "/rest/opmerking/verwijder";
    private final String URL_ZOEKEN = "/rest/opmerking/zoeken";

    public OpmerkingClient(String basisUrl) {
        super(basisUrl);
    }

    public OpmerkingClient() {
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonOpmerking>>() {
        }.getType();
    }

    @Override
    public List<JsonOpmerking> zoeken(String zoekterm) {

        System.out.println("Aanroepen " + URL_ZOEKEN);

        List<JsonOpmerking> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_ZOEKEN, OpvragenOpmerkingenResponse.class, LOGGER, zoekterm).getOpmerkingen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_ZOEKEN, e);
        }

        return result;
    }

    @Override
    public List<JsonOpmerking> lijst(String soortEntiteit, Long entiteitId) {
        System.out.println("Aanroepen " + URL_LIJST);

        List<JsonOpmerking> result = newArrayList();

        try {
            result = getXMLVoorLijstOGA(basisUrl + URL_LIJST, OpvragenOpmerkingenResponse.class, LOGGER, soortEntiteit, String.valueOf(entiteitId)).getOpmerkingen();
        } catch (IOException e) {
            throw new LeesFoutException("Fout bij lezen " + URL_LIJST, e);
        }

        return result;
    }

    @Override
    public String opslaan(List<JsonOpmerking> jsonAdressen, Long ingelogdeGebruiker, String trackAndTraceId) {

        System.out.println("Aanroepen " + URL_OPSLAAN);
        for (JsonOpmerking jsonOpmerking : jsonAdressen) {
            System.out.println(ReflectionToStringBuilder.toString(jsonOpmerking, ToStringStyle.SHORT_PREFIX_STYLE));
        }
        System.out.println("# Aanroepen " + URL_OPSLAAN);

        return aanroepenUrlPost(URL_OPSLAAN, jsonAdressen, ingelogdeGebruiker, trackAndTraceId, LOGGER);
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
}
