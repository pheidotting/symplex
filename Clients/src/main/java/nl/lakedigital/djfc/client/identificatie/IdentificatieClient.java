package nl.lakedigital.djfc.client.identificatie;

import com.google.gson.Gson;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.ZoekIdentificatieResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.request.EntiteitenOpgeslagenRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class IdentificatieClient extends AbstractClient<ZoekIdentificatieResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentificatieClient.class);
    private final String URL_PING = "/rest/zabbix/checkDatabase";

    private MetricsService metricsService;

    public IdentificatieClient(String basisUrl) {
        super(basisUrl);
    }

    public IdentificatieClient() {
    }

    public void setMetricsService(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    protected Type getTypeToken() {
        return null;
    }

    public Identificatie zoekIdentificatie(String soortEntiteit, Long entiteitId) {
        return zoekIdentificatie(soortEntiteit, entiteitId, false);
    }

    public Identificatie zoekIdentificatie(String soortEntiteit, Long entiteitId, boolean retry) {
        if (metricsService != null) {
            metricsService.addMetric("zoekIdentificatie", IdentificatieClient.class, null, null);
        }
        List<Identificatie> lijst = getXML("/rest/identificatie/zoeken", ZoekIdentificatieResponse.class, false, LOGGER, false, metricsService, "zoekIdentificatie", IdentificatieClient.class, soortEntiteit, String.valueOf(entiteitId)).getIdentificaties();

        if (!lijst.isEmpty() && lijst.get(0).getEntiteitId() != null) {
            return lijst.get(0);
        } else if (!retry) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {//NOSONAR
                LOGGER.trace("InterruptedException ", e);
            }
            return zoekIdentificatie(soortEntiteit, entiteitId, true);
        }
        return null;
    }

    public Identificatie zoekIdentificatieCode(String identificatieCode) {
        return zoekIdentificatieCode(identificatieCode, false);
    }

    public Identificatie zoekIdentificatieCode(String identificatieCode, boolean retry) {
        if (metricsService != null) {
            metricsService.addMetric("zoekIdentificatieCode", IdentificatieClient.class, null, null);
        }
        List<Identificatie> lijst = getXML("/rest/identificatie/zoekenOpCode", ZoekIdentificatieResponse.class, false, LOGGER, false, metricsService, "zoekIdentificatieCode", IdentificatieClient.class, identificatieCode).getIdentificaties();

        if (!lijst.isEmpty() && lijst.get(0).getEntiteitId() != null) {
            return lijst.get(0);
        } else if (!retry) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {//NOSONAR
                LOGGER.trace("InterruptedException ", e);
            }
            return zoekIdentificatieCode(identificatieCode, true);
        }
        return null;
    }

    public List<Identificatie> zoekIdentificatieCodes(List<SoortEntiteitEnEntiteitId> soortenEntiteitEnEntiteitId) {
        if (metricsService != null) {
            metricsService.addMetric("zoekIdentificatieCodes", IdentificatieClient.class, null, null);
        }
        String idsString = join("&zoekterm=", soortenEntiteitEnEntiteitId.stream().map(new Function<SoortEntiteitEnEntiteitId, String>() {
            @Override
            public String apply(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
                return soortEntiteitEnEntiteitId.getSoortEntiteit() + "," + soortEntiteitEnEntiteitId.getEntiteitId();
            }
        }).collect(Collectors.toList()));

        LOGGER.debug(idsString);

        List<Identificatie> lijst = getXML("/rest/identificatie/zoekenMeerdere", ZoekIdentificatieResponse.class, false, LOGGER, false, metricsService, "zoekIdentificatieCodes", IdentificatieClient.class, idsString).getIdentificaties();

        return lijst;
    }

    @Deprecated
    public nl.lakedigital.djfc.commons.json.Identificatie opslaan(EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest) {
        return new Gson().fromJson(aanroepenUrlPost("/rest/identificatie/opslaan", entiteitenOpgeslagenRequest, 0L, "", LOGGER), nl.lakedigital.djfc.commons.json.Identificatie.class);
    }

    public String ping() {
        return uitvoerenGetAlsString(URL_PING, LOGGER);
    }
}
