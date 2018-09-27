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
public class OpslaanEntiteitenService extends AfhandelenInkomendeOpdrachtService<OpslaanEntiteitenRequest> {
    @Override
    protected UitgaandeOpdracht genereerUitgaandeOpdrachten(OpslaanEntiteitenRequest opdracht) {
        return null;
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return null;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.ADRES, SoortEntiteit.OPMERKING, SoortEntiteit.REKENINGNUMMER, SoortEntiteit.TELEFOONNUMMER);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(OpslaanEntiteitenRequest opdracht, UitgaandeOpdracht uitgaandeOpdracht) {
        return null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(OpslaanEntiteitenRequest message) {
        return null;
    }
}
