package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.RekeningNummerService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.runner.RunWith;

@RunWith(EasyMockRunner.class)
public class RekeningNummerControllerTest extends AbstractControllerTest<RekeningNummer, JsonRekeningNummer> {
    @TestSubject
    private RekeningNummerController rekeningNummerController = new RekeningNummerController();
    @Mock
    private RekeningNummerService rekeningNummerService;

    @Override
    public AbstractController getController() {
        return rekeningNummerController;
    }

    @Override
    public AbstractService getService() {
        return rekeningNummerService;
    }

    @Override
    public RekeningNummer getEntiteit() {
        return new RekeningNummer();
    }

    @Override
    public JsonRekeningNummer getJsonEntiteit() {
        return new JsonRekeningNummer();
    }

    @Override
    public Class setType() {
        return RekeningNummer.class;
    }

    @Override
    public Class setJsonType() {
        return JsonRekeningNummer.class;
    }
}