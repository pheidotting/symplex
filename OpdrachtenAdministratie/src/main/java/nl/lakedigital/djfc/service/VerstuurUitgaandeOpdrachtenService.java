package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.AbstractMessage;
import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.messaging.sender.AbstractSender;
import nl.lakedigital.djfc.repository.UitgaandeOpdrachtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VerstuurUitgaandeOpdrachtenService {
    private final static Logger LOGGER = LoggerFactory.getLogger(VerstuurUitgaandeOpdrachtenService.class);

    @Inject
    private UitgaandeOpdrachtRepository uitgaandeOpdrachtRepository;
    @Inject
    private List<AbstractSender> senders;

    public void verstuurUitgaandeOpdrachten() {
        List<UitgaandeOpdracht> uitgaandeOpdrachten = uitgaandeOpdrachtRepository.teVersturenUitgaandeOpdrachten();

        uitgaandeOpdrachten.stream().forEach(uitgaandeOpdracht -> {
            Optional<AbstractSender> abstractSenderOptional = senders.stream().filter(abstractSender -> abstractSender.getSoortEntiteiten().contains(uitgaandeOpdracht.getSoortEntiteit())).findFirst();
            if (abstractSenderOptional.isPresent()) {
                abstractSenderOptional.get().send(unmarshal(uitgaandeOpdracht.getBericht()));
            }

            uitgaandeOpdracht.setTijdstipVerzonden(LocalDateTime.now());
        });

        uitgaandeOpdrachtRepository.opslaan(uitgaandeOpdrachten);
    }

    private AbstractMessage unmarshal(String bericht) {
        AbstractMessage message = null;

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AbstractMessage.class);
            Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();

            jaxbUnMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            message = (AbstractMessage) jaxbUnMarshaller.unmarshal(new StringReader(bericht));

        } catch (JAXBException e) {
            LOGGER.error("Error : ", e);
        }

        return message;
    }

}
