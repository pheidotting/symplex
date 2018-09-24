package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanRelatieOpdracht;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.MessagingRelatieToUitgaandeOpdrachtMapper;
import org.springframework.stereotype.Service;

@Service
public class OpslaanRelatieAfhandelenService extends AfhandelenInkomendeOpdrachtService<OpslaanRelatieOpdracht, OpslaanRelatieOpdracht> {
    @Override
    protected UitgaandeOpdracht genereerUitgaandeOpdrachten(OpslaanRelatieOpdracht opdracht) {
        return new MessagingRelatieToUitgaandeOpdrachtMapper().apply(opdracht.getRelatie());


        //        UitgaandeOpdracht uitgaandeOpdrachtWachtenOp = opdracht.getRelatie().getId() != null ? null : uitgaandeOpdracht;
        //
        //        opdrachten.addAll(opdracht.getAdressen().stream().map(new MessagingAdresToUitgaandeOpdrachtMapper(uitgaandeOpdrachtWachtenOp)).collect(Collectors.toSet()));
        //        opdrachten.addAll(opdracht.getRekeningNummers().stream().map(new MessagingRekeningNummerToUitgaandeOpdrachtMapper(uitgaandeOpdrachtWachtenOp)).collect(Collectors.toSet()));
        //        opdrachten.addAll(opdracht.getTelefoonnummers().stream().map(new MessagingTelefoonnummerToUitgaandeOpdrachtMapper(uitgaandeOpdrachtWachtenOp)).collect(Collectors.toSet()));
        //
        //        return opdrachten;
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAANRELATIE;
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanRelatieOpdracht opdracht, UitgaandeOpdracht uitgaandeOpdracht) {
        return opdracht.getRelatie().getId() != null ? null : uitgaandeOpdracht;
    }

    @Override
    public void verwerkTerugkoppeling(OpslaanRelatieOpdracht response) {

    }
}
