package nl.dias.service;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.dias.domein.Schade;
import nl.dias.domein.polis.Polis;
import nl.dias.messaging.sender.VerwijderEntiteitenRequestSender;
import nl.dias.repository.BedrijfRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import org.easymock.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class BedrijfServiceTest extends EasyMockSupport {
    @TestSubject
    private BedrijfService bedrijfService = new BedrijfService();
    @Mock
    private BedrijfRepository bedrijfRepository;
    @Mock
    private VerwijderEntiteitenRequestSender verwijderEntiteitenRequestSender;
    @Mock
    private SchadeService schadeService;
    @Mock
    private PolisRepository polisRepository;

    @After
    public void afterTest() {
        verifyAll();
    }

    @Test
    public void testOpslaan() {
        Bedrijf bedrijf = new Bedrijf();

        bedrijfRepository.opslaan(bedrijf);
        expectLastCall();

        replayAll();

        bedrijfService.opslaan(bedrijf);
    }

    @Test
    public void testLees() {
        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfRepository.lees(1L)).andReturn(bedrijf);

        replayAll();

        assertEquals(bedrijf, bedrijfService.lees(1L));
    }

    @Test
    public void testAlleBedrijvenBijRelatie() {
        List<Bedrijf> bedrijven = new ArrayList<>();
        Relatie relatie = new Relatie();

        expect(bedrijfRepository.alleBedrijvenBijRelatie(relatie)).andReturn(bedrijven);

        replayAll();

        assertEquals(bedrijven, bedrijfService.alleBedrijvenBijRelatie(relatie));
    }

    @Test
    public void testVerwijder() {
        Long id = 69L;
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setId(id);

        expect(bedrijfRepository.lees(id)).andReturn(bedrijf);

        List<Schade> schades = newArrayList();
        expect(schadeService.alleSchadesBijBedrijf(id)).andReturn(schades);
        schadeService.verwijder(schades);
        expectLastCall();

        List<Polis> polises = newArrayList();
        expect(polisRepository.allePolissenBijBedrijf(id)).andReturn(polises);
        polisRepository.verwijder(polises);
        expectLastCall();

        bedrijfRepository.verwijder(bedrijf);
        expectLastCall();

        Capture<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();

        verwijderEntiteitenRequestSender.send(capture(soortEntiteitEnEntiteitIdCapture));
        expectLastCall();

        replayAll();

        bedrijfService.verwijder(id);

        assertThat(soortEntiteitEnEntiteitIdCapture.getValue().getSoortEntiteit(), is(SoortEntiteit.BEDRIJF));
        assertThat(soortEntiteitEnEntiteitIdCapture.getValue().getEntiteitId(), is(id));
    }
}