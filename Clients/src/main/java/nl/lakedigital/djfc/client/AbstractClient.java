package nl.lakedigital.djfc.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public abstract class AbstractClient<D> {
    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();
    protected String basisUrl;
    protected XmlMapper mapper = new XmlMapper();

    public AbstractClient() {
    }

    public AbstractClient(String basisUrl) {
        this.basisUrl = basisUrl;
    }

    public void setBasisUrl(String basisUrl, Logger LOGGER) {
        LOGGER.debug("zet basisurl {}", basisUrl);
        this.basisUrl = basisUrl;
    }

    protected D getXML(String uri, Class<D> clazz, boolean urlEncoden, Logger LOGGER, boolean retry, String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        URL url;
        try {
            if (urlEncoden) {
                url = new URL(basisUrl + uri + URLEncoder.encode(stringBuilder.toString(), "UTF-8").replace("+", "%20"));
            } else {
                url = new URL(basisUrl + uri + stringBuilder.toString());
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("ingelogdeGebruiker", MDC.get("ingelogdeGebruiker"));
            connection.setRequestProperty("ingelogdeGebruikerOpgemaakt", MDC.get("ingelogdeGebruikerOpgemaakt"));
            connection.setRequestProperty("trackAndTraceId", MDC.get("trackAndTraceId"));
            connection.setRequestProperty("url", MDC.get("url"));
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);

            InputStream xml = connection.getInputStream();

            D response = mapper.readValue(xml, clazz);

            connection.disconnect();

            return response;
        } catch (IOException e) {
            if (!retry) {
                LOGGER.debug("Error opgetreden, retry");
                return getXML(uri, clazz, urlEncoden, LOGGER, true, args);
            } else {
                LOGGER.error("Fout bij omzetten xml {}", e);
                throw new LeesFoutException("Fout bij omzetten xml", e);
            }
        }
    }

    @Deprecated
    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId, Logger LOGGER) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(basisUrl + adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {} naar {}", verstuurObject, basisUrl + adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    @Deprecated
    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId, String sessie, Logger LOGGER) {
        Gson gson = builder.create();

        Client client = Client.create();

        client.addFilter(new ClientFilter() {

            @Override
            public ClientResponse handle(ClientRequest clientRequest) throws ClientHandlerException {


                clientRequest.getHeaders().putSingle("sessie", sessie);

                ClientResponse response = getNext().handle(clientRequest);


                return response;
            }
        });

        WebResource webResource = client.resource(basisUrl + adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {} naar {}", verstuurObject, basisUrl + adres);

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("sessie", sessie).header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    @Deprecated
    protected void aanroepenUrlPostZonderBody(String adres, Long ingelogdeGebruiker, String trackAndTraceId, String... args) {
        Client client = Client.create();

        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }

        WebResource webResource = client.resource(basisUrl + adres + stringBuilder.toString());

        webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", ingelogdeGebruiker.toString()).header("trackAndTraceId", trackAndTraceId).post();
    }

    //Functie bestaat alleen tbv PowerMock
    @Deprecated
    protected String uitvoerenGetString(String adres, Logger LOGGER) {
        return uitvoerenGet(adres, LOGGER);
    }

    @Deprecated
    protected String uitvoerenGet(String adres, Logger LOGGER) {
        LOGGER.info("Aanroepen via GET " + adres);
        System.out.println("Aanroepen via GET " + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            LOGGER.error("Failed : HTTP error code : " + response.getStatus());
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }
        return response.getEntity(String.class);
    }

    @Deprecated
    protected <T> T uitvoerenGet(String adres, Class<T> clazz, Logger LOGGER, String... args) {
        LOGGER.debug("uitvoerenGet");

        Gson gson = builder.create();

        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response;
        response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            LOGGER.error("Failed : HTTP error code : " + response.getStatus());
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        String result = response.getEntity(String.class);

        LOGGER.debug("Result : {}", result);

        return gson.fromJson(result, clazz);
    }

    @Deprecated
    protected <T> List<T> uitvoerenGetLijst(String adres, Class<T> clazz, Logger LOGGER, String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            LOGGER.error("Failed : HTTP error code : " + response.getStatus());
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        Type listType = getTypeToken();
        List<T> yourClassList = new Gson().fromJson(response.getEntity(String.class), listType);

        return yourClassList;
    }

    protected abstract Type getTypeToken();
}
