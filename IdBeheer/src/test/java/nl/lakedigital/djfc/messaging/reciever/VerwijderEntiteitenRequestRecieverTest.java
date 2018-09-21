package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.Identificatie;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;

@RunWith(EasyMockRunner.class)
public class VerwijderEntiteitenRequestRecieverTest extends EasyMockSupport {
    @TestSubject
    private VerwijderEntiteitenRequestReciever verwijderEntiteitenRequestReciever = new VerwijderEntiteitenRequestReciever();
    @Mock
    private IdentificatieService identificatieService;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void verwerkMessage() {
        SoortEntiteit soortEntiteit = SoortEntiteit.BEDRIJF;
        Long entiteitId = 6L;
        Identificatie identificatie = new Identificatie();

        expect(identificatieService.zoek(soortEntiteit.name(), entiteitId)).andReturn(identificatie);

        identificatieService.verwijder(identificatie);
        expectLastCall();

        replayAll();

        VerwijderEntiteitenRequest verwijderEntiteitenRequest = new VerwijderEntiteitenRequest();
        verwijderEntiteitenRequest.setEntiteitId(entiteitId);
        verwijderEntiteitenRequest.setSoortEntiteit(soortEntiteit);

        verwijderEntiteitenRequestReciever.verwerkMessage(verwijderEntiteitenRequest);

        verifyAll();
    }
}