package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.as.messaging.request.EntiteitenOpgeslagenRequest;
import nl.lakedigital.djfc.domain.Identificatie;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

public class EntiteitenOpgeslagenRequestReciever extends AbstractReciever<EntiteitenOpgeslagenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntiteitenOpgeslagenRequestReciever.class);

    @Inject
    private IdentificatieService identificatieService;

    public EntiteitenOpgeslagenRequestReciever() {
        super(EntiteitenOpgeslagenRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(EntiteitenOpgeslagenRequest entiteitenOpgeslagenRequest) {
//        LOGGER.debug("Opslaan {} entiteiten",entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds().size());
        for(SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId:entiteitenOpgeslagenRequest.getSoortEntiteitEnEntiteitIds()){
            Identificatie identificatie=new Identificatie(soortEntiteitEnEntiteitId.getSoortEntiteit().name(),soortEntiteitEnEntiteitId.getEntiteitId());
            LOGGER.debug("{}",identificatie);
            identificatieService.opslaan(identificatie);
        }
    }
}
