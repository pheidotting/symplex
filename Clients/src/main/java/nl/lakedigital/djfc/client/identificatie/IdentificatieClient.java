package nl.lakedigital.djfc.client.identificatie;

import com.google.gson.Gson;
import nl.lakedigital.djfc.client.AbstractClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.ZoekIdentificatieResponse;
import nl.lakedigital.djfc.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.request.SoortEntiteitEnEntiteitId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.lang.String.join;

public class IdentificatieClient extends AbstractClient<ZoekIdentificatieResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(IdentificatieClient.class);
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public IdentificatieClient(String basisUrl) {
        super(basisUrl);
    }

    public IdentificatieClient() {
    }

    @Override
    protected Type getTypeToken() {
        return null;
    }

    public Identificatie zoekIdentificatie(String soortEntiteit, Long entiteitId) {
        List<Identificatie> lijst = getXML("/rest/identificatie/zoeken", ZoekIdentificatieResponse.class, false, LOGGER, false, soortEntiteit, String.valueOf(entiteitId)).getIdentificaties();

        if (!lijst.isEmpty()) {
            return lijst.get(0);
        }
        return null;
    }

    @Deprecated
    public Future<Identificatie> zoekIdentificatieMetFuture(String soortEntiteit, Long entiteitId) {
        return executor.submit(() -> {
            List<Identificatie> lijst = getXML("/rest/identificatie/zoeken", ZoekIdentificatieResponse.class, false, LOGGER, false, soortEntiteit, String.valueOf(entiteitId)).getIdentificaties();

            if (!lijst.isEmpty()) {
                return lijst.get(0);
            } else {
                return null;
            }
        });
    }

    public Identificatie zoekIdentificatieCode(String identificatieCode) {
        List<Identificatie> lijst = getXML("/rest/identificatie/zoekenOpCode", ZoekIdentificatieResponse.class, false, LOGGER, false, identificatieCode).getIdentificaties();

        if (!lijst.isEmpty()) {
            return lijst.get(0);
        }
        return null;
    }

    public List<Identificatie> zoekIdentificatieCodes(List<SoortEntiteitEnEntiteitId> soortenEntiteitEnEntiteitId) {
        String idsString = join("&zoekterm=", soortenEntiteitEnEntiteitId.stream().map(new Function<SoortEntiteitEnEntiteitId, String>() {
            @Override
            public String apply(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId) {
                return soortEntiteitEnEntiteitId.getSoortEntiteit() + "," + soortEntiteitEnEntiteitId.getEntiteitId();
            }
        }).collect(Collectors.toList()));

        LOGGER.debug(idsString);

        List<Identificatie> lijst = getXML("/rest/identificatie/zoekenMeerdere", ZoekIdentificatieResponse.class, false, LOGGER, false, idsString).getIdentificaties();

        return lijst;
    }

    public nl.lakedigital.djfc.commons.json.Identificatie opslaan(EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest) {
        return new Gson().fromJson(aanroepenUrlPost("/rest/identificatie/opslaan", entiteitenOpgeslagenRequest, 0L, "", LOGGER), nl.lakedigital.djfc.commons.json.Identificatie.class);
    }
}
