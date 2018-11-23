package nl.dias.common;

import nl.lakedigital.djfc.client.communicatie.CommunicatieClient;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.client.licentie.LicentieClient;
import nl.lakedigital.djfc.client.oga.AdresClient;
import nl.lakedigital.djfc.client.polisadministratie.PolisClient;
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

    @Scheduled(fixedDelay = 900000)
    public void ping() {
        System.out.println("sadfoajweiooejiwfoijweoif");
        System.out.println(polisClient.ping());
        System.out.println(adresClient.ping());
        System.out.println(licentieClient.ping());
        System.out.println(identificatieClient.ping());
        System.out.println(communicatieClient.ping());
    }
}
