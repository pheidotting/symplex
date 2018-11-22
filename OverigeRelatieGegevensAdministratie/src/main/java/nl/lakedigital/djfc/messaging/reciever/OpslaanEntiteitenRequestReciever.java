package nl.lakedigital.djfc.messaging.reciever;

import com.codahale.metrics.Timer;
import nl.lakedigital.as.messaging.request.OpslaanEntiteitenRequest;
import nl.lakedigital.as.messaging.response.Response;
import nl.lakedigital.djfc.client.identificatie.IdentificatieClient;
import nl.lakedigital.djfc.commons.domain.*;
import nl.lakedigital.djfc.messaging.mappers.MessagingAdresNaarDomainAdresMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingOpmerkingNaarDomainOpmerkingMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingRekeningNummerNaarDomainRekeningNummerMapper;
import nl.lakedigital.djfc.messaging.mappers.MessagingTelefoonnummerNaarDomainTelefoonnummerMapper;
import nl.lakedigital.djfc.metrics.MetricsService;
import nl.lakedigital.djfc.service.AdresService;
import nl.lakedigital.djfc.service.OpmerkingService;
import nl.lakedigital.djfc.service.RekeningNummerService;
import nl.lakedigital.djfc.service.TelefoonnummerService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OpslaanEntiteitenRequestReciever extends AbstractReciever<OpslaanEntiteitenRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpslaanEntiteitenRequestReciever.class);

    @Inject
    private OpmerkingService opmerkingService;
    @Inject
    private TelefoonnummerService telefoonnummerService;
    @Inject
    private RekeningNummerService rekeningNummerService;
    @Inject
    private AdresService adresService;
    @Inject
    private IdentificatieClient identificatieClient;
    @Inject
    private MetricsService metricsService;

    public OpslaanEntiteitenRequestReciever() {
        super(OpslaanEntiteitenRequest.class, LOGGER);
    }

    @Override
    public void verwerkMessage(OpslaanEntiteitenRequest opslaanEntiteitenRequest) {
        Timer.Context timer = metricsService.addTimerMetric("verwerkMessage", OpslaanEntiteitenRequestReciever.class);

        List<SoortEntiteitEnEntiteitId> ids = new ArrayList<>();

        ids.addAll(opmerkingService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Opmerking)//
                .map(new MessagingOpmerkingNaarDomainOpmerkingMapper(identificatieClient, opmerkingService, opslaanEntiteitenRequest.getIngelogdeGebruiker()))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId()));

        ids.addAll(adresService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Adres)//
                .map(new MessagingAdresNaarDomainAdresMapper(identificatieClient, adresService))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId()));

        ids.addAll(telefoonnummerService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof Telefoonnummer)//
                .map(new MessagingTelefoonnummerNaarDomainTelefoonnummerMapper(identificatieClient, telefoonnummerService))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId()));

        ids.addAll(rekeningNummerService.opslaan(opslaanEntiteitenRequest.getLijst().stream()//
                .filter(abstracteEntiteitMetSoortEnId -> abstracteEntiteitMetSoortEnId instanceof RekeningNummer)//
                .map(new MessagingRekeningNummerNaarDomainRekeningNummerMapper(identificatieClient, rekeningNummerService))//
                .collect(Collectors.toList()), SoortEntiteit.valueOf(opslaanEntiteitenRequest.getSoortEntiteit().name()), opslaanEntiteitenRequest.getEntiteitId()));

        if (replyTo != null) {
            LOGGER.debug("Response versturen");
            Response response = new Response();
            response.getSoortEntiteitEnEntiteitIds().addAll(ids);

            try {
                setupMessageQueueConsumer();

                response.setTrackAndTraceId(MDC.get("trackAndTraceId"));
                response.setIngelogdeGebruiker(MDC.get("ingelogdeGebruiker") == null ? null : Long.valueOf(MDC.get("ingelogdeGebruiker")));
                response.setIngelogdeGebruikerOpgemaakt(MDC.get("ingelogdeGebruikerOpgemaakt"));
                response.setUrl(MDC.get("url"));
                response.setHoofdSoortEntiteit(opslaanEntiteitenRequest.getHoofdSoortEntiteit());
                response.setUitgaandeOpdrachtId(opslaanEntiteitenRequest.getUitgaandeOpdrachtId());

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
