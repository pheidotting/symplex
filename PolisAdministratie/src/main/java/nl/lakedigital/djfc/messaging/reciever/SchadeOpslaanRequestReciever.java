package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.SchadeOpslaanRequest;
import nl.lakedigital.as.messaging.response.SchadeOpslaanResponse;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.messaging.mapper.DomainSchadeNaarMessagingSchadeMapper;
import nl.lakedigital.djfc.messaging.mapper.MessagingSchadeNaarDomainSchadeMapper;
import nl.lakedigital.djfc.service.SchadeService;
import nl.lakedigital.djfc.service.envers.SessieHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

public class SchadeOpslaanRequestReciever extends AbstractReciever<SchadeOpslaanRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchadeOpslaanRequestReciever.class);

    @Inject
    private SchadeService schadeService;
    @Inject
    private IdentificatieClient identificatieClient;

    public SchadeOpslaanRequestReciever() {
        super(SchadeOpslaanRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(SchadeOpslaanRequest schadeOpslaanRequest) {
        List<Schade> schadesOpslaan = schadeOpslaanRequest.getSchades().stream().map(new MessagingSchadeNaarDomainSchadeMapper(schadeService, identificatieClient))//
                .collect(Collectors.toList());

        if (replyTo != null) {
            SchadeOpslaanResponse schadeOpslaanResponse = new SchadeOpslaanResponse();
            schadeOpslaanResponse.setAntwoordOp(schadeOpslaanRequest);
            schadeOpslaanResponse.setSchades(schadesOpslaan.stream().map(new DomainSchadeNaarMessagingSchadeMapper()).collect(Collectors.toList()));

            try {
                setupMessageQueueConsumer();

                schadeOpslaanResponse.setTrackAndTraceId(SessieHolder.get().getTrackAndTraceId());
                schadeOpslaanResponse.setIngelogdeGebruiker(SessieHolder.get().getIngelogdeGebruiker());

                JAXBContext jaxbContext = JAXBContext.newInstance(SchadeOpslaanResponse.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter sw = new StringWriter();

                jaxbMarshaller.marshal(schadeOpslaanResponse, sw);

                TextMessage message = session.createTextMessage(sw.toString());

                LOGGER.debug("Verzenden message {}", message.getText());
                this.replyProducer.send(replyTo, message);
            } catch (JMSException | JAXBException e) {
                LOGGER.error("{}", e);
            }
        }

    }
}
