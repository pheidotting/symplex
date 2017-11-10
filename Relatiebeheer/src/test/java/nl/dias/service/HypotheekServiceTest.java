package nl.dias.service;

import nl.dias.domein.Hypotheek;
import nl.dias.domein.HypotheekPakket;
import nl.dias.domein.Relatie;
import nl.dias.domein.SoortHypotheek;
import nl.dias.repository.HypotheekPakketRepository;
import nl.dias.repository.HypotheekRepository;
import nl.dias.web.mapper.HypotheekMapper;
import nl.lakedigital.djfc.commons.json.JsonHypotheek;
import org.easymock.*;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(EasyMockRunner.class)
public class HypotheekServiceTest extends EasyMockSupport {
    @TestSubject
    private HypotheekService service = new HypotheekService();
    @Mock
    private HypotheekRepository hypotheekRepository;
    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private HypotheekPakketRepository hypotheekPakketRepository;
    @Mock
    private HypotheekMapper hypotheekMapper;


    @After
    public void tearDown() throws Exception {
        verifyAll();
    }

    @Test
    public void testOpslaan() {
        Hypotheek hypotheek = new Hypotheek();

        hypotheekRepository.opslaan(hypotheek);
        expectLastCall();

        replayAll();

        service.opslaan(hypotheek);
    }

    @Test
    public void testOpslaanJsonHypotheekZonderGekoppeldeHypotheek() {
        JsonHypotheek jsonHypotheek = new JsonHypotheek();
        Hypotheek hypotheek = new Hypotheek();
        Capture<Hypotheek> hypotheekCapture = newCapture();
        String hypotheekVorm = "1";
        Long relatieId = 46L;

        Relatie relatie = new Relatie();
        SoortHypotheek soortHypotheek = new SoortHypotheek();

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);
        expect(hypotheekRepository.leesSoortHypotheek(Long.valueOf(hypotheekVorm))).andReturn(soortHypotheek);
        expect(hypotheekMapper.mapVanJson(isA(JsonHypotheek.class), capture(hypotheekCapture))).andReturn(hypotheek);

        Capture<HypotheekPakket> hypotheekPakketCapture = newCapture();
        hypotheekPakketRepository.opslaan(capture(hypotheekPakketCapture));
        expectLastCall().times(2);

        hypotheekRepository.opslaan(capture(hypotheekCapture));
        expectLastCall().times(2);

        replayAll();

        service.opslaan(jsonHypotheek, hypotheekVorm, relatieId, null);

        verifyAll();
    }

    @Test
    public void testOpslaanJsonHypotheekMetGekoppeldeHypotheek() {
        JsonHypotheek jsonHypotheek = new JsonHypotheek();
        Hypotheek hypotheek = new Hypotheek();
        Capture<Hypotheek> hypotheekCapture = newCapture();
        String hypotheekVorm = "1";
        Long relatieId = 46L;
        Long gekoppeldeHypotheekId = 58L;
        Hypotheek gekoppeldeHypotheek = new Hypotheek();

        Relatie relatie = new Relatie();
        SoortHypotheek soortHypotheek = new SoortHypotheek();

        expect(gebruikerService.lees(relatieId)).andReturn(relatie);
        expect(hypotheekRepository.leesSoortHypotheek(Long.valueOf(hypotheekVorm))).andReturn(soortHypotheek);
        expect(hypotheekMapper.mapVanJson(isA(JsonHypotheek.class), capture(hypotheekCapture))).andReturn(hypotheek);

        expect(hypotheekRepository.lees(gekoppeldeHypotheekId)).andReturn(gekoppeldeHypotheek);

        Capture<HypotheekPakket> hypotheekPakketCapture = newCapture();
        hypotheekPakketRepository.opslaan(capture(hypotheekPakketCapture));
        expectLastCall().times(2);

        hypotheekRepository.opslaan(capture(hypotheekCapture));
        expectLastCall().times(4);

        replayAll();

        service.opslaan(jsonHypotheek, hypotheekVorm, relatieId, gekoppeldeHypotheekId);

        verifyAll();
    }

    @Test
    public void testLeesHypotheek() {
        Long id = 46L;
        Hypotheek hypotheek = createMock(Hypotheek.class);

        expect(hypotheekRepository.lees(id)).andReturn(hypotheek);

        replayAll();

        assertEquals(hypotheek, service.leesHypotheek(id));
    }

    @Test
    public void testLeesHypotheekPakket() {
        Long id = 46L;
        HypotheekPakket hypotheekPakket = createMock(HypotheekPakket.class);

        expect(hypotheekPakketRepository.lees(id)).andReturn(hypotheekPakket);

        replayAll();

        assertEquals(hypotheekPakket, service.leesHypotheekPakket(id));
    }

    @Test
    public void testAlleSoortenHypotheekInGebruik() {
        List<SoortHypotheek> soorten = new ArrayList<>();

        expect(hypotheekRepository.alleSoortenHypotheekInGebruik()).andReturn(soorten);

        replayAll();

        assertEquals(soorten, service.alleSoortenHypotheekInGebruik());
    }

    @Test
    public void allesVanRelatie() {
        Long relatieId = 58L;

        List<Hypotheek> lijst = new ArrayList<Hypotheek>();

        Relatie relatie = createMock(Relatie.class);
        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        expect(hypotheekRepository.allesVanRelatie(relatie)).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.allesVanRelatie(relatieId));
    }

    @Test
    public void allesVanRelatieInclDePakketten() {
        Long relatieId = 58L;

        List<Hypotheek> lijst = new ArrayList<Hypotheek>();

        Relatie relatie = createMock(Relatie.class);
        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        expect(hypotheekRepository.allesVanRelatieInclDePakketten(relatie)).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.allesVanRelatieInclDePakketten(relatieId));
    }

    @Test
    public void allePakketenVanRelatie() {
        Long relatieId = 58L;

        List<HypotheekPakket> lijst = new ArrayList<HypotheekPakket>();

        Relatie relatie = createMock(Relatie.class);
        expect(gebruikerService.lees(relatieId)).andReturn(relatie);

        expect(hypotheekPakketRepository.allesVanRelatie(relatie)).andReturn(lijst);

        replayAll();

        assertEquals(lijst, service.allePakketenVanRelatie(relatieId));
    }

    @Test
    public void testLeesSoortHypotheek() {
        SoortHypotheek soortHypotheek = createMock(SoortHypotheek.class);

        expect(hypotheekRepository.leesSoortHypotheek(2L)).andReturn(soortHypotheek);

        replayAll();

        assertEquals(soortHypotheek, service.leesSoortHypotheek(2L));
    }

}
