package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.taak.OpslaanTaakRequest;
import nl.lakedigital.as.messaging.response.Response;
import nl.lakedigital.djfc.commons.domain.SoortEntiteit;
import nl.lakedigital.djfc.commons.domain.TaakStatus;
import nl.lakedigital.djfc.messaging.sender.EntiteitenOpgeslagenRequestSender;
import nl.lakedigital.djfc.messaging.sender.OpslaanTaakResponseSender;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.TaakService;
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

public class OpslaanTaakRequestReciever extends AbstractReciever<OpslaanTaakRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanTaakRequestReciever.class);

    @Inject
    private TaakService taakService;
    @Inject
    private OpslaanTaakResponseSender nieuweTaakResponseSender;
    @Inject
    private EntiteitenOpgeslagenRequestSender entiteitenOpgeslagenRequestSender;
    @Inject
    private MetricsService metricsService;

    public OpslaanTaakRequestReciever() {
        super(OpslaanTaakRequest.class, LOGGER);
    }

    @Override
    //    @Transactional
    public void verwerkMessage(OpslaanTaakRequest opslaanTaakRequest) {
        Timer.Context context = metricsService.addTimerMetric("verwerkMessage", OpslaanTaakRequestReciever.class);

        LOGGER.info("Verwerken NieuweTaakRequest");
        Long taakId = null;
        if (opslaanTaakRequest.getId() == null) {
            taakId = taakService.nieuweTaak(opslaanTaakRequest.getDeadline(), opslaanTaakRequest.getTitel(), opslaanTaakRequest.getOmschrijving(), opslaanTaakRequest.getEntiteitId(), SoortEntiteit.valueOf(opslaanTaakRequest.getSoortEntiteit().name()), opslaanTaakRequest.getToegewezenAan());

            //            nieuweTaakResponseSender.send(taakId);
            //            SoortEntiteitEnEntiteitId soortEntiteitEnEntiteitId = new SoortEntiteitEnEntiteitId();
            //            soortEntiteitEnEntiteitId.setEntiteitId(taakId);
            //            soortEntiteitEnEntiteitId.setSoortEntiteit(nl.lakedigital.as.messaging.domain.SoortEntiteit.TAAK);
            //            entiteitenOpgeslagenRequestSender.send(newArrayList(soortEntiteitEnEntiteitId));
        } else {
            taakId = opslaanTaakRequest.getId();
            taakService.wijzig(taakService.lees(opslaanTaakRequest.getId()), TaakStatus.valueOf(opslaanTaakRequest.getStatus()), opslaanTaakRequest.getToegewezenAan());
        }

        if (replyTo != null) {
            LOGGER.debug("Response versturen");
            Response response = new Response(taakId);

            try {
                setupMessageQueueConsumer();

                response.setTrackAndTraceId(MDC.get("trackAndTraceId"));
                response.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
                response.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
                response.setUrl(MDC.get("url"));
                response.setHoofdSoortEntiteit(opslaanTaakRequest.getHoofdSoortEntiteit());

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

        metricsService.stop(context);
    }
}
