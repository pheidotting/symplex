package nl.lakedigital.djfc.client.polisadministratie;

import com.google.gson.reflect.TypeToken;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import nl.lakedigital.djfc.commons.json.JsonSoortSchade;
import nl.lakedigital.djfc.commons.xml.OpvragenSchadesResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenSoortSchadeResponse;
import nl.lakedigital.djfc.commons.xml.OpvragenStatusSchadeResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SchadeClient extends AbstractClient<OpvragenSchadesResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeClient.class);

    private final String URL_OPSLAAN = "/rest/schade/opslaan";
    private final String URL_LIJST = "/rest/schade/lijst";
    private final String URL_LIJST_BEDRIJF = "/rest/schade/lijstBijBedrijf";
    private final String URL_LEES = "/rest/schade/lees";
    private final String URL_VERWIJDER = "/rest/schade/verwijder";
    private final String URL_SOORTEN_SCHADE = "/rest/schade/soortenSchade";
    private final String URL_STATUSSEN_SCHADE = "/rest/schade/lijstStatusSchade";
    private final String URL_ZOEK_OP_SCHADENUMMER_MAATSCHAPPIJE = "/rest/schade/zoekOpSchadeNummerMaatschappij";

    private MetricsService metricsService;

    public SchadeClient(String basisUrl) {
        super(basisUrl);
    }

    public SchadeClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return new TypeToken<ArrayList<JsonSchade>>() {
        }.getType();
    }

    @Deprecated
    public Long opslaan(JsonSchade jsonSchade, Long ingelogdeGebruiker, String trackAndTraceId) {
        return Long.valueOf(aanroepenUrlPost(URL_OPSLAAN, jsonSchade, ingelogdeGebruiker, trackAndTraceId, LOGGER));
    }

    public List<JsonSchade> lijst(Long relatieId) {
        return getXML(URL_LIJST, OpvragenSchadesResponse.class, false, LOGGER, false, metricsService, "lijst", SchadeClient.class, String.valueOf(relatieId)).getSchades();
    }

    public List<JsonSchade> lijstBijBedrijf(Long bedrijfId) {
        return getXML(URL_LIJST_BEDRIJF, OpvragenSchadesResponse.class, false, LOGGER, false, metricsService, "lijstBijBedrijf", SchadeClient.class, String.valueOf(bedrijfId)).getSchades();
    }

    public List<JsonSchade> zoekOpSchadeNummerMaatschappij(String schadeNummerMaatschappij) {
        return getXML(URL_ZOEK_OP_SCHADENUMMER_MAATSCHAPPIJE, OpvragenSchadesResponse.class, false, LOGGER, false, metricsService, "zoekOpSchadeNummerMaatschappij", SchadeClient.class, schadeNummerMaatschappij).getSchades();
    }

    public JsonSchade lees(String id) {
        return getXML(URL_LEES, OpvragenSchadesResponse.class, false, LOGGER, false, metricsService, "lees", SchadeClient.class, String.valueOf(id)).getSchades().get(0);
    }

    @Deprecated
    public void verwijder(Long id, Long ingelogdeGebruiker, String trackAndTraceId) {
        aanroepenUrlPostZonderBody(URL_VERWIJDER + "/" + id, ingelogdeGebruiker, trackAndTraceId);
    }

    public List<JsonSoortSchade> soortenSchade(String query) {
        return new SoortSchadeClient(basisUrl).soortenSchade(query);
    }

    public List<String> lijstStatusSchade() {
        return new StatusSchadeClient(basisUrl).lijstStatusSchade();
    }

    private class SoortSchadeClient extends AbstractClient<OpvragenSoortSchadeResponse> {
        public SoortSchadeClient(String basisUrl) {
            super(basisUrl);
        }

        @Override
        protected Type getTypeToken() {
            return new TypeToken<ArrayList<JsonSchade>>() {
            }.getType();
        }

        public List<JsonSoortSchade> soortenSchade(String query) {
            return getXML(URL_SOORTEN_SCHADE, OpvragenSoortSchadeResponse.class, false, LOGGER, false, metricsService, "soortenSchade", SchadeClient.class, query).getSoortSchade();
        }
    }

    private class StatusSchadeClient extends AbstractClient<OpvragenStatusSchadeResponse> {

        public StatusSchadeClient(String basisUrl) {
            super(basisUrl);
        }

        @Override
        protected Type getTypeToken() {
            return new TypeToken<ArrayList<JsonSchade>>() {
            }.getType();
        }

        public List<String> lijstStatusSchade() {
            return getXML(URL_SOORTEN_SCHADE, OpvragenStatusSchadeResponse.class, false, LOGGER, false, metricsService, "lijstStatusSchade", SchadeClient.class, "").getStatusSchade();
        }
    }
}