package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.VerwijderEntiteitenRequest;
import nl.lakedigital.as.messaging.response.Response;
import nl.lakedigital.djfc.commons.domain.Identificatie;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.SoortEntiteitEnEntiteitId;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.IdentificatieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class VerwijderEntiteitenRequestReciever  extends AbstractReciever<VerwijderEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerwijderEntiteitenRequestReciever.class);

    @Inject
    private IdentificatieService identificatieService;
    @Inject
    private MetricsService metricsService;

    public VerwijderEntiteitenRequestReciever() {
        super(VerwijderEntiteitenRequest.class, LOGGER);
    }

    @Override
    @Transactional
    public void verwerkMessage(VerwijderEntiteitenRequest verwijderEntiteitenRequest) {
        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", VerwijderEntiteitenRequestReciever.class);

        LOGGER.debug("Verwijder entiteit met soort {} en id {}", verwijderEntiteitenRequest.getSoortEntiteit(), verwijderEntiteitenRequest.getEntiteitId());
        Identificatie identificatie=     identificatieService.zoek(verwijderEntiteitenRequest.getSoortEntiteit().name(),verwijderEntiteitenRequest.getEntiteitId());

        identificatieService.verwijder(identificatie);

        if (replyTo != null) {
            LOGGER.debug("Response versturen");
            Response response = new Response(new SoortEntiteitEnEntiteitId(SoortEntiteit.valueOf(identificatie.getSoortEntiteit()), identificatie.getId()));

            try {
                setupMessageQueueConsumer();

                response.setTrackAndTraceId(MDC.get("trackAndTraceId"));
                response.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
                response.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
                response.setUrl(MDC.get("url"));
                response.setHoofdSoortEntiteit(verwijderEntiteitenRequest.getHoofdSoortEntiteit());

                JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter sw = new StringWriter();

                jaxbMarshaller.marshal(response, sw);

                TextMessage message = session.createTextMessage(sw.toString());

                LOGGER.debug("Verzenden message {}", message.getText());
                this.replyProducer.send(replyTo, message);
            } catch (JMSException | JAXBException e) {
                LOGGER.error("{}", e);
            }
        }
        metricsService.stop(timer);
    }
}
