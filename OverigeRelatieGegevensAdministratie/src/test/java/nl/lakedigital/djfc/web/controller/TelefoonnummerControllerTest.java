package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TelefoonnummerControllerTest extends AbstractControllerTest<Telefoonnummer, JsonTelefoonnummer> {
    @TestSubject
    private TelefoonnummerController telefoonnummerController = new TelefoonnummerController();
    @Mock
    private TelefoonnummerService telefoonnummerService;

    @Override
    public AbstractController getController() {
        return telefoonnummerController;
    }

    @Override
    public AbstractService getService() {
        return telefoonnummerService;
    }

    @Override
    public Telefoonnummer getEntiteit() {
        return new Telefoonnummer();
    }

    @Override
    public JsonTelefoonnummer getJsonEntiteit() {
        return new JsonTelefoonnummer();
    }

    @Override
    public Class setType() {
        return Telefoonnummer.class;
    }

    @Override
    public Class setJsonType() {
        return JsonTelefoonnummer.class;
    }

    @Test
    public void testAlles() {
        String soortEntiteit = "RELATIE";
        Long entiteitId = 5L;

        Telefoonnummer telefoonnummer = new Telefoonnummer();
        List<Telefoonnummer> lijst = newArrayList(telefoonnummer);

        expect(telefoonnummerService.alles(SoortEntiteit.RELATIE, entiteitId)).andReturn(lijst);

        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();
        expect(mapper.map(telefoonnummer, JsonTelefoonnummer.class)).andReturn(jsonTelefoonnummer);

        replayAll();

        List<JsonTelefoonnummer> result = telefoonnummerController.alles(soortEntiteit, entiteitId, httpServletRequest).getTelefoonnummers();
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(jsonTelefoonnummer));

        verifyAll();
    }

    @Test
    public void testZoeken() {
        String zoekTerm = "65";

        Telefoonnummer telefoonnummer = new Telefoonnummer();
        List<Telefoonnummer> lijst = newArrayList(telefoonnummer);

        expect(telefoonnummerService.zoeken(zoekTerm)).andReturn(lijst);

        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();
        expect(mapper.map(telefoonnummer, JsonTelefoonnummer.class)).andReturn(jsonTelefoonnummer);

        replayAll();

        List<JsonTelefoonnummer> result = telefoonnummerController.zoeken(zoekTerm, httpServletRequest).getTelefoonnummers();
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(jsonTelefoonnummer));

        verifyAll();
    }
}