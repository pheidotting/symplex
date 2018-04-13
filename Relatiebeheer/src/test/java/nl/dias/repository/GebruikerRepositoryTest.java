package nl.dias.repository;

import inloggen.SessieHolder;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
import nl.lakedigital.loginsystem.exception.NietGevondenException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-unittest.xml")
public class GebruikerRepositoryTest {
    @Inject
    private GebruikerRepository gebruikerRepository;
    @Inject
    private KantoorRepository kantoorRepository;

    @Before
    public void init() {
        SessieHolder.get().setIngelogdeGebruiker(4L);
        SessieHolder.get().setTrackAndTraceId("tEnTId");
    }

    @Test
    public void zoek() throws UnsupportedEncodingException, NoSuchAlgorithmException, PostcodeNietGoedException, TelefoonnummerNietGoedException, IbanNietGoedException, BsnNietGoedException {
        Kantoor kantoor = new Kantoor();
        kantoor.setNaam("naam");

        kantoorRepository.opslaan(kantoor);

        Relatie relatie = new Relatie();
        relatie.setEmailadres("mail");
        relatie.setIdentificatie("id");
        relatie.setHashWachtwoord("ww");
        kantoor.getRelaties().add(relatie);
        relatie.setKantoor(kantoor);

        gebruikerRepository.opslaan(relatie);

        try {
            assertEquals(relatie, gebruikerRepository.zoek("mail"));
        } catch (NietGevondenException e) {
            fail("Zou gevonden moeten worden");
        }

        try {
            assertEquals(relatie, gebruikerRepository.zoek("id"));
            fail("Zou niet gevonden mogen worden");
        } catch (NietGevondenException e) {
        }

        gebruikerRepository.verwijder(relatie);
        kantoorRepository.verwijder(kantoor);
    }

    @Test
    public void testAlleRelaties() throws PostcodeNietGoedException, TelefoonnummerNietGoedException, IbanNietGoedException, BsnNietGoedException {
        Kantoor kantoor1 = new Kantoor();
        kantoor1.setNaam("kantoor1");
        Kantoor kantoor2 = new Kantoor();
        kantoor2.setNaam("kantoor2");

        kantoorRepository.opslaan(kantoor1);
        kantoorRepository.opslaan(kantoor2);

        Relatie relatie1 = new Relatie();
        Relatie relatie2 = new Relatie();
        Relatie relatie3 = new Relatie();

        kantoor1.getRelaties().add(relatie1);
        kantoor1.getRelaties().add(relatie2);
        kantoor2.getRelaties().add(relatie3);
        relatie1.setKantoor(kantoor1);
        relatie2.setKantoor(kantoor1);
        relatie3.setKantoor(kantoor2);

        gebruikerRepository.opslaan(relatie1);
        gebruikerRepository.opslaan(relatie2);
        gebruikerRepository.opslaan(relatie3);

        assertEquals(2, gebruikerRepository.alleRelaties(kantoor1).size());
        assertEquals(1, gebruikerRepository.alleRelaties(kantoor2).size());
        assertEquals(3, gebruikerRepository.alleRelaties().size());

        gebruikerRepository.verwijder(relatie1);
        gebruikerRepository.verwijder(relatie2);
        gebruikerRepository.verwijder(relatie3);
        kantoorRepository.verwijder(kantoor1);
        kantoorRepository.verwijder(kantoor2);
    }

    @Test
    public void testZoekOpNaam() throws PostcodeNietGoedException, TelefoonnummerNietGoedException, IbanNietGoedException, BsnNietGoedException {
        Kantoor kantoor = new Kantoor();
        kantoor.setNaam("naam");

        kantoorRepository.opslaan(kantoor);

        Relatie relatie1 = new Relatie();
        relatie1.setVoornaam("ab");
        relatie1.setAchternaam("bc");
        Relatie relatie2 = new Relatie();
        relatie2.setVoornaam("cd");
        relatie2.setAchternaam("de");
        Relatie relatie3 = new Relatie();
        relatie3.setVoornaam("ef");
        relatie3.setAchternaam("fg");

        relatie1.setKantoor(kantoor);
        relatie2.setKantoor(kantoor);
        relatie3.setKantoor(kantoor);

        gebruikerRepository.opslaan(relatie1);
        gebruikerRepository.opslaan(relatie2);
        gebruikerRepository.opslaan(relatie3);

        assertEquals(1, gebruikerRepository.zoekOpNaam("a").size());
        assertEquals("ab", gebruikerRepository.zoekOpNaam("a").get(0).getVoornaam());

        assertEquals(1, gebruikerRepository.zoekOpNaam("b").size());
        assertEquals("ab", gebruikerRepository.zoekOpNaam("b").get(0).getVoornaam());

        assertEquals(2, gebruikerRepository.zoekOpNaam("c").size());
        assertEquals("ab", gebruikerRepository.zoekOpNaam("c").get(0).getVoornaam());
        assertEquals("cd", gebruikerRepository.zoekOpNaam("c").get(1).getVoornaam());

        assertEquals(1, gebruikerRepository.zoekOpNaam("d").size());
        assertEquals("cd", gebruikerRepository.zoekOpNaam("d").get(0).getVoornaam());

        assertEquals(2, gebruikerRepository.zoekOpNaam("e").size());
        assertEquals("cd", gebruikerRepository.zoekOpNaam("e").get(0).getVoornaam());
        assertEquals("ef", gebruikerRepository.zoekOpNaam("e").get(1).getVoornaam());

        gebruikerRepository.verwijder(relatie1);
        gebruikerRepository.verwijder(relatie2);
        gebruikerRepository.verwijder(relatie3);
        kantoorRepository.verwijder(kantoor);

    }
}
