//package nl.lakedigital.djfc.messaging.reciever;
//
//import inloggen.SessieHolder;
//import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
//import nl.lakedigital.as.messaging.response.PolisOpslaanResponse;
//import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
//import nl.lakedigital.djfc.domain.Pakket;
//import nl.lakedigital.djfc.messaging.mapper.DomainPakketNaarMessagingPakketMapper;
//import nl.lakedigital.djfc.messaging.mapper.MessagingPakketNaarDomainPakketMapper;
//import nl.lakedigital.djfc.service.PolisService;
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.inject.Inject;
//import javax.jms.JMSException;
//import javax.jms.TextMessage;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import java.io.StringWriter;
//import java.util.List;
//import java.util.function.Consumer;
//import java.util.stream.Collectors;
//
//public class PolisOpslaanRequestReciever extends AbstractReciever<PolisOpslaanRequest> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanRequestReciever.class);
//
//    @Inject
//    private PolisService polisService;
//    @Inject
//    private List<nl.lakedigital.djfc.domain.Polis> polissen;
//    @Inject
//    private IdentificatieClient identificatieClient;
//
//    public PolisOpslaanRequestReciever() {
//        super(PolisOpslaanRequest.class, LOGGER);
//    }
//
//    @Override
//    public void verwerkMessage(PolisOpslaanRequest polisOpslaanRequest) {
//        LOGGER.debug("Inkomend request {}", ReflectionToStringBuilder.toString(polisOpslaanRequest));
//        for (nl.lakedigital.as.messaging.domain.Pakket p : polisOpslaanRequest.getPokketten()) {
//            LOGGER.debug(ReflectionToStringBuilder.toString(p));
//        }
//        List<Pakket> pakkettenOpslaan = polisOpslaanRequest.getPokketten().stream().map(new MessagingPakketNaarDomainPakketMapper(polisService, polissen, identificatieClient))//
//                .map(polis -> {
//                    LOGGER.debug(ReflectionToStringBuilder.toString(polis));
//                    return polis;
//                }).collect(Collectors.toList());
//
//        pakkettenOpslaan.stream().forEach(new Consumer<Pakket>() {
//            @Override
//            public void accept(Pakket pakket) {
//                polisService.opslaan(pakket);
//            }
//        });
//
//        if (replyTo != null) {
//            LOGGER.debug("Response versturen");
//            PolisOpslaanResponse polisOpslaanResponse = new PolisOpslaanResponse();
//            polisOpslaanResponse.setAntwoordOp(polisOpslaanRequest);
//            polisOpslaanResponse.setPakketten(pakkettenOpslaan.stream().map(new DomainPakketNaarMessagingPakketMapper()).collect(Collectors.toList()));
//
//            try {
//                setupMessageQueueConsumer();
//
//                polisOpslaanResponse.setTrackAndTraceId(SessieHolder.get().getTrackAndTraceId());
//                polisOpslaanResponse.setIngelogdeGebruiker(SessieHolder.get().getIngelogdeGebruiker());
//
//                JAXBContext jaxbContext = JAXBContext.newInstance(PolisOpslaanResponse.class);
//                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//                StringWriter sw = new StringWriter();
//
//                jaxbMarshaller.marshal(polisOpslaanResponse, sw);
//
//                TextMessage message = session.createTextMessage(sw.toString());
//
//                LOGGER.debug("Verzenden message {}", message.getText());
//                this.replyProducer.send(replyTo, message);
//            } catch (JMSException | JAXBException e) {
//                LOGGER.error("{}", e);
//            }
//        }
//
//    }
//}
