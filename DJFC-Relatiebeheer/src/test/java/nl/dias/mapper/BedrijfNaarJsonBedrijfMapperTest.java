package nl.dias.mapper;

import nl.dias.builders.BedrijfBuilder;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public class BedrijfNaarJsonBedrijfMapperTest extends EasyMockSupport {

    @TestSubject
    private BedrijfNaarJsonBedrijfMapper mapper = new BedrijfNaarJsonBedrijfMapper();


    @Test
    public void testMap() throws Exception {
        BedrijfBuilder bedrijfBuilder = new BedrijfBuilder().withId(2L).withNaam("Naam").withKvk("kvk").withCAoVerplichtingen("caoVerplichtingen").withEmail("email").withHoedanigheid("hoedanigheid").withInternetadres("internetadres").withRechtsvorm("rechtsvorm");

        Bedrijf bedrijf = bedrijfBuilder.buildBedrijf();
        JsonBedrijf jsonBedrijf = bedrijfBuilder.buildJsonBedrijf();

        assertEquals(jsonBedrijf, mapper.map(bedrijf));
    }

    @Test
    public void testIsVoorMij() throws Exception {
        assertTrue(mapper.isVoorMij(new Bedrijf()));
        assertFalse(mapper.isVoorMij(new Relatie()));
    }
}