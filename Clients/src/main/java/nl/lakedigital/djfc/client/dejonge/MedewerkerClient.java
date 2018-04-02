package nl.lakedigital.djfc.client.dejonge;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonMedewerker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MedewerkerClient extends AbstractClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedewerkerClient.class);

    private final String URL_LEES = basisUrl + "/rest/applicaties/medewerker/lees";

    public MedewerkerClient(String basisUrl) {
        super(basisUrl);
    }

    public MedewerkerClient() {
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonMedewerker>>() {
        }.getType();
    }

    public JsonMedewerker lees(Long id) {

        System.out.println("Aanroepen " + URL_LEES);

        return (JsonMedewerker) uitvoerenGet(URL_LEES, JsonMedewerker.class, LOGGER, id.toString());
    }
}
