package nl.lakedigital.djfc.commons.domain;

import nl.lakedigital.djfc.domain.Adres;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdresTest {

    @Test
    public void testIsCompleet() {
        nl.lakedigital.djfc.domain.Adres adres = new Adres();

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