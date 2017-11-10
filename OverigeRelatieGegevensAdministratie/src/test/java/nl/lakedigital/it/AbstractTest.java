package nl.lakedigital.it;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.client.oga.AbstractOgaClient;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
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

public abstract class AbstractTest<T extends AbstracteJsonEntiteitMetSoortEnId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
    protected final String trackAndTraceId = UUID.randomUUID().toString();

    public abstract AbstractOgaClient getClient();

    public abstract List<String> getFields();

    public abstract T maakEntiteit(int teller, Long entiteitId, SoortEntiteit soortEntiteit);

    public abstract T maakEntiteitVoorZoeken(String zoekWaarde, SoortEntiteit soortEntiteit, Long entiteitId);

    public abstract void wijzig(T entiteit);

    @Test
    public void allesOpslaanTest() {
        List<Long> ids = Lists.newArrayList(3L, 6L, 9L);
        List<T> adressen = new ArrayList<>();

        int teller = 0;

        for (SoortEntiteit soortEntiteit : SoortEntiteit.values()) {
            for (Long id : ids) {
                T jsonAdres = maakEntiteit(++teller, id, soortEntiteit);

                adressen.add(jsonAdres);

                LOGGER.debug(ReflectionToStringBuilder.toString(jsonAdres, ToStringStyle.SHORT_PREFIX_STYLE));
            }
        }

        getClient().opslaan(adressen, 46L, trackAndTraceId);

        for (T jsonAdres : adressen) {

            List<T> adressedn = getClient().lijst(jsonAdres.getSoortEntiteit(), jsonAdres.getEntiteitId());

            T jsonAdres1 = adressedn.get(0);

            LOGGER.debug(ReflectionToStringBuilder.toString(jsonAdres1, ToStringStyle.SHORT_PREFIX_STYLE));

            assertEquals(jsonAdres, jsonAdres1, getFields());

            getClient().verwijder(jsonAdres.getSoortEntiteit(), jsonAdres.getEntiteitId(), 46L, trackAndTraceId);
        }
    }

    @Test
    @Ignore
    public void opslaanEnVerwijder() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        T entiteit1 = maakEntiteit(1, entiteitId, soortEntiteit);
        T entiteit2 = maakEntiteit(2, entiteitId, soortEntiteit);
        T entiteit3 = maakEntiteit(3, entiteitId, soortEntiteit);

        getClient().opslaan(Lists.newArrayList(entiteit1, entiteit2, entiteit3), 46L, trackAndTraceId);

        List<T> adressenOpgehaald = getClient().lijst(soortEntiteit.name(), entiteitId);
        entiteit1 = adressenOpgehaald.get(0);
        entiteit3 = adressenOpgehaald.get(2);

        wijzig(entiteit3);
        getClient().opslaan(Lists.newArrayList(entiteit1, entiteit2, entiteit3), 46L, trackAndTraceId);

        adressenOpgehaald = getClient().lijst(soortEntiteit.name(), entiteitId);
        entiteit1 = adressenOpgehaald.get(0);
        entiteit3 = adressenOpgehaald.get(2);
        assertEquals(3, adressenOpgehaald.size());

        getClient().opslaan(Lists.newArrayList(entiteit1, entiteit3), 46L, trackAndTraceId);

        assertEquals(2, getClient().lijst(soortEntiteit.name(), entiteitId).size());
        getClient().verwijder(soortEntiteit.name(), entiteitId, 46L, trackAndTraceId);

        assertEquals(0, getClient().lijst(soortEntiteit.name(), entiteitId).size());
    }

    @Test
    @Ignore
    public void testZoek() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        String zoek1 = "abc";
        String zoek2 = "bcd";
        String zoek3 = "cde";

        T entiteit1 = maakEntiteitVoorZoeken(zoek1, soortEntiteit, entiteitId);
        T entiteit2 = maakEntiteitVoorZoeken(zoek2, soortEntiteit, entiteitId);
        T entiteit3 = maakEntiteitVoorZoeken(zoek3, soortEntiteit, entiteitId);

        getClient().opslaan(Lists.newArrayList(entiteit1, entiteit2, entiteit3), 46L, trackAndTraceId);

        assertEquals(1, getClient().zoeken("a").size());
        assertEquals(2, getClient().zoeken("b").size());
        assertEquals(3, getClient().zoeken("c").size());
        assertEquals(2, getClient().zoeken("d").size());
        assertEquals(1, getClient().zoeken("e").size());

        getClient().verwijder(soortEntiteit.name(), entiteitId, 46L, trackAndTraceId);
    }
}
