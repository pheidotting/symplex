package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.domain.Adres;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.AdresService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AdresControllerTest extends AbstractControllerTest<Adres, JsonAdres> {
    @TestSubject
    private AdresController adresController = new AdresController();
    @Mock
    private AdresService adresService;

    @Override
    public AbstractController getController() {
        return adresController;
    }

    @Override
    public AbstractService getService() {
        return adresService;
    }

    @Override
    public Adres getEntiteit() {
        return new Adres();
    }

    @Override
    public JsonAdres getJsonEntiteit() {
        return new JsonAdres();
    }

    @Override
    public Class setType() {
        return Adres.class;
    }

    @Override
    public Class setJsonType() {
        return JsonAdres.class;
    }

    @Test
    public void testLees() {
        Adres adres = new Adres();
        JsonAdres jsonAdres = new JsonAdres();
        Long id = 5L;

        expect(adresService.lees(id)).andReturn(adres);
        expect(mapper.map(adres, JsonAdres.class)).andReturn(jsonAdres);

        replayAll();

        assertThat(adresController.lees(id, null).getAdressen().get(0), is(jsonAdres));

        verifyAll();
    }
}