package nl.lakedigital.djfc.client.oga;

import com.codahale.metrics.Timer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.client.LeesFoutException;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import nl.lakedigital.djfc.interfaces.Metrics;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public abstract class AbstractOgaClient<T extends AbstracteJsonEntiteitMetSoortEnId, D> extends AbstractClient<Object> {
    private GsonBuilder builder = new GsonBuilder();
    protected Gson gson = new Gson();
    protected XmlMapper mapper = new XmlMapper();

    public AbstractOgaClient(String basisUrl) {
        super(basisUrl);
    }

    public AbstractOgaClient() {
    }

    public abstract List<T> lijst(String soortEntiteit, Long entiteitId);


    public abstract void verwijder(String soortEntiteit, Long entiteitId, Long ingelogdeGebruiker, String trackAndTraceId);

    public abstract List<T> zoeken(String zoekterm);

    protected D getXMLVoorLijstOGA(String uri, Class<D> clazz, Logger LOGGER, Metrics metrics, String metricsName, Class metricsClass, String... args) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        URL url;
        try {
            String urlString = uri + URLEncoder.encode(stringBuilder.toString(), "UTF-8").replace("+", "%20");
            LOGGER.debug("Aanroepen {}", urlString);
            url = new URL(urlString);
        } catch (UnsupportedEncodingException e) {
            throw new LeesFoutException("Fout bij omzetten adres", e);
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

        Timer.Context timer = null;
        if (metrics != null) {
            timer = metrics.addTimerMetric(metricsName, metricsClass);
            metrics.addMetric(metricsName, metricsClass, null, null);
        }

        InputStream xml = connection.getInputStream();

        if (metrics != null) {
            metrics.stop(timer);
        }

        D response = mapper.readValue(xml, clazz);

        connection.disconnect();

        return response;
    }

    protected D getXMLVoorLijstOGAZonderEncode(String uri, Class<D> clazz, Logger LOGGER, Metrics metrics, String metricsName, Class metricsClass, String... args) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        String urlString = uri + stringBuilder.toString();
        LOGGER.debug("Aanroepen {}", urlString);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        connection.setRequestProperty("ingelogdeGebruiker", MDC.get("ingelogdeGebruiker"));
        connection.setRequestProperty("ingelogdeGebruikerOpgemaakt", MDC.get("ingelogdeGebruikerOpgemaakt"));
        connection.setRequestProperty("trackAndTraceId", MDC.get("trackAndTraceId"));
        connection.setRequestProperty("url", MDC.get("url"));
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(10000);

        Timer.Context timer = null;
        if (metrics != null) {
            timer = metrics.addTimerMetric(metricsName, metricsClass);
            metrics.addMetric(metricsName, clazz, null, null);
        }

        InputStream xml = connection.getInputStream();

        if (metrics != null) {
            metrics.stop(timer);
        }

        D response = mapper.readValue(xml, clazz);

        connection.disconnect();

        return response;
    }

    protected String aanroepenUrlPost(String adres, Object object, Long ingelogdeGebruiker, String trackAndTraceId, Logger LOGGER) {
        Gson gson = builder.create();

        Client client = Client.create();

        WebResource webResource = client.resource(basisUrl + adres);
        String verstuurObject = gson.toJson(object);
        LOGGER.info("Versturen {}", verstuurObject);
        System.out.println("Versturen " + verstuurObject + " naar " + basisUrl + adres);

        String gebruiker = null;
        if (ingelogdeGebruiker != null) {
            gebruiker = String.valueOf(ingelogdeGebruiker);
        }

        ClientResponse cr = webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", gebruiker).header("trackAndTraceId", trackAndTraceId).post(ClientResponse.class, verstuurObject);

        return cr.getEntity(String.class);
    }

    protected void aanroepenUrlPostZonderBody(String adres, String trackAndTraceId, Long ingelogdeGebruiker, String... args) {
        Client client = Client.create();

        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }

        String gebruiker = null;
        if (ingelogdeGebruiker != null) {
            gebruiker = String.valueOf(ingelogdeGebruiker);
        }

        WebResource webResource = client.resource(basisUrl + adres + stringBuilder.toString());

        webResource.accept("application/json").type("application/json").header("ingelogdeGebruiker", gebruiker).header("trackAndTraceId", trackAndTraceId).post();
    }

    protected String uitvoerenGet(String adres, Logger LOGGER) {
        LOGGER.info("Aanroepen via GET " + basisUrl + adres);
        System.out.println("Aanroepen via GET " + basisUrl + adres);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        String ret = response.getEntity(String.class);

        LOGGER.debug(ret);

        return ret;
    }

    protected <T> List<T> uitvoerenGetLijst(String url, Class<T> clazz, Logger LOGGER, String... args) {
        String adres = url;
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());
        try {
            adres = URLEncoder.encode(adres, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new LeesFoutException("Fout bij omzetten adres", e);
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());
        System.out.println("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        Type listType = getTypeToken();
        List<T> yourClassList = new Gson().fromJson(response.getEntity(String.class), listType);

        return yourClassList;
    }

    protected <T> List<T> uitvoerenGetLijstZonderEncoding(String adres, Class<T> clazz, Logger LOGGER, String... args) {
        StringBuilder stringBuilder = new StringBuilder();
        if (args != null) {
            for (String arg : args) {
                stringBuilder.append("/");
                stringBuilder.append(arg);
            }
        }
        LOGGER.info("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());
        System.out.println("Aanroepen via GET " + basisUrl + adres + stringBuilder.toString());

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(basisUrl + adres);
        ClientResponse response = webResource.accept("application/json").type("application/json").get(ClientResponse.class);
        if (response.getStatus() != 200) {
            throw new LeesFoutException("Failed : HTTP error code : " + response.getStatus());
        }

        Type listType = getTypeToken();
        List<T> yourClassList = new Gson().fromJson(response.getEntity(String.class), listType);

        return yourClassList;
    }

    protected abstract Type getTypeToken();
}
