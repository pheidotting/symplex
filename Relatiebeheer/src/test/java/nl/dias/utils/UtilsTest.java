package nl.dias.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void beginHoofdletter() {
        assertEquals("Hoi", Utils.beginHoofdletter("hoi"));
    }

}
