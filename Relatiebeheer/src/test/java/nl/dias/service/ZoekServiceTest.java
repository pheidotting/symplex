package nl.dias.service;

import nl.dias.ZoekResultaat;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Gebruiker;
import nl.dias.domein.Relatie;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.client.polisadministratie.SchadeClient;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class ZoekServiceTest extends EasyMockSupport {
    @TestSubject
    private ZoekService zoekService = new ZoekService();

    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private BedrijfService bedrijfService;
    @Mock
    private PolisClient polisClient;
    @Mock
    private SchadeClient schadeClient;
    @Mock
    private AdresClient adresClient;

    @Test
    public void testZoekOpNaam() {
        String naam = "deNaam";

        List<Gebruiker> relaties = newArrayList();
        relaties.add(new Relatie());
        expect(gebruikerService.zoekOpNaam(naam)).andReturn(relaties);

        List<Bedrijf> bedrijven = newArrayList();
        bedrijven.add(new Bedrijf());
        expect(bedrijfService.zoekOpNaam(naam)).andReturn(bedrijven);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(naam, null, null, null, null, null, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(1));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void testZoekOpGeboortedatum() {
        LocalDate geboortedatum = LocalDate.now();

        List<Relatie> relaties = newArrayList();
        relaties.add(new Relatie());
        expect(gebruikerService.zoekOpGeboortedatum(geboortedatum)).andReturn(relaties);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(null, geboortedatum, null, null, null, null, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(0));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void testZoekOpTussenvoegsel() {
        String tussenvoegsel = "van";

        List<Relatie> relaties = newArrayList();
        relaties.add(new Relatie());
        expect(gebruikerService.zoekOpTussenVoegsel(tussenvoegsel)).andReturn(relaties);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(null, null, tussenvoegsel, null, null, null, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(0));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void testZoekOpPolisnummerRelatie() {
        String polisnummer = "123";
        Long relatieId = 2L;

        JsonPolis polis = new JsonPolis();
        polis.setEntiteitId(relatieId);
        polis.setSoortEntiteit("RELATIE");

        expect(polisClient.zoekOpPolisNummer(polisnummer)).andReturn(newArrayList(polis));

        Relatie relatie = new Relatie();

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(null, null, null, polisnummer, null, null, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(0));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void testZoekOpPolisnummerBedrijf() {
        String polisnummer = "123";
        Long bedrijfsId = 3L;

        JsonPolis polis = new JsonPolis();
        polis.setEntiteitId(bedrijfsId);
        polis.setSoortEntiteit("BEDRIJF");

        expect(polisClient.zoekOpPolisNummer(polisnummer)).andReturn(newArrayList(polis));

        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfService.lees(bedrijfsId)).andReturn(bedrijf);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(null, null, null, polisnummer, null, null, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(1));
        assertThat(resultaat.getRelaties().size(), is(0));

        verifyAll();
    }

    @Test
    public void testZoekOpVoorletters() {
        String voorletters = "ae";

        List<Relatie> relaties = newArrayList();
        relaties.add(new Relatie());
        expect(gebruikerService.zoekOpVoorletters(voorletters)).andReturn(relaties);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(null, null, null, null, voorletters, null, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(0));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void testZoekOpSchadenummerRelatie() {
        String schadenummer = "234";
        String polisId = "4";
        Long relatieId = 2L;

        JsonSchade schade = new JsonSchade();
        schade.setPolis(polisId);

        expect(schadeClient.zoekOpSchadeNummerMaatschappij(schadenummer)).andReturn(newArrayList(schade));

        JsonPolis polis = new JsonPolis();
        polis.setEntiteitId(relatieId);
        polis.setSoortEntiteit("RELATIE");

        expect(polisClient.lees(polisId)).andReturn(polis);

        Relatie relatie = new Relatie();

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(null, null, null, null, null, schadenummer, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(0));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void testZoekOpSchadenummerBedrijf() {
        String schadenummer = "234";
        String polisId = "5";
        Long bedrijfsId = 2L;

        JsonSchade schade = new JsonSchade();
        schade.setPolis(polisId);

        expect(schadeClient.zoekOpSchadeNummerMaatschappij(schadenummer)).andReturn(newArrayList(schade));

        JsonPolis polis = new JsonPolis();
        polis.setEntiteitId(bedrijfsId);
        polis.setSoortEntiteit("BEDRIJF");

        expect(polisClient.lees(polisId)).andReturn(polis);

        Bedrijf bedrijf = new Bedrijf();

        expect(bedrijfService.lees(bedrijfsId)).andReturn(bedrijf);

        replayAll();

        ZoekResultaat resultaat = zoekService.zoek(null, null, null, null, null, schadenummer, null, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(1));
        assertThat(resultaat.getRelaties().size(), is(0));

        verifyAll();
    }

    @Test
    public void zoekOpAdres() {
        String adresTekst = "aa";
        Long relatieId = 5L;
        Long bedrijfsId = 6L;

        JsonAdres adres1 = new JsonAdres();
        adres1.setSoortEntiteit("RELATIE");
        adres1.setEntiteitId(relatieId);

        JsonAdres adres2 = new JsonAdres();
        adres2.setSoortEntiteit("BEDRIJF");
        adres2.setEntiteitId(bedrijfsId);

        List<JsonAdres> adressen = newArrayList(adres1, adres2);

        expect(adresClient.zoekOpAdres(adresTekst)).andReturn(adressen);

        expect(gebruikerService.lees(relatieId)).andReturn(new Relatie());
        expect(bedrijfService.lees(bedrijfsId)).andReturn(new Bedrijf());

        replayAll();


        ZoekResultaat resultaat = zoekService.zoek(null, null, null, null, null, null, adresTekst, null, null, null);
        assertThat(resultaat.getBedrijven().size(), is(1));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void zoekOpPostcodes() {
        String postcodeTekst = "aa";
        Long relatieId = 5L;
        Long bedrijfsId = 6L;

        JsonAdres adres1 = new JsonAdres();
        adres1.setSoortEntiteit("RELATIE");
        adres1.setEntiteitId(relatieId);

        JsonAdres adres2 = new JsonAdres();
        adres2.setSoortEntiteit("BEDRIJF");
        adres2.setEntiteitId(bedrijfsId);

        List<JsonAdres> adressen = newArrayList(adres1, adres2);

        expect(adresClient.zoekOpPostcode(postcodeTekst)).andReturn(adressen);

        expect(gebruikerService.lees(relatieId)).andReturn(new Relatie());
        expect(bedrijfService.lees(bedrijfsId)).andReturn(new Bedrijf());

        replayAll();


        ZoekResultaat resultaat = zoekService.zoek(null, null, null, null, null, null, null, postcodeTekst, null, null);
        assertThat(resultaat.getBedrijven().size(), is(1));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }

    @Test
    public void zoekOpPlaats() {
        String woonplaatsTekst = "aa";
        Long relatieId = 5L;
        Long bedrijfsId = 6L;

        JsonAdres adres1 = new JsonAdres();
        adres1.setSoortEntiteit("RELATIE");
        adres1.setEntiteitId(relatieId);

        JsonAdres adres2 = new JsonAdres();
        adres2.setSoortEntiteit("BEDRIJF");
        adres2.setEntiteitId(bedrijfsId);

        List<JsonAdres> adressen = newArrayList(adres1, adres2);

        expect(adresClient.zoekOpPlaats(woonplaatsTekst)).andReturn(adressen);

        expect(gebruikerService.lees(relatieId)).andReturn(new Relatie());
        expect(bedrijfService.lees(bedrijfsId)).andReturn(new Bedrijf());

        replayAll();


        ZoekResultaat resultaat = zoekService.zoek(null, null, null, null, null, null, null, null, woonplaatsTekst, null);
        assertThat(resultaat.getBedrijven().size(), is(1));
        assertThat(resultaat.getRelaties().size(), is(1));

        verifyAll();
    }
}