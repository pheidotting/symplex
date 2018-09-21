package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.domain.Adres;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.Opmerking;
import nl.lakedigital.djfc.commons.domain.RekeningNummer;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.Telefoonnummer;
import nl.lakedigital.djfc.messaging.mappers.MessagingAdresNaarDomainAdresMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingOpmerkingNaarDomainOpmerkingMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingRekeningNummerNaarDomainRekeningNummerMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingTelefoonnummerNaarDomainTelefoonnummerMapper;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.AdresService;
import nl.lakedigital.djfc.service.OpmerkingService;
import nl.lakedigital.djfc.service.RekeningNummerService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.stream.Collectors;

public class OpslaanEntiteitenRequestReciever extends AbstractReciever<OpslaanEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanEntiteitenRequestReciever.class);

    @Inject
    private OpmerkingService opmerkingService;
    @Inject
    private TelefoonnummerService telefoonnummerService;
    @Inject
    private RekeningNummerService rekeningNummerService;
    @Inject
    private AdresService adresService;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;

    public OpslaanEntiteitenRequestReciever() {
        super(OpslaanEntiteitenRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", OpslaanEntiteitenRequestReciever.class);

        opmerkingService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Opmerking)//
                .map(new MessagingOpmerkingNaarDomainOpmerkingMapper(identificatieClient, opmerkingService, opslaanEntiteitenRequest.getIngelogdeGebruiker()))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId());

        adresService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Adres)//
                .map(new MessagingAdresNaarDomainAdresMapper(identificatieClient, adresService))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId());

        telefoonnummerService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Telefoonnummer)//
                .map(new MessagingTelefoonnummerNaarDomainTelefoonnummerMapper(identificatieClient, telefoonnummerService))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId());

        rekeningNummerService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof RekeningNummer)//
                .map(new MessagingRekeningNummerNaarDomainRekeningNummerMapper(identificatieClient, rekeningNummerService))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId());

        metricsService.stop(timer);
    }
}
