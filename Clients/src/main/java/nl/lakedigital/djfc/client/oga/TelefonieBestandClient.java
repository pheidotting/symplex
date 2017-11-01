package nl.lakedigital.djfc.client.oga;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonCommunicatieProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.join;

public class TelefonieBestandClient extends AbstractClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(TelefonieBestandClient.class);

    private final String URL_ALLES = "/rest/telefonie/recordings";

    public TelefonieBestandClient(String basisUrl) {
        super(basisUrl);
    }

    public TelefonieBestandClient() {
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonCommunicatieProduct>>() {
        }.getType();
    }

    public Map<String, List<String>> getRecordingsAndVoicemails(List<String> telefoonnummers) {
        String nummers = "?telefoonnummers=" + join("&telefoonnummers=", telefoonnummers);

        String result = uitvoerenGetString(URL_ALLES + nummers, LOGGER);

        LOGGER.debug("result {}", result);

        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, List<String>>>() {
        }.getType();

        return gson.fromJson(result, stringStringMap);
    }
}
