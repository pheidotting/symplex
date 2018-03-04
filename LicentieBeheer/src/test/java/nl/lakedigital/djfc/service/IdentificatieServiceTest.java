package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Licentie;
import nl.lakedigital.djfc.repository.IdentificatieRepository;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class IdentificatieServiceTest extends EasyMockSupport {
    @TestSubject
    private IdentificatieService identificatieService = new IdentificatieService();

    @Mock
    private IdentificatieRepository identificatieRepository;

    @Test
    public void zoekGevondenMaarLegeIdentificatie() throws Exception {
        String soortEntiteit = "BEDRIJF";
        Long entiteitid = 1L;

        Licentie identificatie = new Licentie(soortEntiteit, entiteitid);

        expect(identificatieRepository.zoek(soortEntiteit, entiteitid)).andReturn(identificatie);

        Capture<Licentie> identificatieCapture = newCapture();
        identificatieRepository.opslaan(capture(identificatieCapture));

        replayAll();

        Licentie identificatieUit = identificatieService.zoek(soortEntiteit, entiteitid);

        verifyAll();

        assertThat(identificatieUit.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUit.getSoortEntiteit(), is(soortEntiteit));

        Licentie identificatieUitCapture = identificatieCapture.getValue();

        assertThat(identificatieUitCapture.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUitCapture.getSoortEntiteit(), is(soortEntiteit));
    }

    @Test
    public void zoekNietGevonden() throws Exception {
        String soortEntiteit = "BEDRIJF";
        Long entiteitid = 1L;

        expect(identificatieRepository.zoek(soortEntiteit, entiteitid)).andReturn(null);

        Capture<Licentie> identificatieCapture = newCapture();
        identificatieRepository.opslaan(capture(identificatieCapture));

        replayAll();

        Licentie identificatieUit = identificatieService.zoek(soortEntiteit, entiteitid);

        verifyAll();

        assertThat(identificatieUit.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUit.getSoortEntiteit(), is(soortEntiteit));
        assertThat(identificatieUit.getIdentificatie(), is(nullValue()));

        Licentie identificatieUitCapture = identificatieCapture.getValue();

        assertThat(identificatieUitCapture.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUitCapture.getSoortEntiteit(), is(soortEntiteit));
        assertThat(identificatieUitCapture.getIdentificatie(), is(nullValue()));
    }

    @Test
    public void zoekGevondenIdentificatieGevuld() throws Exception {
        String soortEntiteit = "BEDRIJF";
        Long entiteitid = 1L;

        Licentie identificatie = new Licentie(soortEntiteit, entiteitid);
        identificatie.setSoortEntiteit(UUID.randomUUID().toString());

        expect(identificatieRepository.zoek(soortEntiteit, entiteitid)).andReturn(identificatie);

        Capture<Licentie> identificatieCapture = newCapture();
        identificatieRepository.opslaan(capture(identificatieCapture));

        replayAll();

        Licentie identificatieUit = identificatieService.zoek(soortEntiteit, entiteitid);

        verifyAll();

        assertThat(identificatieUit.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUit.getSoortEntiteit(), is(soortEntiteit));
        assertThat(identificatieUit.getIdentificatie(), is(identificatie.getIdentificatie()));

        Licentie identificatieUitCapture = identificatieCapture.getValue();

        assertThat(identificatieUitCapture.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUitCapture.getSoortEntiteit(), is(soortEntiteit));
        assertThat(identificatieUitCapture.getIdentificatie(), is(identificatie.getIdentificatie()));
    }

}