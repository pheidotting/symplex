package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import nl.lakedigital.djfc.domain.particulier.*;
import nl.lakedigital.djfc.domain.zakelijk.AanhangerVerzekering;
import nl.lakedigital.djfc.domain.zakelijk.GeldVerzekering;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.repository.PolisRepository;
import org.easymock.*;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class PolisServiceTest extends EasyMockSupport {
    private final long entiteitId = 46L;
    private final SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;

    @TestSubject
    private PolisService polisService = new PolisService();

    @Mock
    private PolisRepository polisRepository;
    @Mock
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;

    @After
    public void after() {
        verifyAll();
    }

    @Test
    public void testBeeindigen() {
        Polis polis = createMock(Polis.class);

        expect(polisRepository.lees(2L)).andReturn(polis);
        polis.setEindDatum(LocalDate.now());
        expectLastCall();

        polisRepository.opslaan(polis);
        expectLastCall();

        replayAll();

        polisService.beeindigen(2L);
    }

    @Test
    public void testOpslaanPolis() {
        final Long id = 58L;

        AutoVerzekering polis = new AutoVerzekering(soortEntiteit, entiteitId);

        polisRepository.opslaan(polis);
        expectLastCall().andDelegateTo(new PolisRepository() {
            @Override
            public void opslaan(Polis o) {
                o.setId(id);
            }
        });

        expect(polisRepository.lees(id)).andReturn(polis);

        Capture<List> soortEntiteitEnEntiteitIdsCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(capture(soortEntiteitEnEntiteitIdsCapture));

        replayAll();

        polisService.opslaan(polis);

        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = soortEntiteitEnEntiteitIdsCapture.getValue();
        assertThat(soortEntiteitEnEntiteitIds.size(), is(1));
        assertThat(soortEntiteitEnEntiteitIds.get(0).getEntiteitId(), is(58L));
        assertThat(soortEntiteitEnEntiteitIds.get(0).getSoortEntiteit().name(), is(SoortEntiteit.POLIS.name()));
    }

    @Test
    public void testZoekOpPolisNummer() {
        CamperVerzekering camperVerzekering = new CamperVerzekering(soortEntiteit, entiteitId);

        expect(polisRepository.zoekOpPolisNummer("1234")).andReturn(newArrayList(camperVerzekering));

        replayAll();

        assertThat(polisService.zoekOpPolisNummer("1234"), is(newArrayList(camperVerzekering)));
    }

    @Test
    public void testZoekOpPolisNummerMetException() {
        expect(polisRepository.zoekOpPolisNummer("1234")).andThrow(new NoResultException());

        replayAll();

        assertNull(polisService.zoekOpPolisNummer("1234"));
    }


    @Test
    public void testVerwijder() {
        MobieleApparatuurVerzekering verzekering = new MobieleApparatuurVerzekering(soortEntiteit, entiteitId);
        expect(polisRepository.lees(1L)).andReturn(verzekering);

        polisRepository.verwijder(verzekering);
        expectLastCall();

        replayAll();

        polisService.verwijder(1L);
    }

    @Test
    public void testAllePolisSoorten() {
        AnnuleringsVerzekering annuleringsVerzekering = new AnnuleringsVerzekering(soortEntiteit, entiteitId);
        InboedelVerzekering inboedelVerzekering = new InboedelVerzekering(soortEntiteit, entiteitId);

        AanhangerVerzekering aanhangerVerzekering = new AanhangerVerzekering(soortEntiteit, entiteitId);
        GeldVerzekering geldVerzekering = new GeldVerzekering(soortEntiteit, entiteitId);

        List<Polis> polissen = new ArrayList<>();
        polissen.add(annuleringsVerzekering);
        polissen.add(inboedelVerzekering);
        polissen.add(aanhangerVerzekering);
        polissen.add(geldVerzekering);

        List<String> verwachtParticulier = new ArrayList<>();
        verwachtParticulier.add(annuleringsVerzekering.getSchermNaam());
        verwachtParticulier.add(inboedelVerzekering.getSchermNaam());

        List<String> verwachtZakelijk = new ArrayList<>();
        verwachtZakelijk.add(aanhangerVerzekering.getSchermNaam());
        verwachtZakelijk.add(geldVerzekering.getSchermNaam());

        ReflectionTestUtils.setField(polisService, "polissen", polissen);

        replayAll();

        assertEquals(verwachtParticulier, polisService.allePolisSoorten(SoortVerzekering.PARTICULIER));
        assertEquals(verwachtZakelijk, polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK));
    }


    @Test
    public void testAlles() throws Exception {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 44L;

        List<Polis> polissen = new ArrayList<>();

        expect(polisRepository.alles(soortEntiteit, entiteitId)).andReturn(polissen);

        replayAll();

        assertThat(polisService.alles(soortEntiteit, entiteitId), is(polissen));
    }
}