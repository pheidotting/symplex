package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanPolisOpdracht;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.MessagingPolisToUitgaandeOpdrachtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class OpslaanPolisAfhandelenService extends AfhandelenInkomendeOpdrachtService<OpslaanPolisOpdracht> {
    private final static Logger LOGGER = LoggerFactory.getLogger(OpslaanPolisAfhandelenService.class);

    @Override
    protected UitgaandeOpdracht genereerUitgaandeOpdrachten(OpslaanPolisOpdracht opdracht) {

        return new MessagingPolisToUitgaandeOpdrachtMapper().apply(opdracht.getPakket());
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAANPOLIS;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.POLIS, SoortEntiteit.PAKKET);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanPolisOpdracht opdracht, UitgaandeOpdracht uitgaandeOpdracht) {
        return opdracht.getPakket().getId() != null ? null : uitgaandeOpdracht;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanPolisOpdracht message) {
        return new SoortEntiteitEnEntiteitId(SoortEntiteit.PAKKET, message.getPakket().getId());
    }

}
