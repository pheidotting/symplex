package nl.lakedigital.it;

import nl.lakedigital.djfc.client.oga.AbstractOgaClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public abstract class AbstractTest<T extends AbstracteJsonEntiteitMetSoortEnId> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
    protected final String trackAndTraceId = UUID.randomUUID().toString();

    public abstract AbstractOgaClient getClient();

    public abstract List<String> getFields();

    public abstract T maakEntiteit(int teller, Long entiteitId, SoortEntiteit soortEntiteit);

    public abstract T maakEntiteitVoorZoeken(String zoekWaarde, SoortEntiteit soortEntiteit, Long entiteitId);

    public abstract void wijzig(T entiteit);

}
