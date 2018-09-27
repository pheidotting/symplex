package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class OpslaanTaakService extends AfhandelenInkomendeOpdrachtService<OpslaanTaakRequest> {
    @Override
    protected UitgaandeOpdracht genereerUitgaandeOpdrachten(OpslaanTaakRequest opdracht) {
        return null;
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return null;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.TAAK);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanTaakRequest opdracht, UitgaandeOpdracht uitgaandeOpdracht) {
        return null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanTaakRequest message) {
        return null;
    }
}
