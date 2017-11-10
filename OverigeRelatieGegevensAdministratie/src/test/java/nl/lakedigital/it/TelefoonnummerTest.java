package nl.lakedigital.it;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.client.oga.AbstractOgaClient;
import nl.lakedigital.djfc.client.oga.TelefoonnummerClient;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.junit.Ignore;

import java.util.List;
import java.util.UUID;
@Ignore
public class TelefoonnummerTest extends AbstractTest<JsonTelefoonnummer> {
    private TelefoonnummerClient telefoonnummerClient = new TelefoonnummerClient("http://localhost:7072/oga");

    public final List<String> fieldNames = Lists.newArrayList(//
            "telefoonnummer", //
            "soort",//
            "omschrijving");

    @Override
    public AbstractOgaClient getClient() {
        return telefoonnummerClient;
    }
    @Override
    public List<String> getFields() {
        return fieldNames;
    }

    @Override
    public JsonTelefoonnummer maakEntiteit(int teller, Long entiteitId, SoortEntiteit soortEntiteit) {
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

        jsonTelefoonnummer.setTelefoonnummer(UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        jsonTelefoonnummer.setSoortEntiteit(soortEntiteit.name());
        jsonTelefoonnummer.setEntiteitId(entiteitId);
        jsonTelefoonnummer.setOmschrijving(UUID.randomUUID().toString());
        jsonTelefoonnummer.setSoort("MOBIEL");

        return jsonTelefoonnummer;
    }

    @Override
    public JsonTelefoonnummer maakEntiteitVoorZoeken(String zoekWaarde, SoortEntiteit soortEntiteit, Long entiteitId) {
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();

        jsonTelefoonnummer.setTelefoonnummer(zoekWaarde);
        jsonTelefoonnummer.setSoortEntiteit(soortEntiteit.name());
        jsonTelefoonnummer.setEntiteitId(entiteitId);
        jsonTelefoonnummer.setSoort("MOBIEL");

        return jsonTelefoonnummer;
    }

    @Override
    public void wijzig(JsonTelefoonnummer entiteit) {
        entiteit.setTelefoonnummer("telnummer");
    }
}
