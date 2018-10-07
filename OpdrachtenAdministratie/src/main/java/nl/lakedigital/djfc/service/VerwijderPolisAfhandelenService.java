package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.VerwijderPolisOpdracht;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.PolisVerwijderRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class VerwijderPolisAfhandelenService extends AfhandelenInkomendeOpdrachtService<VerwijderPolisOpdracht> {
    private final static Logger LOGGER = LoggerFactory.getLogger(VerwijderPolisAfhandelenService.class);

    @Override
    protected List<UitgaandeOpdracht> genereerUitgaandeOpdrachten(VerwijderPolisOpdracht opdracht) {
        return new PolisVerwijderRequestMapper().map(opdracht.getId());
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.VERWIJDEREN;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.POLIS, SoortEntiteit.PAKKET);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(VerwijderPolisOpdracht opdracht, List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        return null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(VerwijderPolisOpdracht message) {
        return new SoortEntiteitEnEntiteitId(SoortEntiteit.PAKKET, message.getId());
    }

}
