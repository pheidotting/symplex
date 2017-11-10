package nl.lakedigital.djfc.client.communicatie;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.JsonCommunicatieProduct;
import nl.lakedigital.djfc.commons.json.OpslaanCommunicatieProduct;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommunicatieClient extends AbstractClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(CommunicatieClient.class);

    private final String URL_ALLES = basisUrl + "/rest/communicatieproduct/alles";
    private final String URL_OPSLAAN = basisUrl + "/rest/communicatieproduct/nieuw";
    private final String URL_VERSTUREN = basisUrl + "/rest/communicatieproduct/versturen/act";
    private final String URL_MARKEER_OM_TE_VERZENDEN = basisUrl + "/rest/communicatieproduct/markeerOmTeVerzenden";
    private final String URL_MARKEER_GELEZEN = basisUrl + "/rest/communicatieproduct/markeerAlsGelezen";
    private final String URL_LEES = basisUrl + "/rest/communicatieproduct/lees";

    public CommunicatieClient() {
    }

    public CommunicatieClient(String basisUrl) {
        super(basisUrl);
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonCommunicatieProduct>>() {
        }.getType();
    }

    public List<JsonCommunicatieProduct> alles(String soortentiteit, Long parentid) {
        return uitvoerenGetLijst(URL_ALLES, soortentiteit, parentid.toString());
    }

    public Long nieuwCommunicatieProduct(OpslaanCommunicatieProduct opslaanCommunicatieProduct, Long ingelogdeGebruiker, String trackAndTraceId) {
        LOGGER.debug("Opslaan {}", ReflectionToStringBuilder.toString(opslaanCommunicatieProduct, ToStringStyle.SHORT_PREFIX_STYLE));

        return Long.valueOf(aanroepenUrlPost(URL_OPSLAAN, opslaanCommunicatieProduct, ingelogdeGebruiker, trackAndTraceId, LOGGER));
    }

    public void versturen(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_VERSTUREN, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }

    public void markeerOmTeVerzenden(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_MARKEER_OM_TE_VERZENDEN, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }

    public void markeerAlsGelezen(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_MARKEER_GELEZEN, ingelogdeGebruiker, trackAndTraceId, id.toString());
    }

    public JsonCommunicatieProduct lees(Long id) {
        String adres = URL_LEES + "/" + id.toString();
        Gson gson = new GsonBuilder().registerTypeAdapter(JsonCommunicatieProduct.class, new JsonDeserializer<JsonCommunicatieProduct>() {
            public JsonCommunicatieProduct deserialize(JsonElement json, Type pojoType, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                String type = jsonObject.get("type").getAsString();

                try {
                    String thepackage = "nl.lakedigital.djfc.commons.json.";
                    return context.deserialize(json, Class.forName(thepackage + type));
                } catch (ClassNotFoundException cnfe) {
                    throw new JsonParseException("Unknown element type: " + type, cnfe);
                }
            }
        }).create();

        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        JsonCommunicatieProduct yourClassList = gson.fromJson(response.getEntity(String.class), JsonCommunicatieProduct.class);

        return yourClassList;
    }

    protected List<JsonCommunicatieProduct> uitvoerenGetLijst(String adres, String... args) {
        Gson gson = new GsonBuilder().registerTypeAdapter(JsonCommunicatieProduct.class, new JsonDeserializer<JsonCommunicatieProduct>() {
            public JsonCommunicatieProduct deserialize(JsonElement json, Type pojoType, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                String type = jsonObject.get("type").getAsString();

                try {
                    String thepackage = "nl.lakedigital.djfc.commons.json.";
                    return context.deserialize(json, Class.forName(thepackage + type));
                } catch (ClassNotFoundException cnfe) {
                    throw new JsonParseException("Unknown element type: " + type, cnfe);
                }
            }
        }).create();

        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + adres + stringBuilder.toString());
        System.out.println("Aanroepen via GET " + adres + stringBuilder.toString());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        Type listType = getTypeToken();
        List<JsonCommunicatieProduct> yourClassList = gson.fromJson(response.getEntity(String.class), listType);

        return yourClassList;
    }
}
