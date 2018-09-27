package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.PolisOpslaanRequest;
import nl.lakedigital.as.messaging.response.Response;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.domain.Pakket;
import nl.lakedigital.djfc.domain.Polis;
import nl.lakedigital.djfc.messaging.mapper.MessagingPakketNaarDomainPakketMapper;
import nl.lakedigital.djfc.service.PolisService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
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

public class PolisOpslaanRequestReciever extends AbstractReciever<PolisOpslaanRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisOpslaanRequestReciever.class);

    @Inject
    private PolisService polisService;
    @Inject
    private List<Polis> polissen;
    @Inject
    private IdentificatieClient identificatieClient;

    public PolisOpslaanRequestReciever() {
        super(PolisOpslaanRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(PolisOpslaanRequest polisOpslaanRequest) {
        LOGGER.debug("Inkomend request {}", ReflectionToStringBuilder.toString(polisOpslaanRequest));
        for (nl.lakedigital.djfc.commons.domain.Pakket p : polisOpslaanRequest.getPokketten()) {
            LOGGER.debug(ReflectionToStringBuilder.toString(p));
        }
        List<Pakket> pakkettenOpslaan = polisOpslaanRequest.getPokketten().stream().map(new MessagingPakketNaarDomainPakketMapper(polisService, polissen, identificatieClient))//
                .map(polis -> {
                    LOGGER.debug(ReflectionToStringBuilder.toString(polis));
                    return polis;
                }).collect(Collectors.toList());

        pakkettenOpslaan.stream().forEach(pakket -> polisService.opslaan(pakket));

        if (replyTo != null) {
            LOGGER.debug("Response versturen");
            Response response = new Response(pakkettenOpslaan.get(0).getId());

            try {
                setupMessageQueueConsumer();

                response.setTrackAndTraceId(MDC.get("trackAndTraceId"));
                response.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
                response.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
                response.setUrl(MDC.get("url"));
                response.setHoofdSoortEntiteit(polisOpslaanRequest.getHoofdSoortEntiteit());

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
