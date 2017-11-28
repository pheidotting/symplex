package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.domain.Adres;
import nl.lakedigital.as.messaging.domain.Opmerking;
import nl.lakedigital.as.messaging.domain.Telefoonnummer;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.messaging.mappers.MessagingAdresNaarDomainAdresMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingOpmerkingNaarDomainOpmerkingMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingTelefoonnummerNaarDomainTelefoonnummerMapper;
import nl.lakedigital.djfc.service.AdresService;
import nl.lakedigital.djfc.service.OpmerkingService;
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
    private AdresService adresService;
    @Inject
    private IdentificatieClient identificatieClient;

    public OpslaanEntiteitenRequestReciever() {
        super(OpslaanEntiteitenRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
        opmerkingService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Opmerking)//
                .map(new MessagingOpmerkingNaarDomainOpmerkingMapper(identificatieClient, opmerkingService))//
                .collect(Collectors.toList()));

        adresService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Adres)//
                .map(new MessagingAdresNaarDomainAdresMapper(identificatieClient, adresService))//
                .collect(Collectors.toList()));

        telefoonnummerService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Telefoonnummer)//
                .map(new MessagingTelefoonnummerNaarDomainTelefoonnummerMapper(identificatieClient, telefoonnummerService))//
                .collect(Collectors.toList()));
    }
}
