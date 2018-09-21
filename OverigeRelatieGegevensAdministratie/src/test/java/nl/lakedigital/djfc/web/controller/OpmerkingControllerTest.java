package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.OpmerkingService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OpmerkingControllerTest extends AbstractControllerTest<Opmerking, JsonOpmerking> {
    @TestSubject
    private OpmerkingController opmerkingController = new OpmerkingController();
    @Mock
    private OpmerkingService opmerkingService;

    @Override
    public AbstractController getController() {
        return opmerkingController;
    }

    @Override
    public AbstractService getService() {
        return opmerkingService;
    }

    @Override
    public Opmerking getEntiteit() {
        return new Opmerking();
    }

    @Override
    public JsonOpmerking getJsonEntiteit() {
        return new JsonOpmerking();
    }

    @Override
    public Class setType() {
        return Opmerking.class;
    }

    @Override
    public Class setJsonType() {
        return JsonOpmerking.class;
    }

    @Test
    public void testVerwijder() {
        Long id = 7L;

        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader("ingelogdeGebruiker")).andReturn("46");
        expect(httpServletRequest.getHeader("trackAndTraceId")).andReturn("trackAndTraceId");
        expect(httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt")).andReturn("ingelogdeGebruikerOpgemaakt");
        expect(httpServletRequest.getHeader("url")).andReturn("url");

        opmerkingService.verwijder(id);
        expectLastCall();

        replayAll();

        opmerkingController.verwijder(id, httpServletRequest);

        verifyAll();
    }

    @Test
    public void testOpslaan() {
        JsonOpmerking jsonOpmerking = new JsonOpmerking();
        final Opmerking opmerking = new Opmerking();
        final Long id = 9L;
        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader("ingelogdeGebruiker")).andReturn("46");
        expect(httpServletRequest.getHeader("trackAndTraceId")).andReturn("trackAndTraceId");
        expect(httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt")).andReturn("ingelogdeGebruikerOpgemaakt");
        expect(httpServletRequest.getHeader("url")).andReturn("url");

        expect(mapper.map(jsonOpmerking, Opmerking.class)).andReturn(opmerking);
        opmerkingService.opslaan(opmerking);
        expectLastCall().andDelegateTo(new OpmerkingService() {
            @Override
            public void opslaan(Opmerking opmerking1) {
                opmerking1.setId(id);
            }
        });

        replayAll();

        assertThat(opmerkingController.opslaan(jsonOpmerking, httpServletRequest), is(id));

        verifyAll();
    }

    @Test
    public void testAlles() {
        String soortEntiteit = "RELATIE";
        Long entiteitId = 5L;

        Opmerking telefoonnummer = new Opmerking();
        List<Opmerking> lijst = newArrayList(telefoonnummer);

        expect(opmerkingService.alles(SoortEntiteit.RELATIE, entiteitId)).andReturn(lijst);

        JsonOpmerking jsonOpmerking = new JsonOpmerking();
        expect(mapper.map(telefoonnummer, JsonOpmerking.class)).andReturn(jsonOpmerking);

        replayAll();

        List<JsonOpmerking> result = opmerkingController.alles(soortEntiteit, entiteitId, httpServletRequest).getOpmerkingen();
        Assert.assertThat(result.size(), Is.is(1));
        Assert.assertThat(result.get(0), Is.is(jsonOpmerking));

        verifyAll();
    }

    @Test
    public void testZoeken() {
        String zoekTerm = "65";

        Opmerking telefoonnummer = new Opmerking();
        List<Opmerking> lijst = newArrayList(telefoonnummer);

        expect(opmerkingService.zoeken(zoekTerm)).andReturn(lijst);

        JsonOpmerking jsonOpmerking = new JsonOpmerking();
        expect(mapper.map(telefoonnummer, JsonOpmerking.class)).andReturn(jsonOpmerking);

        replayAll();

        List<JsonOpmerking> result = opmerkingController.zoeken(zoekTerm, httpServletRequest).getOpmerkingen();
        Assert.assertThat(result.size(), Is.is(1));
        Assert.assertThat(result.get(0), Is.is(jsonOpmerking));

        verifyAll();
    }
}