package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.xml.OpvragenPolisSoortenResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenPolissenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PolisClient extends AbstractClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(PolisClient.class);

    private final String URL_ALLE_PARTICULIERE_POLIS_SOORTEN = "/rest/polis/alleParticulierePolisSoorten";
    private final String URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN = "/rest/polis/alleZakelijkePolisSoorten";
    private final String URL_LEES = "/rest/polis/lees";
    private final String URL_LIJST = "/rest/polis/lijst";
    private final String URL_LIJST_BEDRIJF = "/rest/polis/lijstBijBedrijf";
    private final String URL_ZOEK_OP_POLISNUMMER = "/rest/polis/zoekOpPolisNummer";

    public PolisClient(String basisUrl) {
        super(basisUrl);
    }

    public PolisClient() {
    }

    public List<String> alleParticulierePolisSoorten() {
        return ((OpvragenPolisSoortenResponse) getXML(URL_ALLE_PARTICULIERE_POLIS_SOORTEN, OpvragenPolisSoortenResponse.class, false, LOGGER)).getPolisSoorten();
    }

    public List<String> alleZakelijkePolisSoorten() {
        return ((OpvragenPolisSoortenResponse) getXML(URL_ALLE_PARTICULIERE_ZAKELIJKE_SOORTEN, OpvragenPolisSoortenResponse.class, false, LOGGER)).getPolisSoorten();
    }

    public JsonPolis lees(String id) {
        JsonPolis polis = null;
        OpvragenPolissenResponse response = ((OpvragenPolissenResponse) getXML(URL_LEES, OpvragenPolissenResponse.class, false, LOGGER, id));
        if (!response.getPolissen().isEmpty()) {
            polis = response.getPolissen().get(0);
        }

        return polis;
    }

    public List<JsonPolis> lijst(String relatieId) {
        return ((OpvragenPolissenResponse) getXML(URL_LIJST, OpvragenPolissenResponse.class, false, LOGGER, relatieId)).getPolissen();
    }

    public List<JsonPolis> lijstBijBedrijf(Long bedrijfId) {
        return ((OpvragenPolissenResponse) getXML(URL_LIJST_BEDRIJF, OpvragenPolissenResponse.class, false, LOGGER, String.valueOf(bedrijfId))).getPolissen();
    }

    public List<JsonPolis> zoekOpPolisNummer(String polisNummer) {
        OpvragenPolissenResponse response = ((OpvragenPolissenResponse) getXML(URL_ZOEK_OP_POLISNUMMER, OpvragenPolissenResponse.class, false, LOGGER, polisNummer));

        return response.getPolissen();
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonPolis>>() {
        }.getType();
    }
}
