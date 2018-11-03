package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;
import nl.lakedigital.djfc.domain.RekeningNummer;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.RekeningNummerService;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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

    @Test
    public void testAlles() {
        String soortEntiteit = "RELATIE";
        Long entiteitId = 5L;

        RekeningNummer telefoonnummer = new RekeningNummer();
        List<RekeningNummer> lijst = newArrayList(telefoonnummer);

        expect(rekeningNummerService.alles(SoortEntiteit.RELATIE, entiteitId)).andReturn(lijst);

        JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();
        expect(mapper.map(telefoonnummer, JsonRekeningNummer.class)).andReturn(jsonRekeningNummer);

        replayAll();

        List<JsonRekeningNummer> result = rekeningNummerController.alles(soortEntiteit, entiteitId, httpServletRequest).getRekeningNummers();
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(jsonRekeningNummer));

        verifyAll();
    }

    @Test
    public void testZoeken() {
        String zoekTerm = "65";

        RekeningNummer telefoonnummer = new RekeningNummer();
        List<RekeningNummer> lijst = newArrayList(telefoonnummer);

        expect(rekeningNummerService.zoeken(zoekTerm)).andReturn(lijst);

        JsonRekeningNummer jsonRekeningNummer = new JsonRekeningNummer();
        expect(mapper.map(telefoonnummer, JsonRekeningNummer.class)).andReturn(jsonRekeningNummer);

        replayAll();

        List<JsonRekeningNummer> result = rekeningNummerController.zoeken(zoekTerm, httpServletRequest).getRekeningNummers();
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(jsonRekeningNummer));

        verifyAll();
    }
}