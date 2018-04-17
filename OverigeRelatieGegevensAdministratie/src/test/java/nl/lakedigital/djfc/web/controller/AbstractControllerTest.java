package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.AbstractService;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.MockType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public abstract class AbstractControllerTest<T extends AbstracteEntiteitMetSoortEnId, U extends AbstracteJsonEntiteitMetSoortEnId> extends EasyMockSupport {
    @Mock
    protected Mapper mapper;
    @Mock(type = MockType.NICE)
    protected MetricsService metricsService;
    @Mock(type = MockType.NICE)
    protected HttpServletRequest httpServletRequest;
    private Class<T> type;
    private Class<U> jsonType;

    public abstract AbstractController getController();

    public abstract AbstractService getService();

    public abstract T getEntiteit();

    public abstract U getJsonEntiteit();

    public abstract Class setType();

    public abstract Class setJsonType();

    @Before
    public void init() {
        jsonType = setJsonType();
        type = setType();
    }

    @Test
    public void verwijderen() {
        SoortEntiteit soortEntiteit = SoortEntiteit.POLIS;
        Long entiteitId = 4L;
        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader("ingelogdeGebruiker")).andReturn("46");
        expect(httpServletRequest.getHeader("trackAndTraceId")).andReturn("trackAndTraceId");
        expect(httpServletRequest.getHeader("ingelogdeGebruikerOpgemaakt")).andReturn("ingelogdeGebruikerOpgemaakt");
        expect(httpServletRequest.getHeader("url")).andReturn("url");

        getService().verwijderen(soortEntiteit, entiteitId);
        expectLastCall();

        replayAll();

        getController().verwijderen(soortEntiteit.name(), entiteitId, httpServletRequest);

        verifyAll();
    }
}