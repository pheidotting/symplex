package nl.dias.service;

import com.google.common.collect.Lists;
import nl.dias.domein.*;
import nl.dias.domein.polis.AutoVerzekering;
import nl.dias.domein.polis.Polis;
import nl.dias.messaging.SoortEntiteitEnEntiteitId;
import nl.dias.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.dias.messaging.sender.VerwijderEntiteitenRequestSender;
import nl.dias.repository.GebruikerRepository;
import nl.dias.repository.HypotheekRepository;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import nl.lakedigital.as.messaging.domain.SoortEntiteit;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.oga.TelefoonnummerClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.easymock.*;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class GebruikerServiceTest extends EasyMockSupport {
    @TestSubject
    private GebruikerService service = new GebruikerService();

    @Mock
    private GebruikerRepository repository;
    @Mock
    private PolisRepository polisRepository;
    @Mock
    private KantoorRepository kantoorRepository;
    @Mock
    private AdresClient adresClient;
    @Mock
    private TelefoonnummerClient telefoonnummerClient;
    @Mock
    private HypotheekRepository hypotheekRepository;
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

        expect(repository.lees(1L)).andReturn(gebruiker);

        replayAll();

        assertEquals(gebruiker, service.lees(1L));
    }

    @Test
    public void testLeesRelatie() {
        Relatie relatie = new Relatie();

        expect(repository.lees(1L)).andReturn(relatie);

        replayAll();

        assertEquals(relatie, service.leesRelatie(1L));
    }

    @Test
    public void testAlleRelaties() {
        Kantoor kantoor = new Kantoor();
        Relatie relatie = new Relatie();
        List<Relatie> relaties = new ArrayList<>();
        relaties.add(relatie);

        expect(repository.alleRelaties(kantoor)).andReturn(relaties);

        replayAll();

        assertEquals(relaties, service.alleRelaties(kantoor));
    }

    @Test
    public void testOpslaan() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Long relatieId = 46L;

        Relatie relatie = new Relatie();
        relatie.setBsn("1234");
        relatie.setIdentificatie("id");
        relatie.setId(relatieId);

        repository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetRekeningNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setBsn("1234");
        relatie.setIdentificatie("id");

        repository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMetTelefoonNummer() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setBsn("1234");
        relatie.setIdentificatie("id");

        repository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanZonderBsn() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setIdentificatie("id");

        repository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        service.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanZonderEmail() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setBsn("id");

        repository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        service.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanZonderAdres() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setBsn("bsn");
        relatie.setIdentificatie("id");

        repository.opslaan(relatie);
        expectLastCall();

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        replayAll();

        service.opslaan(relatie);

        verifyAll();
    }

    @Test
    public void testOpslaanMetAdresMaarIncompleet() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Relatie relatie = new Relatie();
        relatie.setId(2L);
        relatie.setBsn("bsn");
        relatie.setIdentificatie("id");

        Capture<nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();
        entiteitenOpgeslagenRequestSender.send(newArrayList(capture(soortEntiteitEnEntiteitIdCapture)));
        expectLastCall();

        repository.opslaan(relatie);
        expectLastCall();

        replayAll();

        service.opslaan(relatie);
    }

    @Test
    public void testOpslaanMedewerker() throws UnsupportedEncodingException, NoSuchAlgorithmException, NietGevondenException {
        Medewerker medewerker = new Medewerker();
        medewerker.setIdentificatie("identificatie");

        repository.opslaan(medewerker);
        expectLastCall();

        replayAll();

        service.opslaan(medewerker);
    }

    @Test
    public void testVerwijder() {
        Relatie relatie = new Relatie();
        relatie.setId(1L);

        expect(repository.lees(1L)).andReturn(relatie);
        repository.verwijder(relatie);
        expectLastCall();

        List<Hypotheek> hypotheeks = newArrayList();
        expect(hypotheekRepository.allesVanRelatie(relatie)).andReturn(hypotheeks);
        hypotheekRepository.verwijder(hypotheeks);
        expectLastCall();
        List<Schade> schades = newArrayList();
        expect(schadeService.alleSchadesBijRelatie(1L)).andReturn(schades);
        schadeService.verwijder(schades);
        expectLastCall();
        List<Polis> polises = newArrayList();
        expect(polisRepository.allePolissenBijRelatie(1L)).andReturn(polises);
        polisRepository.verwijder(polises);
        expectLastCall();

        Capture<SoortEntiteitEnEntiteitId> soortEntiteitEnEntiteitIdCapture = newCapture();

        verwijderEntiteitenRequestSender.send(capture(soortEntiteitEnEntiteitIdCapture));
        expectLastCall();

        replayAll();

        service.verwijder(1L);

        assertThat(soortEntiteitEnEntiteitIdCapture.getValue().getSoortEntiteit(), is(SoortEntiteit.RELATIE));
        assertThat(soortEntiteitEnEntiteitIdCapture.getValue().getEntiteitId(), is(1L));
    }

    @Test
    public void testZoek() throws NietGevondenException {
        Medewerker medewerker = new Medewerker();

        expect(repository.zoek("e")).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoek("e"));
    }

    @Test
    public void testZoekOpSessieEnIpAdres() throws NietGevondenException {
        String sessie = "sessie";
        String ipadres = "ipadres";

        Medewerker medewerker = new Medewerker();

        expect(repository.zoekOpSessieEnIpadres(sessie, ipadres)).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoekOpSessieEnIpAdres(sessie, ipadres));
    }

    @Test
    public void testZoekSessieOpOpSessieEnIpadres() {
        Sessie sessie1 = new Sessie();
        Sessie sessie2 = new Sessie();
        Sessie sessie3 = new Sessie();

        sessie1.setSessie("sessie1");
        sessie1.setIpadres("ipadres1");
        sessie2.setSessie("sessie2");
        sessie2.setIpadres("ipadres2");
        sessie3.setSessie("sessie3");
        sessie3.setIpadres("ipadres3");

        Relatie relatie = new Relatie();
        relatie.getSessies().add(sessie1);
        relatie.getSessies().add(sessie2);
        relatie.getSessies().add(sessie3);

        replayAll();

        assertEquals(sessie1, service.zoekSessieOp("sessie1", "ipadres1", relatie.getSessies()));
        assertEquals(sessie2, service.zoekSessieOp("sessie2", "ipadres2", relatie.getSessies()));
        assertEquals(sessie3, service.zoekSessieOp("sessie3", "ipadres3", relatie.getSessies()));
        assertNull(service.zoekSessieOp("sessie", "ipadres1", relatie.getSessies()));
        assertNull(service.zoekSessieOp("sessie1", "ipadres", relatie.getSessies()));
    }

    @Test
    public void testZoekSessieOpCookie() {
        Sessie sessie1 = new Sessie();
        Sessie sessie2 = new Sessie();
        Sessie sessie3 = new Sessie();

        sessie1.setCookieCode("cookieCode1");
        sessie2.setCookieCode("cookieCode2");
        sessie3.setCookieCode("cookieCode3");

        Relatie relatie = new Relatie();
        relatie.getSessies().add(sessie1);
        relatie.getSessies().add(sessie2);
        relatie.getSessies().add(sessie3);

        replayAll();

        assertEquals(sessie1, service.zoekSessieOp("cookieCode1", relatie.getSessies()));
        assertEquals(sessie2, service.zoekSessieOp("cookieCode2", relatie.getSessies()));
        assertEquals(sessie3, service.zoekSessieOp("cookieCode3", relatie.getSessies()));
        assertNull(service.zoekSessieOp("cookieCode", relatie.getSessies()));
    }

    @Test
    public void testZoekOpCookieCode() throws NietGevondenException {
        Medewerker medewerker = new Medewerker();
        String cookieCode = "cookieCode";

        expect(repository.zoekOpCookieCode(cookieCode)).andReturn(medewerker);

        replayAll();

        assertEquals(medewerker, service.zoekOpCookieCode(cookieCode));
    }

    @Test
    public void testZoekOpCookieCodeNietGevondenException() throws NietGevondenException {
        String cookieCode = "cookieCode";

        expect(repository.zoekOpCookieCode(cookieCode)).andThrow(new NietGevondenException("wie"));

        replayAll();

        service.zoekOpCookieCode(cookieCode);
    }

    @Test
    public void refresh() {
        Sessie sessie = new Sessie();

        repository.refresh(sessie);
        expectLastCall();

        replayAll();

        service.refresh(sessie);
    }

    @Test
    public void opslaan() {
        Sessie sessie = new Sessie();

        repository.opslaan(sessie);
        expectLastCall();

        replayAll();

        service.opslaan(sessie);
    }

    @Test
    public void verwijder() {
        Sessie sessie = new Sessie();

        repository.verwijder(sessie);
        expectLastCall();

        replayAll();

        service.verwijder(sessie);
    }

    @Test
    public void verwijderVerlopenSessies() {
        Long id = 46L;
        Medewerker medewerker = createMock(Medewerker.class);

        expect(medewerker.getId()).andReturn(id);

        expect(repository.lees(id)).andReturn(medewerker);

        Sessie sessie1 = createMock(Sessie.class);
        Sessie sessie2 = createMock(Sessie.class);
        Sessie sessie3 = createMock(Sessie.class);

        Set<Sessie> sessies = new HashSet<>();
        sessies.add(sessie1);
        sessies.add(sessie2);
        sessies.add(sessie3);

        expect(sessie1.getDatumLaatstGebruikt()).andReturn(new LocalDate().minusDays(2));
        expect(sessie2.getDatumLaatstGebruikt()).andReturn(new LocalDate().minusDays(3));
        expect(sessie3.getDatumLaatstGebruikt()).andReturn(new LocalDate().minusDays(4));

        expect(sessie1.getBrowser()).andReturn("mozilla");
        expect(sessie2.getBrowser()).andReturn("curl");

        expect(medewerker.getSessies()).andReturn(sessies).times(3);

        repository.opslaan(medewerker);
        expectLastCall();

        replayAll();

        service.verwijderVerlopenSessies(medewerker);
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

        expect(repository.zoekOpNaam(zoekterm)).andReturn(relatiesZoekOpNaam);
        expect(adresClient.zoeken(zoekterm)).andReturn(Lists.<JsonAdres>newArrayList());
        expect(repository.lees(8L)).andReturn(relatiePolis);
        expect(polisRepository.zoekOpPolisNummer(zoekterm, null)).andReturn(polis);
        expect(repository.zoekRelatiesOpBedrijfsnaam("a")).andReturn(relatiesZoekOpBedrijfsnaam);

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieZoekOpNaam);
        relatiesVerwacht.add(relatiePolis);

        assertEquals(relatiesVerwacht.size(), service.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
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

        expect(adresClient.zoeken(zoekterm)).andReturn(Lists.<JsonAdres>newArrayList());
        expect(telefoonnummerClient.zoeken(zoekterm)).andReturn(newArrayList(jsonTelefoonnummer));
        expect(repository.lees(888L)).andReturn(relatieTelefoonnummer).times(2);

        expect(repository.zoekOpNaam(zoekterm)).andReturn(new ArrayList<Gebruiker>());
        expect(polisRepository.zoekOpPolisNummer(zoekterm, null)).andReturn(null);
        expect(repository.zoekRelatiesOpBedrijfsnaam(zoekterm)).andReturn(new ArrayList<Relatie>());

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieTelefoonnummer);

        assertEquals(relatiesVerwacht.size(), service.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
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

        expect(repository.zoekOpNaam(zoekterm)).andReturn(relatiesZoekOpNaam);
        expect(adresClient.zoeken(zoekterm)).andReturn(newArrayList(adres));
        expect(repository.lees(23L)).andReturn(relatieZoekOpAdres).times(2);
        expect(polisRepository.zoekOpPolisNummer(zoekterm, null)).andReturn(null);
        expect(repository.zoekRelatiesOpBedrijfsnaam("a")).andReturn(relatiesZoekOpBedrijfsnaam);

        replayAll();

        List<Relatie> relatiesVerwacht = new ArrayList<>();
        relatiesVerwacht.add(relatieZoekOpNaam);
        relatiesVerwacht.add(relatieZoekOpAdres);
        relatiesVerwacht.add(relatieZoekOpBedrijfsnaam);

        assertEquals(relatiesVerwacht.size(), service.zoekOpNaamAdresOfPolisNummer(zoekterm).size());
    }

    @Test
    public void testKoppelenOnderlingeRelatie() {
        Relatie relatie1 = new Relatie();
        relatie1.setId(1L);
        Relatie relatie2 = new Relatie();
        relatie2.setId(2L);

        expect(repository.lees(relatie1.getId())).andReturn(relatie1);
        expect(repository.lees(relatie2.getId())).andReturn(relatie2);

        Capture<Relatie> relatieCapture1 = newCapture();
        Capture<Relatie> relatieCapture2 = newCapture();

        repository.opslaan(capture(relatieCapture1));
        repository.opslaan(capture(relatieCapture2));

        replayAll();

        service.koppelenOnderlingeRelatie(relatie1.getId(), relatie2.getId(), "O");

        Relatie relatie1Opgeslagen = relatieCapture1.getValue();
        Relatie relatie2Opgeslagen = relatieCapture2.getValue();

        assertEquals(1, relatie1Opgeslagen.getOnderlingeRelaties().size());
        OnderlingeRelatie onderlingeRelatie1 = relatie1Opgeslagen.getOnderlingeRelaties().iterator().next();
        assertEquals(relatie2, onderlingeRelatie1.getRelatieMet());
        assertEquals(OnderlingeRelatieSoort.O, onderlingeRelatie1.getOnderlingeRelatieSoort());
        assertEquals(1, relatie2Opgeslagen.getOnderlingeRelaties().size());
        OnderlingeRelatie onderlingeRelatie2 = relatie2Opgeslagen.getOnderlingeRelaties().iterator().next();
        assertEquals(relatie1, onderlingeRelatie2.getRelatieMet());
        assertEquals(OnderlingeRelatieSoort.K, onderlingeRelatie2.getOnderlingeRelatieSoort());
    }
}
