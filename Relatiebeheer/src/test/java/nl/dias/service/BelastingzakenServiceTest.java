package nl.dias.service;

import nl.dias.builders.BelastingzakenBuilder;
import nl.dias.domein.Belastingzaken;
import nl.dias.repository.BelastingzakenRepository;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.easymock.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@Ignore
@RunWith(EasyMockRunner.class)
public class BelastingzakenServiceTest extends EasyMockSupport {
    @TestSubject
    private BelastingzakenService belastingzakenService = new BelastingzakenService();

    @Mock
    private BelastingzakenRepository belastingzakenRepository;

    @Test
    public void testNietAlleAanwezigRelatie() {
        Long id = 46L;
        Belastingzaken ib = new BelastingzakenBuilder().metRelatie(id).metJaar(2017).metSoort(Belastingzaken.SoortBelastingzaak.IB).build();

        expect(belastingzakenRepository.alles(SoortEntiteit.RELATIE, id)).andReturn(newArrayList(ib)).times(3);

        Capture<Belastingzaken> belastingzakenCapture = newCapture();
        belastingzakenRepository.opslaan(capture(belastingzakenCapture));
        expectLastCall().times(2);

        replayAll();

        assertThat(belastingzakenService.alles(SoortEntiteit.RELATIE, id).size(), is(2));
        Belastingzaken belastingzaken = belastingzakenCapture.getValue();
        assertThat(belastingzaken.getSoort(), is(Belastingzaken.SoortBelastingzaak.OVERIG));

        verifyAll();
    }

    @Test
    public void testNietAlleAanwezigBedrijf() {
        Long id = 46L;
        Belastingzaken ib = new BelastingzakenBuilder().metBedrijf(id).metJaar(2017).metSoort(Belastingzaken.SoortBelastingzaak.IB).build();

        expect(belastingzakenRepository.alles(SoortEntiteit.BEDRIJF, id)).andReturn(newArrayList(ib)).times(3);

        Capture<Belastingzaken> belastingzakenCapture1 = newCapture();
        belastingzakenRepository.opslaan(capture(belastingzakenCapture1));
        expectLastCall().times(2);

        Capture<Belastingzaken> belastingzakenCapture2 = newCapture();
        belastingzakenRepository.opslaan(capture(belastingzakenCapture2));
        expectLastCall().times(2);

        Capture<Belastingzaken> belastingzakenCapture3 = newCapture();
        belastingzakenRepository.opslaan(capture(belastingzakenCapture3));
        expectLastCall().times(2);

        Capture<Belastingzaken> belastingzakenCapture4 = newCapture();
        belastingzakenRepository.opslaan(capture(belastingzakenCapture4));
        expectLastCall().times(2);

        Capture<Belastingzaken> belastingzakenCapture5 = newCapture();
        belastingzakenRepository.opslaan(capture(belastingzakenCapture5));
        expectLastCall().times(2);

        replayAll();

        assertThat(belastingzakenService.alles(SoortEntiteit.BEDRIJF, id).size(), is(Belastingzaken.SoortBelastingzaak.values().length));
        Belastingzaken belastingzaken1 = belastingzakenCapture1.getValue();
        assertThat(belastingzaken1.getSoort(), is(Belastingzaken.SoortBelastingzaak.CONTRACTEN));
        Belastingzaken belastingzaken2 = belastingzakenCapture2.getValue();
        assertThat(belastingzaken2.getSoort(), is(Belastingzaken.SoortBelastingzaak.JAARREKENING));
        Belastingzaken belastingzaken3 = belastingzakenCapture3.getValue();
        assertThat(belastingzaken3.getSoort(), is(Belastingzaken.SoortBelastingzaak.BTW));
        Belastingzaken belastingzaken4 = belastingzakenCapture4.getValue();
        assertThat(belastingzaken4.getSoort(), is(Belastingzaken.SoortBelastingzaak.LOONBELASTING));
        Belastingzaken belastingzaken5 = belastingzakenCapture5.getValue();
        assertThat(belastingzaken5.getSoort(), is(Belastingzaken.SoortBelastingzaak.OVERIG));

        verifyAll();
    }
}