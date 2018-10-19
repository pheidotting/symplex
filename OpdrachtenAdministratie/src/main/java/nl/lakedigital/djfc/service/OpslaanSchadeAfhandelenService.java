package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanSchadeOpdracht;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.MessagingSchadeToUitgaandeOpdrachtMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class OpslaanSchadeAfhandelenService extends AfhandelenInkomendeOpdrachtService<OpslaanSchadeOpdracht> {
    @Override
    protected List<UitgaandeOpdracht> genereerUitgaandeOpdrachten(OpslaanSchadeOpdracht opdracht) {
        return new MessagingSchadeToUitgaandeOpdrachtMapper().map(opdracht.getSchade());
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAAN;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.SCHADE);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanSchadeOpdracht opdracht, List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        Optional<UitgaandeOpdracht> optionalUitgaandeOpdracht = opdracht.getSchade().getId() != null ? null : uitgaandeOpdrachten.stream().filter(uitgaandeOpdracht -> getSoortEntiteiten().contains(uitgaandeOpdracht.getSoortEntiteit())).findFirst();
        return optionalUitgaandeOpdracht.isPresent() ? optionalUitgaandeOpdracht.get() : null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanSchadeOpdracht message) {
        return new SoortEntiteitEnEntiteitId(SoortEntiteit.SCHADE, message.getSchade().getId());
    }

}
