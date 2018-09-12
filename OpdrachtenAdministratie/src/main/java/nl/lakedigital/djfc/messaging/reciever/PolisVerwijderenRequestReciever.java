//package nl.lakedigital.djfc.messaging.reciever;
//
//import nl.lakedigital.as.messaging.request.PolisVerwijderenRequest;
//import nl.lakedigital.djfc.service.PolisService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//
//public class PolisVerwijderenRequestReciever extends AbstractReciever<PolisVerwijderenRequest> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(PolisVerwijderenRequestReciever.class);
//
//    @Inject
//    private PolisService polisService;
//
//    public PolisVerwijderenRequestReciever() {
//        super(PolisVerwijderenRequest.class, LOGGER);
//    }
//
//    @Override
//    public void verwerkMessage(PolisVerwijderenRequest polisVerwijderenRequest) {
//        polisService.verwijder(polisVerwijderenRequest.getIds());
//    }
//}
