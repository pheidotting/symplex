package nl.lakedigital.djfc.service;

import com.google.common.collect.Lists;
import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.AbstracteEntiteitMetSoortEnId;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.messaging.sender.EntiteitenVerwijderdRequestSender;
import nl.lakedigital.djfc.repository.AbstractRepository;
import org.easymock.Capture;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public abstract class AbstractServicetTest<T extends AbstracteEntiteitMetSoortEnId> extends EasyMockSupport {

    @Mock
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Mock
    private EntiteitenVerwijderdRequestSender entiteitenVerwijderdRequestSender;

    public abstract AbstractService getService();

    public abstract AbstractRepository getRepository();

    public abstract T getLegeEntiteit();

    public abstract T getGevuldeEntiteit();

    public abstract T getGevuldeBestaandeEntiteit();

    public abstract T getTeVerwijderenEntiteit();

    @Test
    public void testAlles() throws Exception {
        List<T> lijst = new ArrayList<>();

        expect(getRepository().alles(SoortEntiteit.SCHADE, 3L)).andReturn(lijst);

        replayAll();

        assertEquals(lijst, getService().alles(SoortEntiteit.SCHADE, 3L));

        verifyAll();
    }

    @Test
    public void testLees() throws Exception {
        T entiteit = getLegeEntiteit();
        Long id = 33L;

        expect(getRepository().lees(id)).andReturn(entiteit);

        replayAll();

        assertEquals(entiteit, getService().lees(id));

        verifyAll();
    }

    @Test
    public void testOpslaan() throws Exception {
        T entiteit = getLegeEntiteit();

        getRepository().opslaan(Lists.newArrayList(entiteit));
            expectLastCall();

        replayAll();

        getService().opslaan(entiteit);

        verifyAll();
    }

    @Test
    public void testOpslaanLijst() throws Exception {
        SoortEntiteit soortEntiteit = SoortEntiteit.POLIS;
        Long entiteitId = 55L;

        T nieuw = getGevuldeEntiteit();
        T bestaand = getGevuldeBestaandeEntiteit();
        T teVerwijderen = getTeVerwijderenEntiteit();

        expect(getRepository().alles(soortEntiteit, entiteitId)).andReturn(Lists.newArrayList(bestaand, teVerwijderen));

        getRepository().opslaan(Lists.newArrayList(nieuw, bestaand));
            expectLastCall();
        getRepository().verwijder(Lists.newArrayList(teVerwijderen));
        expectLastCall();

        Capture<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenVerwijderdRequestSender.send(capture(soortEntiteitEnEntiteitIdCapture));

        Capture<List> listCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(capture(listCapture));

        expectLastCall();

        replayAll();

        getService().opslaan(Lists.newArrayList(nieuw, bestaand), soortEntiteit, entiteitId);

        verifyAll();

        assertThat(listCapture.getValue().size(), is(2));
    }

    @Test
    public void testVerwijderen() throws Exception {
        SoortEntiteit soortEntiteit = SoortEntiteit.POLIS;
        Long entiteitId = 55L;

        T entiteit = getLegeEntiteit();
        List<T> entiteiten = Lists.newArrayList(entiteit);

        expect(getRepository().alles(soortEntiteit, entiteitId)).andReturn(entiteiten);
        getRepository().verwijder(Lists.newArrayList(entiteit));
        expectLastCall();

        replayAll();

        getService().verwijderen(soortEntiteit, entiteitId);

        verifyAll();
    }

    @Test
    public void testZoeken() {
        String zoekTerm = "zoekTerm";
        List<T> lijst = new ArrayList<>();

        expect(getRepository().zoek(zoekTerm)).andReturn(lijst);

        replayAll();

        assertEquals(getService().zoeken(zoekTerm), lijst);

        verifyAll();
    }
}
