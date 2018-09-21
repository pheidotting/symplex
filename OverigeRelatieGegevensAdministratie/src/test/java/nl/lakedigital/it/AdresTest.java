package nl.lakedigital.it;

import com.google.common.collect.Lists;
import nl.lakedigital.djfc.client.oga.AbstractOgaClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.repository.AdresRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-it.xml")
public class AdresTest extends AbstractTest<JsonAdres> {
    private AdresClient adresClient = new AdresClient("http://localhost:7072/oga");
    @Inject
    private AdresRepository adresRepository;

    public final List<String> jsonAdresFieldNames = Lists.newArrayList(//
            "straat", //
            "huisnummer", //
            "toevoeging", //
            "postcode", //
            "plaats", //
            "soortAdres", //
            "soortEntiteit", //
            "entiteitId" //
    );

    @Override
    public AbstractOgaClient getClient() {
        return adresClient;
    }

    @Override
    public List<String> getFields() {
        return jsonAdresFieldNames;
    }

    @Override
    public JsonAdres maakEntiteit(int teller, Long entiteitId, SoortEntiteit soortEntiteit) {
        JsonAdres jsonAdres = new JsonAdres();

        jsonAdres.setToevoeging(UUID.randomUUID().toString().replace("-", ""));
        jsonAdres.setStraat(UUID.randomUUID().toString().replace("-", ""));
        jsonAdres.setPlaats(UUID.randomUUID().toString().replace("-", ""));
        jsonAdres.setSoortAdres("POSTADRES");
        jsonAdres.setPostcode(UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase());
        jsonAdres.setHuisnummer(Long.valueOf(teller));

        jsonAdres.setSoortEntiteit(soortEntiteit.name());
        jsonAdres.setEntiteitId(entiteitId);

        return jsonAdres;
    }

    @Override
    public void wijzig(JsonAdres entiteit) {
        entiteit.setStraat("nieuweStraat");
    }

    @Test
    @Ignore("om onnodige api calls te voorkomen")
    public void testOphalenAdresOpPostcode() {
        String postcode = "7894AB";
        String huisnummer = "41";

        JsonAdres verwacht = new JsonAdres();
        verwacht.setStraat("Eemslandweg");
        verwacht.setHuisnummer(41L);
        verwacht.setPostcode(postcode);
        verwacht.setPlaats("Zwartemeer");

        assertThat(adresClient.ophalenAdresOpPostcode(postcode, huisnummer, false), is(verwacht));
    }

    @Override
    public JsonAdres maakEntiteitVoorZoeken(String zoekWaarde, SoortEntiteit soortEntiteit, Long entiteitId) {
        JsonAdres jsonAdres = new JsonAdres();

        jsonAdres.setStraat(zoekWaarde);
        jsonAdres.setPlaats(zoekWaarde);
        jsonAdres.setSoortEntiteit(soortEntiteit.name());
        jsonAdres.setSoortAdres(Adres.SoortAdres.POSTADRES.name());
        jsonAdres.setEntiteitId(entiteitId);

        return jsonAdres;
    }
}
