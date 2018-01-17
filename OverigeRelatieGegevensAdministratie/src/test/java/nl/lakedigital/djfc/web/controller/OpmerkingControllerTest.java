package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.JsonOpmerking;
import nl.lakedigital.djfc.domain.Opmerking;
import nl.lakedigital.djfc.service.AbstractService;
import nl.lakedigital.djfc.service.OpmerkingService;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

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
}