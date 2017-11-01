package nl.dias.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTest {

	@Test
	public void beginHoofdletter() {
		assertEquals("Hoi", Utils.beginHoofdletter("hoi"));
	}

}
