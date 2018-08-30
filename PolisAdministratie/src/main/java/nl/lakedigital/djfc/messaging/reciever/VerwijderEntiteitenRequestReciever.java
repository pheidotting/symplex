package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.djfc.service.PolisService;
import nl.lakedigital.djfc.service.SchadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class VerwijderEntiteitenRequestReciever extends AbstractReciever<VerwijderEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitenRequestReciever.class);

    @Inject
    private PolisService polisService;
    @Inject
    private SchadeService schadeService;

    public VerwijderEntiteitenRequestReciever() {
        super(VerwijderEntiteitenRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(VerwijderEntiteitenRequest verwijderEntiteitenRequest) {
        //        List<Polis> polissen = polisService.alles(SoortEntiteit.valueOf(verwijderEntiteitenRequest.getSoortEntiteit().name()), verwijderEntiteitenRequest.getEntiteitId());

        //        List<Schade> schades = schadeService.alles(SoortEntiteit.valueOf(verwijderEntiteitenRequest.getSoortEntiteit().name()), verwijderEntiteitenRequest.getEntiteitId());
        //
        //        for (Schade schade : schades) {
        //            schadeService.verwijder(schade.getId());
        //        }
        //        polisService.verwijder(polissen.stream().map(polis -> polis.getId()).collect(Collectors.toList()));
    }
}
