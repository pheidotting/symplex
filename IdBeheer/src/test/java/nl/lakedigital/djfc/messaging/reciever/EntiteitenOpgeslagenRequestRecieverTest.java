package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.commons.domain.Identificatie;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class EntiteitenOpgeslagenRequestRecieverTest extends EasyMockSupport {
    @TestSubject
    private EntiteitenOpgeslagenRequestReciever entiteitenOpgeslagenRequestReciever = new EntiteitenOpgeslagenRequestReciever();
    @Mock
    private IdentificatieService identificatieService;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void verwerkMessage() {

        Capture<Identificatie> identificatieCapture1 = newCapture();
        Capture<Identificatie> identificatieCapture2 = newCapture();

        identificatieService.opslaan(capture(identificatieCapture1));
        expectLastCall();
        identificatieService.opslaan(capture(identificatieCapture2));
        expectLastCall();

        replayAll();

        EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest = new EntiteitenOpgeslagenRequest();

        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId1 = new SoortEntiteitEnEntiteitId();
        soortEntiteitEnEntiteitId1.setSoortEntiteit(SoortEntiteit.BEDRIJF);
        soortEntiteitEnEntiteitId1.setEntiteitId(1L);
        entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().add(soortEntiteitEnEntiteitId1);

        SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId2 = new SoortEntiteitEnEntiteitId();
        soortEntiteitEnEntiteitId2.setSoortEntiteit(SoortEntiteit.HYPOTHEEK);
        soortEntiteitEnEntiteitId2.setEntiteitId(3L);
        entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().add(soortEntiteitEnEntiteitId2);

        entiteitenOpgeslagenRequestReciever.verwerkMessage(entiteitenOpgeslagenRequest);

        verifyAll();

        Identificatie identificatie1 = identificatieCapture1.getValue();
        assertThat(identificatie1.getEntiteitId(), is(1L));
        assertThat(identificatie1.getSoortEntiteit(), is("BEDRIJF"));

        Identificatie identificatie2 = identificatieCapture2.getValue();
        assertThat(identificatie2.getEntiteitId(), is(3L));
        assertThat(identificatie2.getSoortEntiteit(), is("HYPOTHEEK"));
    }
}