package nl.lakedigital.it;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.client.oga.AbstractOgaClient;
import nl.lakedigital.djfc.client.oga.BijlageClient;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static nl.lakedigital.assertion.Assert.assertEquals;

public class BijlageTest extends AbstractTest<JsonBijlage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BijlageTest.class);

    private BijlageClient bijlageClient = new BijlageClient("http://localhost:7072/oga");

    @Override
    public AbstractOgaClient getClient() {
        return bijlageClient;
    }

    public final List<String> fieldNames = Lists.newArrayList();

    @Override
    public List<String> getFields() {
        return fieldNames;
    }

    @Override
    public JsonBijlage maakEntiteit(int teller, Long entiteitId, SoortEntiteit soortEntiteit) {
        JsonBijlage jsonBijlage = new JsonBijlage();
        jsonBijlage.setBestandsNaam(UUID.randomUUID().toString());
        jsonBijlage.setOmschrijving(UUID.randomUUID().toString());
        jsonBijlage.setEntiteitId(entiteitId);
        jsonBijlage.setSoortEntiteit(soortEntiteit.name());

        return jsonBijlage;
    }

    @Override
    public JsonBijlage maakEntiteitVoorZoeken(String zoekWaarde, SoortEntiteit soortEntiteit, Long entiteitId) {
        JsonBijlage jsonBijlage = new JsonBijlage();
        jsonBijlage.setOmschrijving(zoekWaarde);
        jsonBijlage.setEntiteitId(entiteitId);
        jsonBijlage.setSoortEntiteit(soortEntiteit.name());

        return jsonBijlage;
    }

    @Override
    public void wijzig(JsonBijlage entiteit) {
        entiteit.setOmschrijving(UUID.randomUUID().toString());
    }

    @Test
    public void testZoek() {
        String trackAndTraceId = UUID.randomUUID().toString();
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        String zoek1 = "abc";
        String zoek2 = "bcd";
        String zoek3 = "cde";

        JsonBijlage entiteit1 = maakEntiteitVoorZoeken(zoek1, soortEntiteit, entiteitId);
        JsonBijlage entiteit2 = maakEntiteitVoorZoeken(zoek2, soortEntiteit, entiteitId);
        JsonBijlage entiteit3 = maakEntiteitVoorZoeken(zoek3, soortEntiteit, entiteitId);

        bijlageClient.opslaan(entiteit1, 46L, trackAndTraceId);
        bijlageClient.opslaan(entiteit2, 46L, trackAndTraceId);
        bijlageClient.opslaan(entiteit3, 46L, trackAndTraceId);

        assertEquals(1, getClient().zoeken("a").size());
        assertEquals(2, getClient().zoeken("b").size());
        assertEquals(3, getClient().zoeken("c").size());
        assertEquals(2, getClient().zoeken("d").size());
        assertEquals(1, getClient().zoeken("e").size());

        getClient().verwijder(soortEntiteit.name(), entiteitId, 46L, trackAndTraceId);
    }

    @Test
    public void allesOpslaanTest() {
        List<Long> ids = Lists.newArrayList(3L, 6L, 9L);
        List<JsonBijlage> adressen = new ArrayList<>();

        int teller = 0;

        for (SoortEntiteit soortEntiteit : SoortEntiteit.values()) {
            for (Long id : ids) {
                JsonBijlage jsonAdres = maakEntiteit(++teller, id, soortEntiteit);

                adressen.add(jsonAdres);

                LOGGER.info(ReflectionToStringBuilder.toString(jsonAdres, ToStringStyle.SHORT_PREFIX_STYLE));

                bijlageClient.opslaan(jsonAdres, 46L, trackAndTraceId);
            }
        }

        for (JsonBijlage jsonAdres : adressen) {

            List<JsonBijlage> adressedn = getClient().lijst(jsonAdres.getSoortEntiteit(), jsonAdres.getEntiteitId());

            JsonBijlage jsonAdres1 = adressedn.get(0);

            LOGGER.info(ReflectionToStringBuilder.toString(jsonAdres1, ToStringStyle.SHORT_PREFIX_STYLE));

            assertEquals(jsonAdres, jsonAdres1, getFields());

            getClient().verwijder(jsonAdres.getSoortEntiteit(), jsonAdres.getEntiteitId(), 46L, trackAndTraceId);
        }
    }

    @Test
    @Ignore
    public void opslaanEnVerwijder() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        JsonBijlage entiteit1 = maakEntiteit(1, entiteitId, soortEntiteit);
        JsonBijlage entiteit2 = maakEntiteit(2, entiteitId, soortEntiteit);
        JsonBijlage entiteit3 = maakEntiteit(3, entiteitId, soortEntiteit);

        bijlageClient.opslaan(entiteit1, 46L, trackAndTraceId);
        bijlageClient.opslaan(entiteit2, 46L, trackAndTraceId);
        bijlageClient.opslaan(entiteit3, 46L, trackAndTraceId);

        List<JsonBijlage> adressenOpgehaald = getClient().lijst(soortEntiteit.name(), entiteitId);
        entiteit1 = adressenOpgehaald.get(0);
        entiteit2 = adressenOpgehaald.get(1);
        entiteit3 = adressenOpgehaald.get(2);

        wijzig(entiteit3);
        bijlageClient.opslaan(entiteit1, 46L, trackAndTraceId);
        bijlageClient.opslaan(entiteit2, 46L, trackAndTraceId);
        bijlageClient.opslaan(entiteit3, 46L, trackAndTraceId);

        adressenOpgehaald = getClient().lijst(soortEntiteit.name(), entiteitId);
        entiteit1 = adressenOpgehaald.get(0);
        entiteit3 = adressenOpgehaald.get(2);
        assertEquals(3, adressenOpgehaald.size());

        bijlageClient.opslaan(Lists.newArrayList(entiteit1, entiteit3), 46L, trackAndTraceId);

        assertEquals(2, getClient().lijst(soortEntiteit.name(), entiteitId).size());
        getClient().verwijder(soortEntiteit.name(), entiteitId, 46L, trackAndTraceId);

        assertEquals(0, getClient().lijst(soortEntiteit.name(), entiteitId).size());
    }
}
