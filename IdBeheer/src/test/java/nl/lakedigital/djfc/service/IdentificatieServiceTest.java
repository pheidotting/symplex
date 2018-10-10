package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.domain.Identificatie;
import nl.lakedigital.djfc.repository.IdentificatieRepository;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

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

        Identificatie identificatie = null;

        expect(identificatieRepository.zoek(soortEntiteit, entiteitid)).andReturn(identificatie);

        Capture<Identificatie> identificatieCapture = newCapture();
        identificatieRepository.opslaan(capture(identificatieCapture));

        replayAll();

        Identificatie identificatieUit = identificatieService.zoek(soortEntiteit, entiteitid);

        verifyAll();

        assertThat(identificatieUit.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUit.getSoortEntiteit(), is(soortEntiteit));

        Identificatie identificatieUitCapture = identificatieCapture.getValue();

        assertThat(identificatieUitCapture.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUitCapture.getSoortEntiteit(), is(soortEntiteit));
    }

    @Test
    public void zoekNietGevonden() throws Exception {
        String soortEntiteit = "BEDRIJF";
        Long entiteitid = 1L;

        expect(identificatieRepository.zoek(soortEntiteit, entiteitid)).andReturn(null);

        Capture<Identificatie> identificatieCapture = newCapture();
        identificatieRepository.opslaan(capture(identificatieCapture));

        replayAll();

        Identificatie identificatieUit = identificatieService.zoek(soortEntiteit, entiteitid);

        verifyAll();

        assertThat(identificatieUit.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUit.getSoortEntiteit(), is(soortEntiteit));
        assertThat(identificatieUit.getIdentificatie(), is(nullValue()));

        Identificatie identificatieUitCapture = identificatieCapture.getValue();

        assertThat(identificatieUitCapture.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUitCapture.getSoortEntiteit(), is(soortEntiteit));
        assertThat(identificatieUitCapture.getIdentificatie(), is(nullValue()));
    }

    @Test
    public void zoekGevondenIdentificatieGevuld() throws Exception {
        String soortEntiteit = "BEDRIJF";
        Long entiteitid = 1L;

        Identificatie identificatie = new Identificatie(soortEntiteit, entiteitid);
        identificatie.setSoortEntiteit(soortEntiteit);

        expect(identificatieRepository.zoek(soortEntiteit, entiteitid)).andReturn(identificatie);

        replayAll();

        Identificatie identificatieUit = identificatieService.zoek(soortEntiteit, entiteitid);

        verifyAll();

        assertThat(identificatieUit.getEntiteitId(), is(entiteitid));
        assertThat(identificatieUit.getSoortEntiteit(), is(soortEntiteit));
        assertThat(identificatieUit.getIdentificatie(), is(identificatie.getIdentificatie()));
    }

    @Test
    public void verwijder() {
        Identificatie identificatie = new Identificatie();

        identificatieRepository.verwijder(identificatie);
        expectLastCall();

        replayAll();

        identificatieService.verwijder(identificatie);

        verifyAll();
    }

    @Test
    public void zoekOpIdentificatieCode() {
        Identificatie identificatie = new Identificatie();

        expect(identificatieRepository.zoekOpIdentificatieCode("code")).andReturn(identificatie);

        replayAll();

        assertThat(identificatieService.zoekOpIdentificatieCode("code"), is(identificatie));

        verifyAll();
    }
}