package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanPolisOpdracht;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.MessagingPolisToUitgaandeOpdrachtMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class OpslaanPolisAfhandelenService extends AfhandelenInkomendeOpdrachtService<OpslaanPolisOpdracht> {
    @Override
    protected List<UitgaandeOpdracht> genereerUitgaandeOpdrachten(OpslaanPolisOpdracht opdracht) {
        return new MessagingPolisToUitgaandeOpdrachtMapper().map(opdracht.getPakket());
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAAN;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.POLIS, SoortEntiteit.PAKKET);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanPolisOpdracht opdracht, List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        Optional<UitgaandeOpdracht> optionalUitgaandeOpdracht = opdracht.getPakket().getId() != null ? null : uitgaandeOpdrachten.stream().filter(uitgaandeOpdracht -> getSoortEntiteiten().contains(uitgaandeOpdracht.getSoortEntiteit())).findFirst();
        return optionalUitgaandeOpdracht.isPresent() ? optionalUitgaandeOpdracht.get() : null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanPolisOpdracht message) {
        return new SoortEntiteitEnEntiteitId(SoortEntiteit.PAKKET, message.getPakket().getId());
    }

}
