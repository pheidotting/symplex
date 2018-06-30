package nl.dias.service;

import nl.dias.domein.*;
import nl.dias.domein.polis.AutoVerzekering;
import nl.lakedigital.djfc.client.taak.TaakClient;
import nl.lakedigital.djfc.commons.json.Taak;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(EasyMockRunner.class)
public class OpenstaandeTaakServiceTest extends EasyMockSupport {
    @TestSubject
    private OpenstaandeTaakService openstaandeTaakService = new OpenstaandeTaakService();

    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private BedrijfService bedrijfService;
    @Mock
    private PolisService polisService;
    @Mock
    private SchadeService schadeService;
    @Mock
    private HypotheekService hypotheekService;
    @Mock
    private TaakClient taakClient;

    @Test
    public void testAlleOpenstaandeTaken() {
        Kantoor kantoor = new Kantoor();
        Relatie relatie = new Relatie();
        relatie.setId(4L);
        Taak taakRelatie = new Taak();
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setId(8L);
        Taak taakBedrijf = new Taak();
        AutoVerzekering autoVerzekering = new AutoVerzekering();
        autoVerzekering.setId(5L);
        Taak taakPolis = new Taak();
        Schade schade = new Schade();
        schade.setId(6L);
        Taak taakSchade = new Taak();
        Hypotheek hypotheek = new Hypotheek();
        hypotheek.setId(7L);
        Taak taakHypotheek = new Taak();
        AutoVerzekering autoVerzekering1 = new AutoVerzekering();
        autoVerzekering1.setId(9L);
        Taak taakPolis1 = new Taak();
        Schade schade1 = new Schade();
        schade1.setId(10L);
        Taak taakSchade1 = new Taak();

        expect(gebruikerService.alleRelaties(kantoor, true)).andReturn(newArrayList(relatie));
        expect(taakClient.alles("RELATIE", relatie.getId())).andReturn(newArrayList(taakRelatie));

        expect(polisService.allePolissenBijRelatie(relatie.getId())).andReturn(newArrayList(autoVerzekering));
        expect(taakClient.alles("POLIS", autoVerzekering.getId())).andReturn(newArrayList(taakPolis));

        expect(schadeService.alleSchadesBijPolis(autoVerzekering.getId())).andReturn(newArrayList(schade));
        expect(taakClient.alles("SCHADE", schade.getId())).andReturn(newArrayList(taakSchade));

        expect(hypotheekService.allesVanRelatie(relatie.getId())).andReturn(newArrayList(hypotheek));
        expect(taakClient.alles("HYPOTHEEK", hypotheek.getId())).andReturn(newArrayList(taakHypotheek));


        expect(bedrijfService.alles()).andReturn(newArrayList(bedrijf));
        expect(taakClient.alles("BEDRIJF", bedrijf.getId())).andReturn(newArrayList(taakBedrijf));

        expect(polisService.allePolissenBijBedrijf(bedrijf.getId())).andReturn(newArrayList(autoVerzekering1));
        expect(taakClient.alles("POLIS", autoVerzekering1.getId())).andReturn(newArrayList(taakPolis1));

        expect(schadeService.alleSchadesBijPolis(autoVerzekering1.getId())).andReturn(newArrayList(schade1));
        expect(taakClient.alles("SCHADE", schade1.getId())).andReturn(newArrayList(taakSchade1));

        replayAll();

        List<Taak> taken = openstaandeTaakService.alleOpenstaandeTaken(kantoor);

        verifyAll();

        assertThat(taken.size(), is(7));
        assertTrue(taken.contains(taakRelatie));
        assertTrue(taken.contains(taakBedrijf));
        assertTrue(taken.contains(taakPolis));
        assertTrue(taken.contains(taakPolis1));
        assertTrue(taken.contains(taakSchade));
        assertTrue(taken.contains(taakSchade1));
        assertTrue(taken.contains(taakHypotheek));
    }
}