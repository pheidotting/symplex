package nl.dias.service;

import nl.dias.domein.Relatie;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.client.polisadministratie.SchadeClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.commons.json.JsonAdres;
import nl.lakedigital.djfc.commons.json.JsonPolis;
import nl.lakedigital.djfc.commons.json.JsonSchade;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static org.easymock.EasyMock.expect;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class RelatieServiceTest extends EasyMockSupport {
    @TestSubject
    private RelatieService relatieService = new RelatieService();

    @Mock
    private IdentificatieClient identificatieClient;
    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private PolisClient polisClient;
    @Mock
    private AdresClient adresClient;
    @Mock
    private SchadeClient schadeClient;

    @Test
    public void testZoekRelatieObvRelatieIdentificatie() throws Exception {
        String code = UUID.randomUUID().toString();
        Long relatieId = 4L;
        Relatie relatie = new Relatie();

        Identificatie identificatie = new Identificatie();
        identificatie.setEntiteitId(relatieId);
        identificatie.setSoortEntiteit("RELATIE");

        expect(identificatieClient.zoekIdentificatieCode(code)).andReturn(identificatie);

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        replayAll();

        assertThat(relatieService.zoekRelatie(code), is(relatie));

        verifyAll();
    }

    @Test
    public void testZoekRelatieObvPolisIdentificatie() throws Exception {
        String code = UUID.randomUUID().toString();
        Long polisId = 5L;
        Long relatieId = 4L;
        Relatie relatie = new Relatie();
        JsonPolis polis = new JsonPolis();
        polis.setEntiteitId(relatieId);

        Identificatie identificatie = new Identificatie();
        identificatie.setEntiteitId(polisId);
        identificatie.setSoortEntiteit("POLIS");

        expect(identificatieClient.zoekIdentificatieCode(code)).andReturn(identificatie);

        expect(polisClient.lees(String.valueOf(polisId))).andReturn(polis);

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        replayAll();

        assertThat(relatieService.zoekRelatie(code), is(relatie));

        verifyAll();
    }

    @Test
    public void testZoekRelatieObvAdresIdentificatie() throws Exception {
        String code = UUID.randomUUID().toString();
        Long adresId = 5L;
        Long relatieId = 4L;
        Relatie relatie = new Relatie();
        JsonAdres adres = new JsonAdres();
        adres.setEntiteitId(relatieId);

        Identificatie identificatie = new Identificatie();
        identificatie.setEntiteitId(adresId);
        identificatie.setSoortEntiteit("ADRES");

        expect(identificatieClient.zoekIdentificatieCode(code)).andReturn(identificatie);

        expect(adresClient.lees(adresId)).andReturn(adres);

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        replayAll();

        assertThat(relatieService.zoekRelatie(code), is(relatie));

        verifyAll();
    }

    @Test
    public void testZoekRelatieObvSchadeIdentificatie() throws Exception {
        String code = UUID.randomUUID().toString();
        Long schadeId = 6L;
        Long polisId = 5L;
        Long relatieId = 4L;
        Relatie relatie = new Relatie();
        JsonPolis polis = new JsonPolis();
        polis.setEntiteitId(relatieId);
        JsonSchade schade = new JsonSchade();
        schade.setPolis(String.valueOf(polisId));
        schade.setId(schadeId);

        Identificatie identificatie = new Identificatie();
        identificatie.setEntiteitId(schadeId);
        identificatie.setSoortEntiteit("SCHADE");

        expect(identificatieClient.zoekIdentificatieCode(code)).andReturn(identificatie);

        expect(schadeClient.lees(String.valueOf(schadeId))).andReturn(schade);
        expect(polisClient.lees(String.valueOf(polisId))).andReturn(polis);

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        replayAll();

        assertThat(relatieService.zoekRelatie(code), is(relatie));

        verifyAll();
    }
}