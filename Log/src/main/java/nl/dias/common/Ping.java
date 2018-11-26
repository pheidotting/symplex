package nl.dias.common;

import nl.lakedigital.djfc.client.communicatie.CommunicatieClient;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.licentie.LicentieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
import nl.lakedigital.djfc.client.taak.TaakClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class Ping {
    @Inject
    private PolisClient polisClient;
    @Inject
    private AdresClient adresClient;
    @Inject
    private LicentieClient licentieClient;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private CommunicatieClient communicatieClient;
    @Inject
    private TaakClient taakClient;

    @Scheduled(fixedDelay = 900000)
    public void ping() {
        polisClient.ping();
        adresClient.ping();
        licentieClient.ping();
        identificatieClient.ping();
        communicatieClient.ping();
        taakClient.ping();
    }
}
