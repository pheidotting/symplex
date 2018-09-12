package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.Pakket;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.domain.SoortVerzekering;
import nl.lakedigital.djfc.domain.particulier.AnnuleringsVerzekering;
import nl.lakedigital.djfc.domain.particulier.AutoVerzekering;
import nl.lakedigital.djfc.domain.particulier.CamperVerzekering;
import nl.lakedigital.djfc.domain.particulier.InboedelVerzekering;
import nl.lakedigital.djfc.domain.zakelijk.AanhangerVerzekering;
import nl.lakedigital.djfc.domain.zakelijk.GeldVerzekering;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.repository.InkomendeOpdrachtRepository;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class PolisServiceTest extends EasyMockSupport {
    private final long entiteitId = 46L;
    private final SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;

    @TestSubject
    private PolisService polisService = new PolisService();

    @Mock
    private InkomendeOpdrachtRepository polisRepository;
    @Mock
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;


    //    @Test
    //    public void testBeeindigen() {
    //        Polis polis = createMock(Polis.class);
    //
    //        expect(polisRepository.lees(2L)).andReturn(polis);
    //        polis.setEindDatum(LocalDate.now());
    //        expectLastCall();
    //
    //        polisRepository.opslaan(polis);
    //        expectLastCall();
    //
    //        replayAll();
    //
    //        polisService.beeindigen(2L);
    //    }

    @Test
    public void testOpslaanPolis() {
        final Long id = 58L;

        AutoVerzekering polis = new AutoVerzekering(new nl.lakedigital.djfc.domain.Pakket(soortEntiteit, entiteitId));

        polisRepository.opslaan(polis);
        expectLastCall().andDelegateTo(new InkomendeOpdrachtRepository() {
            @Override
            public void opslaan(Polis o) {
                o.setId(id);
            }
        });

        //        expect(polisRepository.lees(id)).andReturn(polis);

        Capture<List> soortEntiteitEnEntiteitIdsCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(capture(soortEntiteitEnEntiteitIdsCapture));

        replayAll();

        polisService.opslaan(polis);

        List<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIds = soortEntiteitEnEntiteitIdsCapture.getValue();
        assertThat(soortEntiteitEnEntiteitIds.size(), is(1));
        assertThat(soortEntiteitEnEntiteitIds.get(0).getEntiteitId(), is(58L));
        assertThat(soortEntiteitEnEntiteitIds.get(0).getSoortEntiteit().name(), is(SoortEntiteit.POLIS.name()));

        verifyAll();
    }

    @Test
    public void testZoekOpPolisNummer() {
        Pakket pakket = new nl.lakedigital.djfc.domain.Pakket(soortEntiteit, entiteitId);
        CamperVerzekering camperVerzekering = new CamperVerzekering(pakket);

        expect(polisRepository.zoekOpPolisNummer("1234")).andReturn(newArrayList(pakket));

        replayAll();

        assertThat(polisService.zoekOpPolisNummer("1234"), is(newArrayList(pakket)));

        verifyAll();
    }

    @Test
    public void testZoekOpPolisNummerMetException() {
        expect(polisRepository.zoekOpPolisNummer("1234")).andThrow(new NoResultException());

        replayAll();

        assertNull(polisService.zoekOpPolisNummer("1234"));

        verifyAll();
    }


    @Test
    public void testVerwijder() {
        Pakket pakket = new nl.lakedigital.djfc.domain.Pakket(soortEntiteit, entiteitId);
        AutoVerzekering autoVerzekering = new AutoVerzekering(pakket);
        autoVerzekering.setId(1L);

        expect(polisRepository.lees(1L)).andReturn(pakket);

        polisRepository.verwijder(autoVerzekering);
        expectLastCall();

        replayAll();

        polisService.verwijder(1L);

        verifyAll();
    }

    @Test
    public void testAllePolisSoorten() {
        AnnuleringsVerzekering annuleringsVerzekering = new AnnuleringsVerzekering(new nl.lakedigital.djfc.domain.Pakket(soortEntiteit, entiteitId));
        InboedelVerzekering inboedelVerzekering = new InboedelVerzekering(new nl.lakedigital.djfc.domain.Pakket(soortEntiteit, entiteitId));

        AanhangerVerzekering aanhangerVerzekering = new AanhangerVerzekering(new nl.lakedigital.djfc.domain.Pakket(soortEntiteit, entiteitId));
        GeldVerzekering geldVerzekering = new GeldVerzekering(new nl.lakedigital.djfc.domain.Pakket(soortEntiteit, entiteitId));

        List<Polis> polissen = new ArrayList<>();
        polissen.add(annuleringsVerzekering);
        polissen.add(inboedelVerzekering);
        polissen.add(aanhangerVerzekering);
        polissen.add(geldVerzekering);

        Map<String, String> verwachtParticulier = new HashMap<>();
        verwachtParticulier.put(annuleringsVerzekering.getClass().getSimpleName().replace("Verzekering", ""), annuleringsVerzekering.getSchermNaam());
        verwachtParticulier.put(inboedelVerzekering.getClass().getSimpleName().replace("Verzekering", ""), inboedelVerzekering.getSchermNaam());

        Map<String, String> verwachtZakelijk = new HashMap<>();
        verwachtZakelijk.put(aanhangerVerzekering.getClass().getSimpleName().replace("Verzekering", ""), aanhangerVerzekering.getSchermNaam());
        verwachtZakelijk.put(geldVerzekering.getClass().getSimpleName().replace("Verzekering", ""), geldVerzekering.getSchermNaam());

        ReflectionTestUtils.setField(polisService, "polissen", polissen);

        replayAll();

        assertEquals(verwachtParticulier, polisService.allePolisSoorten(SoortVerzekering.PARTICULIER));
        assertEquals(verwachtZakelijk, polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK));

        verifyAll();
    }


    @Test
    public void testAlles() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 44L;

        //        List<Polis> polissen = new ArrayList<>();
        List<Pakket> pakketten = new ArrayList<>();

        expect(polisRepository.alles(soortEntiteit, entiteitId)).andReturn(pakketten);

        replayAll();

        assertThat(polisService.alles(soortEntiteit, entiteitId), is(pakketten));

        verifyAll();
    }
}