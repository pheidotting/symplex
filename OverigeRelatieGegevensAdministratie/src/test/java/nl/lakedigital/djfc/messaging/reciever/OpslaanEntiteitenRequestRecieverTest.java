package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.*;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.AdresService;
import nl.lakedigital.djfc.service.OpmerkingService;
import nl.lakedigital.djfc.service.RekeningNummerService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.easymock.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.easymock.EasyMock.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(EasyMockRunner.class)
public class OpslaanEntiteitenRequestRecieverTest extends EasyMockSupport {
    @TestSubject
    private OpslaanEntiteitenRequestReciever opslaanEntiteitenRequestReciever = new OpslaanEntiteitenRequestReciever();

    @Mock
    private OpmerkingService opmerkingService;
    @Mock
    private TelefoonnummerService telefoonnummerService;
    @Mock
    private RekeningNummerService rekeningNummerService;
    @Mock
    private AdresService adresService;
    @Mock
    private IdentificatieClient identificatieClient;
    @Mock(MockType.NICE)
    private MetricsService metricsService;

    @Test
    public void verwerkMessage() {
        SoortEntiteit soortEntiteit = SoortEntiteit.RELATIE;
        Long entiteitId = 5L;

        OpslaanEntiteitenRequest opslaanEntiteitenRequest = new OpslaanEntiteitenRequest();
        opslaanEntiteitenRequest.setSoortEntiteit(soortEntiteit);
        opslaanEntiteitenRequest.setEntiteitId(entiteitId);

        Opmerking opmerking = new Opmerking();
        opmerking.setIdentificatie("a");
        opmerking.setTijd("01-04-2018 23:59");
        opmerking.setSoortEntiteit(soortEntiteit);
        opmerking.setEntiteitId(entiteitId);
        opslaanEntiteitenRequest.getLijst().add(opmerking);
        Adres adres = new Adres();
        adres.setIdentificatie("b");
        adres.setSoortEntiteit(soortEntiteit);
        adres.setEntiteitId(entiteitId);
        adres.setSoortAdres("WOONADRES");
        opslaanEntiteitenRequest.getLijst().add(adres);
        Telefoonnummer telefoonnummer = new Telefoonnummer();
        telefoonnummer.setIdentificatie("c");
        telefoonnummer.setSoortEntiteit(soortEntiteit);
        telefoonnummer.setEntiteitId(entiteitId);
        telefoonnummer.setSoort("VAST");
        opslaanEntiteitenRequest.getLijst().add(telefoonnummer);
        RekeningNummer rekeningNummer = new RekeningNummer();
        rekeningNummer.setIdentificatie("d");
        rekeningNummer.setSoortEntiteit(soortEntiteit);
        rekeningNummer.setEntiteitId(entiteitId);
        opslaanEntiteitenRequest.getLijst().add(rekeningNummer);

        expect(identificatieClient.zoekIdentificatieCode("a")).andReturn(null);
        expect(identificatieClient.zoekIdentificatieCode("b")).andReturn(null);
        expect(identificatieClient.zoekIdentificatieCode("c")).andReturn(null);
        expect(identificatieClient.zoekIdentificatieCode("d")).andReturn(null);

        Capture<List> opmerkingCapture = newCapture();
        Capture<List> adresCapture = newCapture();
        Capture<List> telefoonnummerCapture = newCapture();
        Capture<List> rekeningNummerCapture = newCapture();

        expect(opmerkingService.opslaan(capture(opmerkingCapture), eq(SoortEntiteit.RELATIE), eq(entiteitId))).andReturn(newArrayList());
        expect(adresService.opslaan(capture(adresCapture), eq(SoortEntiteit.RELATIE), eq(entiteitId))).andReturn(newArrayList());
        expect(telefoonnummerService.opslaan(capture(telefoonnummerCapture), eq(SoortEntiteit.RELATIE), eq(entiteitId))).andReturn(newArrayList());
        expect(rekeningNummerService.opslaan(capture(rekeningNummerCapture), eq(SoortEntiteit.RELATIE), eq(entiteitId))).andReturn(newArrayList());

        replayAll();

        opslaanEntiteitenRequestReciever.verwerkMessage(opslaanEntiteitenRequest);

        verifyAll();

        List<Opmerking> opmerkingen = opmerkingCapture.getValue();
        List<nl.lakedigital.djfc.domain.Adres> adressen = adresCapture.getValue();
        List<Telefoonnummer> telefoonnummers = telefoonnummerCapture.getValue();
        List<RekeningNummer> rekeningNummers = rekeningNummerCapture.getValue();

        assertThat(opmerkingen.size(), is(1));
        assertThat(adressen.size(), is(1));
        assertThat(telefoonnummers.size(), is(1));
        assertThat(rekeningNummers.size(), is(1));
    }
}