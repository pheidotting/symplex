package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanRelatieOpdracht;
import nl.lakedigital.djfc.domain.SoortOpdracht;
import nl.lakedigital.djfc.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.messaging.mapper.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OpslaanRelatieAfhandelenService extends AfhandelenInkomendeOpdrachtService<OpslaanRelatieOpdracht> {
    @Override
    protected Set<UitgaandeOpdracht> genereerUitgaandeOpdrachten(OpslaanRelatieOpdracht opdracht) {
        Set<UitgaandeOpdracht> opdrachten = new HashSet<>();

        UitgaandeOpdracht uitgaandeOpdracht = new MessagingRelatieToUitgaandeOpdrachtMapper().apply(opdracht.getRelatie());

        opdrachten.add(uitgaandeOpdracht);

        UitgaandeOpdracht uitgaandeOpdrachtWachtenOp = opdracht.getRelatie().getId() == null ? null : uitgaandeOpdracht;
        opdrachten.addAll(opdracht.getAdressen().stream().map(new MessagingAdresToUitgaandeOpdrachtMapper(uitgaandeOpdrachtWachtenOp)).collect(Collectors.toSet()));
        opdrachten.addAll(opdracht.getRekeningNummers().stream().map(new MessagingRekeningNummerToUitgaandeOpdrachtMapper(uitgaandeOpdrachtWachtenOp)).collect(Collectors.toSet()));
        opdrachten.addAll(opdracht.getTelefoonnummers().stream().map(new MessagingTelefoonnummerToUitgaandeOpdrachtMapper(uitgaandeOpdrachtWachtenOp)).collect(Collectors.toSet()));
        opdrachten.addAll(opdracht.getOpmerkingen().stream().map(new MessagingOpmerkingToUitgaandeOpdrachtMapper(uitgaandeOpdrachtWachtenOp)).collect(Collectors.toSet()));

        return opdrachten;
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAANRELATIE;
    }
}
