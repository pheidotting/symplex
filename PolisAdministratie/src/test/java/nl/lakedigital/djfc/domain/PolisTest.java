package nl.lakedigital.djfc.domain;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class PolisTest extends EasyMockSupport {
    private Polis polis;

    @Before
    public void init() {
        polis = new Polis() {
            @Override
            public SoortVerzekering getSoortVerzekering() {
                return null;
            }

            @Override
            public String getSchermNaam() {
                return null;
            }

            @Override
            public Polis nieuweInstantie(SoortEntiteit soortEntiteit, Long entiteitId) {
                return null;
            }
        };
    }

    @Test
    public void maakNaamAaBbCc() {
        assertThat(polis.maakNaam("AaBbCc"), is("Aa Bb Cc"));
    }

    @Test
    public void maakNaamWGAHiaatVerzekering() {
        assertThat(polis.maakNaam("WGAHiaatVerzekering"), is("WGA Hiaat Verzekering"));
    }
}