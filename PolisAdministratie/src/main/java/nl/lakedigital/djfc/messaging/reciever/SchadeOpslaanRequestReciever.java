package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.SchadeOpslaanRequest;
import nl.lakedigital.as.messaging.response.Response;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.domain.Schade;
import nl.lakedigital.djfc.messaging.mapper.MessagingSchadeNaarDomainSchadeMapper;
import nl.lakedigital.djfc.service.SchadeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

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
            LOGGER.debug("Response versturen");
            Response response = new Response();
            response.getSoortEntiteitEnEntiteitIds().add(new SoortEntiteitEnEntiteitId(SoortEntiteit.SCHADE, schadesOpslaan.get(0).getId()));

            try {
                setupMessageQueueConsumer();

                response.setTrackAndTraceId(MDC.get("trackAndTraceId"));
                response.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
                response.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
                response.setUrl(MDC.get("url"));
                response.setHoofdSoortEntiteit(schadeOpslaanRequest.getHoofdSoortEntiteit());
                response.setUitgaandeOpdrachtId(schadeOpslaanRequest.getUitgaandeOpdrachtId());

                JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter sw = new StringWriter();

                jaxbMarshaller.marshal(response, sw);

                TextMessage message = session.createTextMessage(sw.toString());

                LOGGER.debug("Verzenden message {}, naar {}", message.getText(), replyTo);
                this.replyProducer.send(replyTo, message);
            } catch (JMSException | JAXBException e) {
                LOGGER.error("{}", e);
            }
        }

    }
}
