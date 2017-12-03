package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.djfc.domain.Telefoonnummer;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.easymock.Mock;
import org.easymock.TestSubject;

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


}