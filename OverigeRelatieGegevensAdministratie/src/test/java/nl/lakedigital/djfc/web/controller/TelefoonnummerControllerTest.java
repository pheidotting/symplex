package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

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
    public void testOpslaanLijst() {
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();
        jsonTelefoonnummer.setSoortEntiteit("RELATIE");
        final Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setSoortEntiteit(SoortEntiteit.RELATIE);
        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader("ingelogdeGebruiker")).andReturn("46");
        expect(httpServletRequest.getHeader("trackAndTraceId")).andReturn("trackAndTraceId");

        expect(mapper.map(jsonTelefoonnummer, Telefoonnummer.class)).andReturn(telefoonnummer);
        telefoonnummerService.opslaan(newArrayList(telefoonnummer), SoortEntiteit.RELATIE, null);
        expectLastCall();

        replayAll();

        telefoonnummerController.opslaan(newArrayList(jsonTelefoonnummer), httpServletRequest);

        verifyAll();
    }

}