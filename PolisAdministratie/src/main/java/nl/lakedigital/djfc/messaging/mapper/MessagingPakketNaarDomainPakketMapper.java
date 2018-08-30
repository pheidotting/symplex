package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.domain.Pakket;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.reflection.ReflectionToStringBuilder;
import nl.lakedigital.djfc.service.PolisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MessagingPakketNaarDomainPakketMapper implements Function<nl.lakedigital.as.messaging.domain.Pakket, Pakket> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessagingPakketNaarDomainPakketMapper.class);

    private PolisService polisService;
    private List<nl.lakedigital.djfc.domain.Polis> polissen;
    private IdentificatieClient identificatieClient;

    public MessagingPakketNaarDomainPakketMapper(PolisService polisService, List<nl.lakedigital.djfc.domain.Polis> polissen, IdentificatieClient identificatieClient) {
        this.polisService = polisService;
        this.polissen = polissen;
        this.identificatieClient = identificatieClient;
    }

    @Override
    public Pakket apply(nl.lakedigital.as.messaging.domain.Pakket pakketIn) {
        nl.lakedigital.djfc.domain.Pakket pakket = null;

        LOGGER.debug(ReflectionToStringBuilder.toString(pakketIn));

        //        if (StringUtils.isNotEmpty(pakketIn.getIdentificatie())) {
        //            Identificatie identificatie = identificatieClient.zoekIdentificatieCode(pakketIn.getIdentificatie());
        //            LOGGER.debug("Opgehaalde Identificatie {}", ReflectionToStringBuilder.toString(identificatie));

        if (pakketIn.getId() != null) {
            LOGGER.debug("Bestaande pakket, ophalen dus..");
            pakket = polisService.lees(pakketIn.getId());
            //                pakket.setSoortEntiteit(SoortEntiteit.valueOf(pakketIn.getSoortEntiteit()));
            //                pakket.setEntiteitId(pakketIn.getEntiteitId());
        } else {
            pakket = new Pakket(SoortEntiteit.valueOf(pakketIn.getSoortEntiteit()), pakketIn.getEntiteitId());
        }
        //        }

        pakket.setPolisNummer(pakketIn.getPolisNummer());
        pakket.setMaatschappij(pakketIn.getMaatschappij());

        pakket.setPolissen(pakketIn.getPolissen().stream().map(new MessagingPolisNaarDomainPolisMapper(polisService, polissen, identificatieClient, pakket.getPolissen())).collect(Collectors.toSet()));

        return pakket;
    }
}
