package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.opdracht.opdracht.VerwijderSchadeOpdracht;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.commons.domain.SoortOpdracht;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.mapper.SchadeVerwijderRequestMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class VerwijderSchadeAfhandelenService extends AfhandelenInkomendeOpdrachtService<VerwijderSchadeOpdracht> {
    @Override
    protected List<UitgaandeOpdracht> genereerUitgaandeOpdrachten(VerwijderSchadeOpdracht opdracht) {
        return new SchadeVerwijderRequestMapper().map(opdracht.getId());
    }

    @Override
    protected SoortOpdracht getSoortOpdracht() {
        return SoortOpdracht.VERWIJDEREN;
    }

    @Override
    public List<SoortEntiteit> getSoortEntiteiten() {
        return newArrayList(SoortEntiteit.SCHADE);
    }

    @Override
    protected UitgaandeOpdracht bepaalUitgaandeOpdrachtWachtenOp(VerwijderSchadeOpdracht opdracht, List<UitgaandeOpdracht> uitgaandeOpdrachten) {
        return null;
    }

    @Override
    protected SoortEntiteitEnEntiteitId getSoortEntiteitEnEntiteitId(VerwijderSchadeOpdracht message) {
        return new SoortEntiteitEnEntiteitId(SoortEntiteit.SCHADE, message.getId());
    }

}
