package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.OpslaanRelatieOpdracht;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.MessagingRelatieToUitgaandeOpdrachtMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class OpslaanRelatieAfhandelenService extends AfhandelenInkomendeOpdrachtService<OpslaanRelatieOpdracht> {
    @Override
    protected List<UitgaandeOpdracht> genereerUitgaandeOpdrachten(OpslaanRelatieOpdracht opdracht) {
        return newArrayList(new MessagingRelatieToUitgaandeOpdrachtMapper().apply(opdracht.getRelatie()));
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.OPSLAAN;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.RELATIE);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanRelatieOpdracht opdracht, List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        Optional<UitgaandeOpdracht> optionalUitgaandeOpdracht = opdracht.getRelatie().getId() != null ? null : uitgaandeOpdrachten.stream().filter(uitgaandeOpdracht -> uitgaandeOpdracht.getSoortEntiteit() == SoortEntiteit.RELATIE).findFirst();
        return optionalUitgaandeOpdracht.isPresent() ? optionalUitgaandeOpdracht.get() : null;
    }


    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanRelatieOpdracht message) {
        return null;
    }
}
