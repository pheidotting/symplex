package nl.dias.service;

import com.google.common.collect.Lists;
import nl.dias.domein.*;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.dias.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.dias.messaging.sender.VerwijderEntiteitenRequestSender;
import nl.dias.repository.GebruikerRepository;
import nl.dias.repository.KantoorRepository;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.oga.TelefoonnummerClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.easymock.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class GebruikerServiceTest extends EasyMockSupport {
    @TestSubject
    private GebruikerService gebruikerService = new GebruikerService();

    @Mock
    private GebruikerRepository gebruikerRepository;
    @Mock
    private PolisService polisService;
    @Mock
    private KantoorRepository kantoorRepository;
    @Mock
    private AdresClient adresClient;
    @Mock
    private TelefoonnummerClient telefoonnummerClient;
    @Mock
    private HypotheekService hypotheekService;
    @Mock
    private SchadeService schadeService;
    @Mock
    private VerwijderEntiteitenRequestSender verwijderEntiteitenRequestSender;
    @Mock
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;


    @After
    public void teardown() {
        verifyAll();
    }

    @Test
    public void testLees() {
        Gebruiker gebruiker = new Relatie();

        expect(gebruikerRepository.lees(1L)).andReturn(gebruiker);

        replayAll();

        assertEquals(gebruiker, gebruikerService.lees(1L));
    }

    @Test
    public void testLeesRelatie() {
        Relatie relatie = new Relatie();

        expect(gebruikerRepository.lees(1L)).andReturn(relatie);

        replayAll();

        assertEquals(relatie, gebruikerService.leesRelatie(1L));
    }

    @Test
    public void testAlleRelaties() {
        Kantoor kantoor = new Kantoor();
        Relatie relatie = new Relatie();
        List<Relatie> relaties = new ArrayList<>();
        relaties.add(relatie);

        expect(gebruikerRepository.alleRelaties(kantoor, true)).andReturn(relaties);

        replayAll();

        assertEquals(relaties, gebruikerService.alleRelaties(kantoor, true));
    }

    @Test
    public void testOpslaan() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Long relatieId = 46L;

        Relatie relatie = new Relatie();
        relatie.setIdentificatie("id");
        relatie.setId(relatieId);

        gebruikerRepository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetRekeningNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setIdentificatie("id");

        gebruikerRepository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetTelefoonNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setIdentificatie("id");

        gebruikerRepository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(relatie);
    }

    @Test
    public void testOpslaanZonderBsn() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setIdentificatie("id");

        gebruikerRepository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanZonderEmail() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);

        gebruikerRepository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanZonderAdres() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setIdentificatie("id");

        gebruikerRepository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanMetAdresMaarIncompleet() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setIdentificatie("id");

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        gebruikerRepository.opslaan(relatie);
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(relatie);
    }

    @Test
    public void testOpslaanMedewerker() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("identificatie");

        gebruikerRepository.opslaan(medewerker);
        expectLastCall();

        replayAll();

        gebruikerService.opslaan(medewerker);
    }

    @Test
    public void testVerwijder() {
        Relatie relatie = new Relatie();
        relatie.setId(1L);

        expect(gebruikerRepository.lees(1L)).andReturn(relatie);
        gebruikerRepository.verwijder(relatie);
        expectLastCall();

        List<Hypotheek> hypotheeks = newArrayList();
        expect(hypotheekService.allesVanRelatie(relatie.getId())).andReturn(hypotheeks);
        hypotheekService.verwijder(hypotheeks);
        expectLastCall();
        List<Schade> schades = newArrayList();
        expect(schadeService.alleSchadesBijRelatie(1L)).andReturn(schades);
        schadeService.verwijder(schades);
        expectLastCall();
        List<Polis> polises = newArrayList();
        expect(polisService.allePolissenBijRelatie(1L)).andReturn(polises);
        polisService.verwijder(polises);
        expectLastCall();

        Capture<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();

        verwijderEntiteitenRequestSender.send(capture(soortEntiteitEnEntiteitIdCapture));
        expectLastCall();

        replayAll();

        gebruikerService.verwijder(1L);

        assertThat(soortEntiteitEnEntiteitIdCapture.getValue().getSoortEntiteit(), is(SoortEntiteit.RELATIE));
        assertThat(soortEntiteitEnEntiteitIdCapture.getValue().getEntiteitId(), is(1L));
    }

    @Test
    public void testZoek() throws NietGevondenException {
        Medewerker medewerker = new Medewerker();

        expect(gebruikerRepository.zoek("e")).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, gebruikerService.zoek("e"));
    }

    @Test
    public void testZoekOpSessieEnIpAdres() throws NietGevondenException {
        String sessie = "sessie";
        String ipadres = "ipadres";

        Medewerker medewerker = new Medewerker();

        expect(gebruikerRepository.zoekOpSessieEnIpadres(sessie, ipadres)).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, gebruikerService.zoekOpSessieEnIpAdres(sessie, ipadres));
    }

    @Test
    public void testZoekOpNaamAdresOfPolisNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String zoekterm = "a";

        List<Gebruiker> relatiesZoekOpNaam = new ArrayList<Gebruiker>();
        Relatie relatieZoekOpNaam = new Relatie();
        relatieZoekOpNaam.setId(2L);
        relatieZoekOpNaam.setAchternaam("relatieZoekOpNaam");
        relatieZoekOpNaam.setIdentificatie("relatieZoekOpNaamId");
        relatiesZoekOpNaam.add(relatieZoekOpNaam);

        List<Relatie> relatiesZoekOpBedrijfsnaam = new ArrayList<>();
        Relatie relatieZoekOpBedrijfsnaam = new Relatie();
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("naamBedrijf");
        relatiesZoekOpBedrijfsnaam.add(relatieZoekOpBedrijfsnaam);

        Polis polis = new AutoVerzekering();
        Relatie relatiePolis = new Relatie();
        polis.setRelatie(8L);

        expect(gebruikerRepository.zoekOpNaam(zoekterm)).andReturn(relatiesZoekOpNaam);
        expect(gebruikerRepository.zoekRelatieOpRoepnaam(zoekterm)).andReturn(newArrayList());
        expect(adresClient.zoeken(zoekterm)).andReturn(Lists.<JsonAdres>newArrayList());
        expect(gebruikerRepository.lees(8L)).andReturn(relatiePolis);
        expect(polisService.zoekOpPolisNummer(zoekterm)).andReturn(polis);
        expect(gebruikerRepository.zoekRelatiesOpBedrijfsnaam("a")).andReturn(relatiesZoekOpBedrijfsnaam);

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieZoekOpNaam);
        relatiesVerwacht.add(relatiePolis);

        assertEquals(relatiesVerwacht.size(), gebruikerService.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
    }


    @Test
    public void testZoekOpNaamAdresOfPolisNummerMetTelefoonnummer() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String zoekterm = "06-12 345 678";

        Relatie relatieTelefoonnummer = new Relatie();
        List<Relatie> relatiesTelefoonnummer = new ArrayList<>();
        relatiesTelefoonnummer.add(relatieTelefoonnummer);
        JsonTelefoonnummer jsonTelefoonnummer = new JsonTelefoonnummer();
        jsonTelefoonnummer.setEntiteitId(888L);
        jsonTelefoonnummer.setSoortEntiteit("RELATIE");

        expect(gebruikerRepository.zoekRelatieOpRoepnaam(zoekterm)).andReturn(newArrayList());
        expect(adresClient.zoeken(zoekterm)).andReturn(Lists.<JsonAdres>newArrayList());
        expect(telefoonnummerClient.zoeken(zoekterm)).andReturn(newArrayList(jsonTelefoonnummer));
        expect(gebruikerRepository.lees(888L)).andReturn(relatieTelefoonnummer).times(2);

        expect(gebruikerRepository.zoekOpNaam(zoekterm)).andReturn(new ArrayList<Gebruiker>());
        expect(polisService.zoekOpPolisNummer(zoekterm)).andReturn(null);
        expect(gebruikerRepository.zoekRelatiesOpBedrijfsnaam(zoekterm)).andReturn(new ArrayList<Relatie>());

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieTelefoonnummer);

        assertEquals(relatiesVerwacht.size(), gebruikerService.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
    }

    @Test
    public void testZoekOpNaamAdresOfPolisNummerZonderPolis() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String zoekterm = "a";

        List<Gebruiker> relatiesZoekOpNaam = new ArrayList<Gebruiker>();
        Relatie relatieZoekOpNaam = new Relatie();
        relatieZoekOpNaam.setAchternaam("relatieZoekOpNaam");
        relatieZoekOpNaam.setId(2L);
        relatieZoekOpNaam.setIdentificatie("relatieZoekOpNaamId");
        relatiesZoekOpNaam.add(relatieZoekOpNaam);

        List<Relatie> relatiesZoekOpRoepnaam = new ArrayList<>();
        Relatie relatieZoekOpRoppnaam = new Relatie();
        relatiesZoekOpRoepnaam.add(relatieZoekOpRoppnaam);

        Relatie relatieZoekOpAdres = new Relatie();
        relatieZoekOpAdres.setAchternaam("relatieZoekOpAdres");
        relatieZoekOpAdres.setId(23L);
        relatieZoekOpAdres.setIdentificatie("relatieZoekOpAdresId");

        List<Relatie> relatiesZoekOpBedrijfsnaam = new ArrayList<>();
        Relatie relatieZoekOpBedrijfsnaam = new Relatie();
        Bedrijf bedrijf = new Bedrijf();
        bedrijf.setNaam("naamBedrijf");
        relatiesZoekOpBedrijfsnaam.add(relatieZoekOpBedrijfsnaam);

        JsonAdres adres = new JsonAdres();
        adres.setEntiteitId(23L);
        adres.setSoortEntiteit("RELATIE");

        expect(gebruikerRepository.zoekOpNaam(zoekterm)).andReturn(relatiesZoekOpNaam);
        expect(gebruikerRepository.zoekRelatieOpRoepnaam(zoekterm)).andReturn(relatiesZoekOpRoepnaam);
        expect(adresClient.zoeken(zoekterm)).andReturn(newArrayList(adres));
        expect(gebruikerRepository.lees(23L)).andReturn(relatieZoekOpAdres).times(2);
        expect(polisService.zoekOpPolisNummer(zoekterm)).andReturn(null);
        expect(gebruikerRepository.zoekRelatiesOpBedrijfsnaam("a")).andReturn(relatiesZoekOpBedrijfsnaam);

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieZoekOpNaam);
        relatiesVerwacht.add(relatieZoekOpAdres);
        relatiesVerwacht.add(relatieZoekOpBedrijfsnaam);

        assertEquals(relatiesVerwacht.size(), gebruikerService.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
    }

    @Test
    public void alleRelaties() {
        Relatie relatie = new Relatie();
        List<Relatie> relatieList = newArrayList(relatie);

        expect(gebruikerRepository.alleRelaties()).andReturn(relatieList);

        replayAll();

        assertThat(gebruikerService.alleRelaties(), is(relatieList));

        verifyAll();
    }
}
