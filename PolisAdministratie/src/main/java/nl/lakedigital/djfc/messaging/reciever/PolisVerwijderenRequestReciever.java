package nl.lakedigital.djfc.messaging.reciever;

import nl.lakedigital.as.messaging.request.PolisVerwijderenRequest;
import nl.lakedigital.as.messaging.response.Response;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.service.PolisService;
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
import java.util.function.Consumer;

public class PolisVerwijderenRequestReciever extends AbstractReciever<PolisVerwijderenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(PolisVerwijderenRequestReciever.class);

    @Inject
    private PolisService polisService;

    public PolisVerwijderenRequestReciever() {
        super(PolisVerwijderenRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(PolisVerwijderenRequest polisVerwijderenRequest) {
        polisService.verwijder(polisVerwijderenRequest.getIds());

        if (replyTo != null) {
            LOGGER.debug("Response versturen");
            Response response = new Response();
            polisVerwijderenRequest.getIds().stream().forEach(new Consumer<Long>() {
                @Override
                public void accept(Long id) {
                    response.getSoortEntiteitEnEntiteitIds().add(new SoortEntiteitEnEntiteitId(SoortEntiteit.PAKKET, id));
                }
            });

            try {
                setupMessageQueueConsumer();

                response.setTrackAndTraceId(MDC.get("trackAndTraceId"));
                response.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
                response.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
                response.setUrl(MDC.get("url"));
                response.setHoofdSoortEntiteit(polisVerwijderenRequest.getHoofdSoortEntiteit());
                response.setUitgaandeOpdrachtId(polisVerwijderenRequest.getUitgaandeOpdrachtId());

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
