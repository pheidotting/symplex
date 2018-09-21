package nl.lakedigital.djfc.web.controller;

import nl.lakedigital.djfc.commons.domain.Identificatie;
import nl.lakedigital.djfc.commons.json.ZoekIdentificatieResponse;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.http.HttpServletRequest;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class IdentificatieControllerTest extends EasyMockSupport {
    @TestSubject
    private IdentificatieController identificatieController = new IdentificatieController();

    @Mock
    private IdentificatieService identificatieService;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void zoeken() {
        String identificatieCode = "identificatieCode";
        HttpServletRequest httpServletRequest = createMock(MockType.NICE, HttpServletRequest.class);

        Identificatie identificatie = new Identificatie();
        identificatie.setEntiteitId(4L);
        identificatie.setIdentificatie(identificatieCode);
        identificatie.setSoortEntiteit("soortEntiteit");

        expect(identificatieService.zoekOpIdentificatieCode(identificatieCode)).andReturn(identificatie);

        replayAll();

        ZoekIdentificatieResponse response = identificatieController.zoeken(identificatieCode, httpServletRequest);
        assertThat(response.getIdentificaties().size(), is(1));
        nl.lakedigital.djfc.commons.json.Identificatie id = response.getIdentificaties().get(0);
        assertThat(id.getEntiteitId(), is(identificatie.getEntiteitId()));
        assertThat(id.getIdentificatie(), is(identificatie.getIdentificatie()));
        assertThat(id.getSoortEntiteit(), is(identificatie.getSoortEntiteit()));

        verifyAll();
    }

    @Test
    public void zoekenMeerdere() {
        String identificatieCode = "BEDRIJF,1&zoekterm=RELATIE,2";
        HttpServletRequest httpServletRequest = createMock(MockType.NICE, HttpServletRequest.class);

        Identificatie identificatie1 = new Identificatie();
        identificatie1.setEntiteitId(4L);
        identificatie1.setIdentificatie("identificatieCode1");
        identificatie1.setSoortEntiteit("soortEntiteit");
        Identificatie identificatie2 = new Identificatie();
        identificatie2.setEntiteitId(5L);
        identificatie2.setIdentificatie("identificatieCode2");
        identificatie2.setSoortEntiteit("soortEntiteit");

        expect(identificatieService.zoek("BEDRIJF", 1L)).andReturn(identificatie1);
        expect(identificatieService.zoek("RELATIE", 2L)).andReturn(identificatie2);

        replayAll();

        ZoekIdentificatieResponse response = identificatieController.zoekenMeerdere(identificatieCode, httpServletRequest);
        assertThat(response.getIdentificaties().size(), is(2));
        nl.lakedigital.djfc.commons.json.Identificatie id1 = response.getIdentificaties().get(0);
        assertThat(id1.getEntiteitId(), is(identificatie1.getEntiteitId()));
        assertThat(id1.getIdentificatie(), is(identificatie1.getIdentificatie()));
        assertThat(id1.getSoortEntiteit(), is(identificatie1.getSoortEntiteit()));
        nl.lakedigital.djfc.commons.json.Identificatie id2 = response.getIdentificaties().get(1);
        assertThat(id2.getEntiteitId(), is(identificatie2.getEntiteitId()));
        assertThat(id2.getIdentificatie(), is(identificatie2.getIdentificatie()));
        assertThat(id2.getSoortEntiteit(), is(identificatie2.getSoortEntiteit()));

        verifyAll();
    }

    @Test
    public void zoeken1() {
        HttpServletRequest httpServletRequest = createMock(MockType.NICE, HttpServletRequest.class);

        Identificatie identificatie1 = new Identificatie();
        identificatie1.setEntiteitId(4L);
        identificatie1.setIdentificatie("identificatieCode1");
        identificatie1.setSoortEntiteit("soortEntiteit");

        expect(identificatieService.zoek("BEDRIJF", 5L)).andReturn(identificatie1);

        replayAll();

        ZoekIdentificatieResponse response = identificatieController.zoeken("BEDRIJF", 5L, httpServletRequest);
        assertThat(response.getIdentificaties().size(), is(1));
        nl.lakedigital.djfc.commons.json.Identificatie id1 = response.getIdentificaties().get(0);
        assertThat(id1.getEntiteitId(), is(identificatie1.getEntiteitId()));
        assertThat(id1.getIdentificatie(), is(identificatie1.getIdentificatie()));
        assertThat(id1.getSoortEntiteit(), is(identificatie1.getSoortEntiteit()));

        verifyAll();
    }
}