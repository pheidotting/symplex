package nl.lakedigital.djfc.commons.json;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(EasyMockRunner.class)
public class ZoekVeldenTest extends EasyMockSupport {

    @Test
    public void testIsEmpty() throws Exception {
        ZoekVelden zoekVelden = new ZoekVelden();
        assertTrue(zoekVelden.isEmpty());

        zoekVelden.setPolisnummer("polis");

        assertFalse(zoekVelden.isEmpty());
    }
}