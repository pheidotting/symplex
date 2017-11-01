package nl.lakedigital.djfc.client.dejonge;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonRelatie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RelatieClient extends AbstractClient<Object> {
    private final static Logger LOGGER = LoggerFactory.getLogger(RelatieClient.class);

    private final String URL_LEES = basisUrl + "/rest/applicaties/relatie/lees";
    private final String URL_ZOEK_OP_EMAILADRES = basisUrl + "/rest/applicaties/relatie/zoekOpEmailadres";
    private final String URL_ZOEK_OP_NAAM = basisUrl + "/rest/applicaties/relatie/zoekOpNaam";

    public RelatieClient(String basisUrl) {
        super(basisUrl);
    }

    public RelatieClient() {
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonRelatie>>() {
        }.getType();
    }

    public JsonRelatie lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return uitvoerenGet(URL_LEES, JsonRelatie.class, LOGGER, id.toString());
    }

    public JsonRelatie zoekOpEmailadres(String emailadres) {
        System.out.println("Aanroepen " + URL_ZOEK_OP_EMAILADRES);

        return uitvoerenGet(URL_ZOEK_OP_EMAILADRES, JsonRelatie.class, LOGGER, emailadres, "dummy");
    }

    public List<JsonRelatie> zoekOpNaam(String naam) {
        System.out.println("Aanroepen " + URL_ZOEK_OP_NAAM);

        return uitvoerenGetLijst(URL_ZOEK_OP_NAAM, JsonRelatie.class, LOGGER, naam);
    }
}
