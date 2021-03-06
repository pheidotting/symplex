package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.domain.Taak;
import nl.lakedigital.djfc.commons.domain.TaakStatus;
import nl.lakedigital.djfc.commons.domain.WijzigingTaak;
import nl.lakedigital.djfc.repository.TaakRepository;
import nl.lakedigital.djfc.repository.WijzigingTaakRepository;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class TaakServiceTest extends EasyMockSupport {
    @TestSubject
    private TaakService taakService = new TaakService();

    @Mock
    private TaakRepository taakRepository;
    @Mock
    private WijzigingTaakRepository wijzigingTaakRepository;

    @Test
    public void wijzig() {
        Long taakId = 9L;
        Taak taak = new Taak();
        taak.setId(taakId);
        TaakStatus taakStatus = TaakStatus.AFGEROND;
        Long toegewezenAan = 5L;

        taakRepository.opslaan(taak);
        expectLastCall();

        Capture<WijzigingTaak> wijzigingTaakCapture = newCapture();

        wijzigingTaakRepository.opslaan(capture(wijzigingTaakCapture));
        expectLastCall();

        replayAll();

        taakService.wijzig(taak, taakStatus, toegewezenAan);

        verifyAll();

        WijzigingTaak wijzigingTaak = wijzigingTaakCapture.getValue();
        assertThat(wijzigingTaak.getTaak(), is(taakId));
        assertThat(wijzigingTaak.getTaakStatus(), is(taakStatus));
        assertThat(wijzigingTaak.getToegewezenAan(), is(toegewezenAan));
    }

    @Test
    public void testLees() {
        Long taakId = 4L;

        Taak taak = new Taak();
        WijzigingTaak wijzigingTaak = new WijzigingTaak();

        expect(taakRepository.lees(taakId)).andReturn(taak);
        expect(wijzigingTaakRepository.allesBijTaak(taak)).andReturn(newArrayList(wijzigingTaak));

        replayAll();

        Taak taakOpgehaald = taakService.lees(taakId);

        verifyAll();

        assertThat(taakOpgehaald.getWijzigingTaak().size(), is(1));
    }
}