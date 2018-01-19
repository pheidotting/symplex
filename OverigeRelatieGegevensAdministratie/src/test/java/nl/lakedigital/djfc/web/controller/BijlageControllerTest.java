package nl.lakedigital.djfc.web.controller;

import com.google.gson.Gson;
import nl.lakedigital.djfc.commons.json.JsonBijlage;
import nl.lakedigital.djfc.commons.xml.OpvragenBijlagesResponse;
import nl.lakedigital.djfc.domain.Bijlage;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.BijlageService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BijlageControllerTest extends AbstractControllerTest<Bijlage, JsonBijlage> {
    private static final String INGELOGDE_GEBRUIKER_HEADER = "ingelogdeGebruiker";
    private static final String TRACK_AND_TRACE_ID_HEADER = "trackAndTraceId";
    private static final String TRACK_AND_TRACE_ID = UUID.randomUUID().toString();
    
    @TestSubject
    private BijlageController bijlageController = new BijlageController();
    @Mock
    private BijlageService bijlageService;

    @Override
    public AbstractController getController() {
        return bijlageController;
    }

    @Override
    public AbstractService getService() {
        return bijlageService;
    }

    @Override
    public Bijlage getEntiteit() {
        return new Bijlage();
    }

    @Override
    public JsonBijlage getJsonEntiteit() {
        return new JsonBijlage();
    }

    @Override
    public Class setType() {
        return Bijlage.class;
    }

    @Override
    public Class setJsonType() {
        return JsonBijlage.class;
    }

    @Test
    public void testVerwijder() {
        Long id = 7L;

        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader(INGELOGDE_GEBRUIKER_HEADER)).andReturn("46");
        expect(httpServletRequest.getHeader(TRACK_AND_TRACE_ID_HEADER)).andReturn(TRACK_AND_TRACE_ID);
        expect(httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt")).andReturn("ingelogdeGebruikerOpgemaakt");
        expect(httpServletRequest.getHeader("url")).andReturn("url");

        bijlageService.verwijder(id);
        expectLastCall();

        replayAll();

        bijlageController.verwijder(id, httpServletRequest);

        verifyAll();
    }

    @Test
    public void testOpslaan() {
        JsonBijlage jsonBijlage = new JsonBijlage();
        final Bijlage bijlage = new Bijlage();
        final Long id = 9L;
        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader(INGELOGDE_GEBRUIKER_HEADER)).andReturn("46");
        expect(httpServletRequest.getHeader(TRACK_AND_TRACE_ID_HEADER)).andReturn(TRACK_AND_TRACE_ID);
        expect(httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt")).andReturn("ingelogdeGebruikerOpgemaakt");
        expect(httpServletRequest.getHeader("url")).andReturn("url");

        expect(mapper.map(jsonBijlage, Bijlage.class)).andReturn(bijlage);
        bijlageService.opslaan(bijlage);
        expectLastCall().andDelegateTo(new BijlageService() {
            @Override
            public void opslaan(Bijlage bijlage1) {
                bijlage1.setId(id);
            }
        });

        replayAll();

        assertThat(bijlageController.opslaan(jsonBijlage, httpServletRequest), is(id));

        verifyAll();
    }

    @Test
    public void testOpslaanLijst() {
        JsonBijlage jsonBijlage = new JsonBijlage();
        jsonBijlage.setSoortEntiteit("RELATIE");
        final Bijlage bijlage = new Bijlage();
        bijlage.setSoortEntiteit(SoortEntiteit.RELATIE);
        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader(INGELOGDE_GEBRUIKER_HEADER)).andReturn("46");
        expect(httpServletRequest.getHeader(TRACK_AND_TRACE_ID_HEADER)).andReturn(TRACK_AND_TRACE_ID);
        expect(httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt")).andReturn("ingelogdeGebruikerOpgemaakt");
        expect(httpServletRequest.getHeader("url")).andReturn("url");

        expect(mapper.map(jsonBijlage, Bijlage.class)).andReturn(bijlage);
        bijlageService.opslaan(newArrayList(bijlage), SoortEntiteit.RELATIE, null);
        expectLastCall();

        replayAll();

        bijlageController.opslaan(newArrayList(jsonBijlage), httpServletRequest);

        verifyAll();
    }

    @Test
    public void testGenereerBestandsnaam() {
        assertThat(bijlageController.genereerBestandsnaam(), is(notNullValue()));
    }

    @Test
    public void testLees() {
        OpvragenBijlagesResponse response = new OpvragenBijlagesResponse();
        JsonBijlage jsonBijlage = new JsonBijlage();
        response.getBijlages().add(jsonBijlage);
        final Bijlage bijlage = new Bijlage();
        final Long id = 9L;

        expect(bijlageService.lees(id)).andReturn(bijlage);
        expect(mapper.map(bijlage, JsonBijlage.class)).andReturn(jsonBijlage);

        replayAll();

        OpvragenBijlagesResponse real = bijlageController.lees(id, null);

        assertThat(real.getBijlages(), is(response.getBijlages()));

        verifyAll();
    }

    @Test
    public void testGetUploadPad() {
        String pad = "ditIsHetUploadPad";

        ReflectionTestUtils.setField(bijlageController, "uploadpad", pad);

        assertThat(bijlageController.getUploadPad(), is(new Gson().toJson(pad)));
    }
}