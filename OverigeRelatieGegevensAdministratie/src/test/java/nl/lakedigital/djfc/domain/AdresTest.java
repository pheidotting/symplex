package nl.lakedigital.djfc.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdresTest {

    @Test
    public void testIsCompleet() {
        Adres adres = new Adres();

        assertFalse(adres.isCompleet());

        adres.setStraat("straatnaam");
        assertFalse(adres.isCompleet());

        adres.setPlaats("plaatsnaam");
        assertFalse(adres.isCompleet());

        adres.setHuisnummer(2L);
        assertFalse(adres.isCompleet());

        adres.setPostcode("1234AA");
        assertTrue(adres.isCompleet());
    }
}