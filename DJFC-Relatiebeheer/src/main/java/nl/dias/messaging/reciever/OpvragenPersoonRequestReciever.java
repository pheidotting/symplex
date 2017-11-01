package nl.dias.messaging.reciever;

import nl.dias.service.OpvragenPersoonsOfBedrijfsGegevensService;
import nl.lakedigital.as.messaging.domain.Bedrijf;
import nl.lakedigital.as.messaging.domain.BedrijfOfPersoon;
import nl.lakedigital.as.messaging.domain.Persoon;
import nl.lakedigital.as.messaging.request.OpvragenPersoonSOfBedrijfsGegevensRequest;
import nl.lakedigital.as.messaging.response.OpvragenPersoonSOfBedrijfsGegevensResponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class OpvragenPersoonRequestReciever extends AbstractReciever<OpvragenPersoonSOfBedrijfsGegevensRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpvragenPersoonRequestReciever.class);

    public OpvragenPersoonRequestReciever() {
        super(OpvragenPersoonSOfBedrijfsGegevensRequest.class, LOGGER);
    }

    @Inject
    private OpvragenPersoonsOfBedrijfsGegevensService opvragenPersoonsOfBedrijfsGegevensService;

    @Override
    public void verwerkMessage(OpvragenPersoonSOfBedrijfsGegevensRequest opvragenPersoonRequest) {
        OpvragenPersoonSOfBedrijfsGegevensResponse opvragenPersoonResponse = new OpvragenPersoonSOfBedrijfsGegevensResponse();

        LOGGER.debug(ReflectionToStringBuilder.toString(opvragenPersoonRequest.getAntwoordOp()));

        opvragenPersoonResponse.setAntwoordOp(opvragenPersoonRequest.getAntwoordOp());
        opvragenPersoonResponse.setIngelogdeGebruiker(opvragenPersoonRequest.getIngelogdeGebruiker());
        opvragenPersoonResponse.setTrackAndTraceId(opvragenPersoonRequest.getTrackAndTraceId());

        BedrijfOfPersoon bedrijfOfPersoon = opvragenPersoonsOfBedrijfsGegevensService.opvragenGegevens(opvragenPersoonRequest.getEntiteitId(), opvragenPersoonRequest.getSoortEntiteit());

        if (bedrijfOfPersoon instanceof Persoon) {
            opvragenPersoonResponse.setPersoon((Persoon) bedrijfOfPersoon);
        } else {
            opvragenPersoonResponse.setBedrijf((Bedrijf) bedrijfOfPersoon);
        }

        if (replyTo != null) {
            try {
                setupMessageQueueConsumer();

                JAXBContext jaxbContext = JAXBContext.newInstance(OpvragenPersoonSOfBedrijfsGegevensResponse.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                StringWriter sw = new StringWriter();

                jaxbMarshaller.marshal(opvragenPersoonResponse, sw);

                TextMessage message = session.createTextMessage(sw.toString());

                LOGGER.debug("Verzenden message {}", message.getText());
                this.replyProducer.send(replyTo, message);
            } catch (JMSException | JAXBException e) {
                LOGGER.error("{}", e);
            }
        }
    }

}
