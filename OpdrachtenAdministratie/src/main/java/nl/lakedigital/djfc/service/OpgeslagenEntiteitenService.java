package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class OpgeslagenEntiteitenService extends AfhandelenInkomendeOpdrachtService<OpslaanEntiteitenRequest> {
    @Override
    protected List<UitgaandeOpdracht> genereerUitgaandeOpdrachten(OpslaanEntiteitenRequest opdracht) {
        return null;
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return null;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.ENTITEITOPGESLAGEN);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanEntiteitenRequest opdracht, List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        return null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanEntiteitenRequest message) {
        return null;
    }
}
