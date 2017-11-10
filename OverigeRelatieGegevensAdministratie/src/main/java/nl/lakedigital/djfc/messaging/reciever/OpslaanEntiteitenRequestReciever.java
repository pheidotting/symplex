package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.domain.Opmerking;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.json.Identificatie;
import nl.lakedigital.djfc.domain.SoortEntiteit;
import nl.lakedigital.djfc.service.OpmerkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.stream.Collectors;

public class OpslaanEntiteitenRequestReciever extends AbstractReciever<OpslaanEntiteitenRequest>{
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanEntiteitenRequestReciever.class);

    @Inject
    private OpmerkingService opmerkingService;
    @Inject
    private IdentificatieClient identificatieClient;

    public OpslaanEntiteitenRequestReciever() {
        super(OpslaanEntiteitenRequest.class, LOGGER);
    }
    @Override
    public void verwerkMessage(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
        opmerkingService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Opmerking)//
                .map(abstracteEntiteitMetSoortEnId -> {
                    Opmerking opm =(Opmerking)abstracteEntiteitMetSoortEnId;

                    Identificatie identificatie = identificatieClient.zoekIdentificatieCode(opm.getIdentificatie());

                    nl.lakedigital.djfc.domain.Opmerking opmerking = null;
                    if (identificatie != null && identificatie.getEntiteitId() != null) {
                        opmerking = opmerkingService.lees(identificatie.getEntiteitId());
                    } else {
                        opmerking = new nl.lakedigital.djfc.domain.Opmerking();
                    }

                    opmerking.setOpmerking(opm.getTekst());
                    opmerking.setMedewerker(opm.getMedewerker());
                    opmerking.setSoortEntiteit(SoortEntiteit.valueOf(opm.getSoortEntiteit().name()));opmerking.setEntiteitId(opm.getEntiteitId());

                    return opmerking;
                }).collect(Collectors.toList()));
    }
}
