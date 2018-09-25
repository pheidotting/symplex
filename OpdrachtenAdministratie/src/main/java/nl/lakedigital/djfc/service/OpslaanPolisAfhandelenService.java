package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanPolisOpdracht;
import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.MessagingPolisToUitgaandeOpdrachtMapper;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpslaanPolisAfhandelenService extends AfhandelenInkomendeOpdrachtService<OpslaanPolisOpdracht, PolisOpslaanResponse> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanPolisAfhandelenService.class);

    @Override
    protected UitgaandeOpdracht genereerUitgaandeOpdrachten(OpslaanPolisOpdracht opdracht) {

        return new MessagingPolisToUitgaandeOpdrachtMapper().apply(opdracht.getPakket());
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAANRELATIE;
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanPolisOpdracht opdracht, UitgaandeOpdracht uitgaandeOpdracht) {
        return opdracht.getPakket().getId() != null ? null : uitgaandeOpdracht;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanPolisOpdracht message) {
        return new SoortEntiteitEnEntiteitId(SoortEntiteit.PAKKET, message.getPakket().getId());
    }

    @Override
    public void verwerkTerugkoppeling(PolisOpslaanResponse response) {
        UitgaandeOpdracht uitgaandeOpdracht = uitgaandeOpdrachtRepository.zoekObvSoortEntiteitEnTrackAndTrackeId(response.getTrackAndTraceId(), SoortEntiteit.POLIS);

        uitgaandeOpdracht.setTijdstipAfgerond(LocalDateTime.now());
        uitgaandeOpdrachtRepository.opslaan(uitgaandeOpdracht);

        LOGGER.debug("Opzoeken openstaande opdrachten op {} en {}", response.getTrackAndTraceId(), uitgaandeOpdracht.getId());

        List<UitgaandeOpdracht> uitgaandeOpdrachten = uitgaandeOpdrachtRepository.teVersturenUitgaandeOpdrachten(response.getTrackAndTraceId(), uitgaandeOpdracht);
        uitgaandeOpdrachten.stream().forEach(uo -> uo.setBericht(uo.getBericht().replace("<entiteitId>0</entiteitId>", "<entiteitId>" + response.getId() + "</entiteitId>")));

        LOGGER.debug("Gevonden : {}", uitgaandeOpdrachten);

        verstuurUitgaandeOpdrachtenService.verstuurUitgaandeOpdrachten(uitgaandeOpdrachten);
    }
}
