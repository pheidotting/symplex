package nl.dias.service;

import com.google.common.collect.Lists;
import nl.dias.domein.Bedrijf;
import nl.dias.domein.Kantoor;
import nl.dias.domein.Relatie;
import nl.dias.domein.polis.*;
import nl.dias.domein.polis.zakelijk.AanhangerVerzekering;
import nl.dias.domein.polis.zakelijk.GeldVerzekering;
import nl.dias.domein.polis.zakelijk.MilieuSchadeVerzekering;
import nl.dias.repository.KantoorRepository;
import nl.dias.repository.PolisRepository;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


@RunWith(EasyMockRunner.class)
public class PolisServiceTest extends EasyMockSupport {
    @TestSubject
    private PolisService polisService = new PolisService();

    @Mock
    private PolisRepository polisRepository;
    @Mock
    private GebruikerService gebruikerService;
    @Mock
    private KantoorRepository kantoorRepository;
    @Mock
    private BedrijfService bedrijfService;
    @Mock
    private VerzekeringsMaatschappijService verzekeringsMaatschappijService;

    @After
    public void after() {
        verifyAll();
    }

    @Test
    public void testBeeindigen() {
        Polis polis = createMock(Polis.class);

        expect(polisRepository.lees(2L)).andReturn(polis);
        polis.setEindDatum(LocalDate.now());
        expectLastCall();

        polisRepository.opslaan(polis);
        expectLastCall();

        replayAll();

        polisService.beeindigen(2L);

        verifyAll();
    }

    @Test
    public void testOpslaanPolis() {
        final Long id = 58L;

        AutoVerzekering polis = new AutoVerzekering();

        polisRepository.opslaan(polis);
        expectLastCall().andDelegateTo(new PolisRepository() {
            @Override
            public void opslaan(Polis o) {
                o.setId(id);
            }
        });

        expect(polisRepository.lees(id)).andReturn(polis);

        replayAll();

        polisService.opslaan(polis);
    }

    @Test
    public void testZoekOpPolisNummer() {
        CamperVerzekering camperVerzekering = new CamperVerzekering();
        Kantoor kantoor = new Kantoor();

        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andReturn(camperVerzekering);

        replayAll();

        assertEquals(camperVerzekering, polisService.zoekOpPolisNummer("1234"));
    }

    @Test
    public void testZoekOpPolisNummerMetException() {
        Kantoor kantoor = new Kantoor();

        expect(kantoorRepository.lees(1L)).andReturn(kantoor);
        expect(polisRepository.zoekOpPolisNummer("1234", kantoor)).andThrow(new NoResultException());

        replayAll();

        assertNull(polisService.zoekOpPolisNummer("1234"));
    }

    @Test
    public void testVerwijder() {
        MobieleApparatuurVerzekering verzekering = new MobieleApparatuurVerzekering();
        expect(polisRepository.lees(1L)).andReturn(verzekering);

        Relatie relatie = new Relatie();
        relatie.setId(2L);
        verzekering.setRelatie(relatie.getId());

        polisRepository.verwijder(verzekering);
        expectLastCall();

        replayAll();

        polisService.verwijder(1L);
    }

    @Test
    public void testAllePolisSoorten() {
        AnnuleringsVerzekering annuleringsVerzekering = new AnnuleringsVerzekering();
        InboedelVerzekering inboedelVerzekering = new InboedelVerzekering();

        AanhangerVerzekering aanhangerVerzekering = new AanhangerVerzekering();
        GeldVerzekering geldVerzekering = new GeldVerzekering();

        List<Polis> polissen = new ArrayList<>();
        polissen.add(annuleringsVerzekering);
        polissen.add(inboedelVerzekering);
        polissen.add(aanhangerVerzekering);
        polissen.add(geldVerzekering);

        List<String> verwachtParticulier = new ArrayList<>();
        verwachtParticulier.add(annuleringsVerzekering.getSchermNaam());
        verwachtParticulier.add(inboedelVerzekering.getSchermNaam());

        List<String> verwachtZakelijk = new ArrayList<>();
        verwachtZakelijk.add(aanhangerVerzekering.getSchermNaam());
        verwachtZakelijk.add(geldVerzekering.getSchermNaam());

        ReflectionTestUtils.setField(polisService, "polissen", polissen);

        replayAll();

        assertEquals(verwachtParticulier, polisService.allePolisSoorten(SoortVerzekering.PARTICULIER));
        assertEquals(verwachtZakelijk, polisService.allePolisSoorten(SoortVerzekering.ZAKELIJK));
    }

    @Test
    public void testAllePolissenBijRelatie() {
        List<Polis> polissen = new ArrayList<>();
        Relatie relatie = new Relatie();
        relatie.setId(9L);

        expect(polisRepository.allePolissenBijRelatie(relatie.getId())).andReturn(polissen);

        replayAll();

        assertEquals(polissen, polisService.allePolissenBijRelatie(relatie.getId()));
    }

    @Test
    public void testAllePolissenBijBedrijf() {
        List<Polis> polissen = new ArrayList<>();
        Bedrijf bedrijf = new Bedrijf();

        expect(polisRepository.allePolissenBijBedrijf(bedrijf.getId())).andReturn(polissen);

        replayAll();

        assertEquals(polissen, polisService.allePolissenBijBedrijf(bedrijf.getId()));
    }

    @Test
    public void testDefinieerPolisSoort() {
        MilieuSchadeVerzekering milieuSchadeVerzekering = new MilieuSchadeVerzekering();

        ReflectionTestUtils.setField(polisService, "polissen", Lists.newArrayList(milieuSchadeVerzekering));

        replayAll();

        assertEquals(milieuSchadeVerzekering, polisService.definieerPolisSoort(milieuSchadeVerzekering.getSchermNaam()));
    }
}
