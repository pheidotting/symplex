package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static nl.lakedigital.djfc.commons.domain.SoortEntiteit.ENTITEITVERWIJDERD;

@Service
public class VerwijderEntiteitenService extends AfhandelenInkomendeOpdrachtService<VerwijderEntiteitenRequest> {
    @Override
    protected List<UitgaandeOpdracht> genereerUitgaandeOpdrachten(VerwijderEntiteitenRequest opdracht) {
        return null;
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.VERWIJDEREN;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(ENTITEITVERWIJDERD);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(VerwijderEntiteitenRequest opdracht, List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        return null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(VerwijderEntiteitenRequest message) {
        return null;
    }
}
