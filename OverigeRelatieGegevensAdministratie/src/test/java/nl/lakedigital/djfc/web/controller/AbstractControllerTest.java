package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.mapper.Mapper;
import nl.lakedigital.djfc.service.AbstractService;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public abstract class AbstractControllerTest<T extends AbstracteEntiteitMetSoortEnId, U extends AbstracteJsonEntiteitMetSoortEnId> extends EasyMockSupport {
    @Mock
    protected Mapper mapper;
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

    //    @Test
    //    public void testAlles() {
    //        SoortEntiteit soortEntiteit = SoortEntiteit.POLIS;
    //        Long entiteitId = 4L;
    //
    //        T entiteit = getEntiteit();
    //        U jsonEntiteit = getJsonEntiteit();
    //
    //        expect(getService().alles(soortEntiteit, entiteitId)).andReturn(newArrayList(entiteit));
    //        expect(mapper.map(entiteit, jsonType)).andReturn(jsonEntiteit);
    //
    //        replayAll();
    //
    //        assertEquals(newArrayList(jsonEntiteit), getController().alles(soortEntiteit.name(), entiteitId));
    //
    //        verifyAll();
    //    }

    @Test
    @Ignore
    public void opslaan() {
        SoortEntiteit soortEntiteit = SoortEntiteit.POLIS;
        Long entiteitId = 4L;
        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader("ingelogdeGebruiker")).andReturn("46").times(2);
        expect(httpServletRequest.getHeader("trackAndTraceId")).andReturn("trackAndTraceId").times(2);

        T entiteit = getEntiteit();
        U jsonEntiteit = getJsonEntiteit();
        jsonEntiteit.setSoortEntiteit(soortEntiteit.name());
        jsonEntiteit.setEntiteitId(entiteitId);

        expect(mapper.map(jsonEntiteit, type)).andReturn(entiteit);
        //        getService().opslaan(newArrayList(entiteit));
        expectLastCall();

        replayAll();

        getController().opslaan(newArrayList(jsonEntiteit), httpServletRequest);

        verifyAll();
    }

    @Test
    public void verwijderen() {
        SoortEntiteit soortEntiteit = SoortEntiteit.POLIS;
        Long entiteitId = 4L;
        HttpServletRequest httpServletRequest = createMock(HttpServletRequest.class);
        expect(httpServletRequest.getHeader("ingelogdeGebruiker")).andReturn("46");
        expect(httpServletRequest.getHeader("trackAndTraceId")).andReturn("trackAndTraceId");

        getService().verwijderen(soortEntiteit, entiteitId);
        expectLastCall();

        replayAll();

        getController().verwijderen(soortEntiteit.name(), entiteitId, httpServletRequest);

        verifyAll();
    }

    //    @Test
    //    public void zoeken() {
    //        String zoekTerm = "zoekTerm";
    //        List<T> domainEntiteiten = new ArrayList<>();
    //        T t = getEntiteit();
    //        domainEntiteiten.add(t);
    //
    //        List<U> jsonEntiteiten = new ArrayList<>();
    //        U u = getJsonEntiteit();
    //        jsonEntiteiten.add(u);
    //
    //        expect(getService().zoeken(zoekTerm)).andReturn(domainEntiteiten);
    //        expect(mapper.map(t, jsonType)).andReturn(u);
    //
    //        replayAll();
    //
    //        assertEquals(jsonEntiteiten, getController().zoeken(zoekTerm));
    //
    //        verifyAll();
    //    }

}