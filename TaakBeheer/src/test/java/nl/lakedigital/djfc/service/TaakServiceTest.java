package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Taak;
import nl.lakedigital.djfc.domain.TaakStatus;
import nl.lakedigital.djfc.domain.WijzigingTaak;
import nl.lakedigital.djfc.repository.TaakRepository;
import nl.lakedigital.djfc.repository.WijzigingTaakRepository;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

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
}