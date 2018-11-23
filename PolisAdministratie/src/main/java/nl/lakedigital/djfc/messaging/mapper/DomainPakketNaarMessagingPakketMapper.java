package nl.lakedigital.djfc.messaging.mapper;

import nl.lakedigital.djfc.commons.domain.Pakket;

import java.util.function.Function;
import java.util.stream.Collectors;

//import org.joda.time.format.DateTimeFormatter;

public class DomainPakketNaarMessagingPakketMapper implements Function<nl.lakedigital.djfc.domain.Pakket, Pakket> {
    @Override
    public Pakket apply(nl.lakedigital.djfc.domain.Pakket pakketIn) {
        Pakket pakket = new Pakket();

        pakket.setId(pakketIn.getId());
        pakket.setPolisNummer(pakketIn.getPolisNummer());
        pakket.setPolissen(pakketIn.getPolissen().stream().map(new DomainPolisNaarMessagingPolisMapper()).collect(Collectors.toList()));

        return pakket;
    }
}
