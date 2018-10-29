//package nl.dias.service;
//
//import nl.dias.domein.*;
//import nl.dias.domein.polis.AutoVerzekering;
//import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
//import nl.lakedigital.djfc.client.taak.TaakClient;
//import nl.lakedigital.djfc.commons.json.Identificatie;
//import nl.lakedigital.djfc.commons.json.Taak;
//import org.easymock.EasyMockRunner;
//import org.easymock.EasyMockSupport;
//import org.easymock.Mock;
//import org.easymock.TestSubject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.List;
//import java.util.UUID;
//
//import static com.google.common.collect.Lists.newArrayList;
//import static org.easymock.EasyMock.expect;
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(EasyMockRunner.class)
//public class OpenstaandeTaakServiceTest extends EasyMockSupport {
//    @TestSubject
//    private OpenstaandeTaakService openstaandeTaakService = new OpenstaandeTaakService();
//
//    @Mock
//    private GebruikerService gebruikerService;
//    @Mock
//    private BedrijfService bedrijfService;
//    @Mock
//    private PolisService polisService;
//    @Mock
//    private SchadeService schadeService;
//    @Mock
//    private HypotheekService hypotheekService;
//    @Mock
//    private TaakClient taakClient;
//    @Mock
//    private IdentificatieClient identificatieClient;
//
//    @Test
//    public void testAlleOpenstaandeTaken() {
//        Kantoor kantoor = new Kantoor();
//        Relatie relatie = new Relatie();
//        relatie.setId(4L);
//        Taak taakRelatie = new Taak();
//        taakRelatie.setId(99L);
//        Identificatie taakRelatieIdentificatie = new Identificatie();
//        taakRelatieIdentificatie.setIdentificatie(UUID.randomUUID().toString());
//        Bedrijf bedrijf = new Bedrijf();
//        bedrijf.setId(8L);
//        Taak taakBedrijf = new Taak();
//        taakBedrijf.setId(98L);
//        Identificatie taakBedrijfIdentificatie = new Identificatie();
//        taakBedrijfIdentificatie.setIdentificatie(UUID.randomUUID().toString());
//
//        AutoVerzekering autoVerzekering = new AutoVerzekering();
//        autoVerzekering.setId(5L);
//        Taak taakPolis = new Taak();
//        taakPolis.setId(97L);
//        Identificatie taakPolisIdentificatie = new Identificatie();
//        taakPolisIdentificatie.setIdentificatie(UUID.randomUUID().toString());
//
//        Schade schade = new Schade();
//        schade.setId(6L);
//        Taak taakSchade = new Taak();
//        taakSchade.setId(96L);
//        Identificatie taakSchadeIdentificatie = new Identificatie();
//        taakSchadeIdentificatie.setIdentificatie(UUID.randomUUID().toString());
//
//        Hypotheek hypotheek = new Hypotheek();
//        hypotheek.setId(7L);
//        Taak taakHypotheek = new Taak();
//        taakHypotheek.setId(95L);
//        Identificatie taakHypotheekIdentificatie = new Identificatie();
//        taakHypotheekIdentificatie.setIdentificatie(UUID.randomUUID().toString());
//
//        AutoVerzekering autoVerzekering1 = new AutoVerzekering();
//        autoVerzekering1.setId(9L);
//        Taak taakPolis1 = new Taak();
//        taakPolis1.setId(94L);
//        Identificatie taakPolis1Identificatie = new Identificatie();
//        taakPolis1Identificatie.setIdentificatie(UUID.randomUUID().toString());
//
//        Schade schade1 = new Schade();
//        schade1.setId(10L);
//        Taak taakSchade1 = new Taak();
//        taakSchade1.setId(93L);
//        Identificatie taakSchade1Identificatie = new Identificatie();
//        taakSchade1Identificatie.setIdentificatie(UUID.randomUUID().toString());
//
//        expect(gebruikerService.alleRelaties(kantoor, true)).andReturn(newArrayList(relatie));
//        expect(taakClient.alles("RELATIE", relatie.getId())).andReturn(newArrayList(taakRelatie));
//        expect(identificatieClient.zoekIdentificatie("TAAK", taakRelatie.getId())).andReturn(taakRelatieIdentificatie);
//
//        expect(polisService.allePolissenBijRelatie(relatie.getId())).andReturn(newArrayList(autoVerzekering));
//        expect(taakClient.alles("POLIS", autoVerzekering.getId())).andReturn(newArrayList(taakPolis));
//        expect(identificatieClient.zoekIdentificatie("TAAK", taakPolis.getId())).andReturn(taakPolisIdentificatie);
//
//        expect(schadeService.alleSchadesBijPolis(autoVerzekering.getId())).andReturn(newArrayList(schade));
//        expect(taakClient.alles("SCHADE", schade.getId())).andReturn(newArrayList(taakSchade));
//        expect(identificatieClient.zoekIdentificatie("TAAK", taakSchade.getId())).andReturn(taakSchadeIdentificatie);
//
//        expect(hypotheekService.allesVanRelatie(relatie.getId())).andReturn(newArrayList(hypotheek));
//        expect(taakClient.alles("HYPOTHEEK", hypotheek.getId())).andReturn(newArrayList(taakHypotheek));
//        expect(identificatieClient.zoekIdentificatie("TAAK", taakHypotheek.getId())).andReturn(taakHypotheekIdentificatie);
//
//
//        expect(bedrijfService.alles()).andReturn(newArrayList(bedrijf));
//        expect(taakClient.alles("BEDRIJF", bedrijf.getId())).andReturn(newArrayList(taakBedrijf));
//        expect(identificatieClient.zoekIdentificatie("TAAK", taakBedrijf.getId())).andReturn(taakBedrijfIdentificatie);
//
//        expect(polisService.allePolissenBijBedrijf(bedrijf.getId())).andReturn(newArrayList(autoVerzekering1));
//        expect(taakClient.alles("POLIS", autoVerzekering1.getId())).andReturn(newArrayList(taakPolis1));
//        expect(identificatieClient.zoekIdentificatie("TAAK", taakPolis1.getId())).andReturn(taakPolis1Identificatie);
//
//        expect(schadeService.alleSchadesBijPolis(autoVerzekering1.getId())).andReturn(newArrayList(schade1));
//        expect(taakClient.alles("SCHADE", schade1.getId())).andReturn(newArrayList(taakSchade1));
//        expect(identificatieClient.zoekIdentificatie("TAAK", taakSchade1.getId())).andReturn(taakSchade1Identificatie);
//
//        replayAll();
//
//        List<Taak> taken = openstaandeTaakService.alleOpenstaandeTaken(kantoor);
//
//        verifyAll();
//
//        assertThat(taken.size(), is(7));
//        assertTrue(taken.contains(taakRelatie));
//        assertTrue(taken.contains(taakBedrijf));
//        assertTrue(taken.contains(taakPolis));
//        assertTrue(taken.contains(taakPolis1));
//        assertTrue(taken.contains(taakSchade));
//        assertTrue(taken.contains(taakSchade1));
//        assertTrue(taken.contains(taakHypotheek));
//    }
//}