package nl.dias.service;

import nl.dias.builders.BelastingzakenBuilder;
import nl.dias.domein.Belastingzaken;
import nl.dias.repository.BelastingzakenRepository;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        //    Belastingzaken overig=new BelastingzakenBuilder().metRelatie(id).metJaar(2017).metSoort(Belastingzaken.SoortBelastingzaak.OVERIG).build();

        expect(belastingzakenRepository.alles(SoortEntiteit.RELATIE, id)).andReturn(newArrayList(ib));

        Capture<Belastingzaken> belastingzakenCapture = newCapture();
        belastingzakenRepository.opslaan(capture(belastingzakenCapture));
        expectLastCall();

        replayAll();

        assertThat(belastingzakenService.alles(SoortEntiteit.RELATIE, id).size(), is(2));
        Belastingzaken belastingzaken = belastingzakenCapture.getValue();
        assertThat(belastingzaken.getSoort(), is(Belastingzaken.SoortBelastingzaak.OVERIG));

        verifyAll();
    }
}