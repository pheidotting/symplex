package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.commons.domain.uitgaand.UitgaandeOpdracht;
import nl.lakedigital.djfc.messaging.sender.AbstractSender;
import nl.lakedigital.djfc.repository.UitgaandeOpdrachtRepository;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
public class VerstuurUitgaandeOpdrachtenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerstuurUitgaandeOpdrachtenService.class);

    @Inject
    private UitgaandeOpdrachtRepository uitgaandeOpdrachtRepository;
    @Inject
    private List<AbstractSender> senders;

    public void verstuurUitgaandeOpdrachten() {
        verstuurUitgaandeOpdrachten(null);
    }

    public void verstuurUitgaandeOpdrachten(List<UitgaandeOpdracht> uitgaandeOpdrachtenIn) {
        List<UitgaandeOpdracht> uitgaandeOpdrachten = uitgaandeOpdrachtenIn == null ? uitgaandeOpdrachtRepository.teVersturenUitgaandeOpdrachten() : uitgaandeOpdrachtenIn;
        LOGGER.info("Versturen {} uitgaande opdrachten", uitgaandeOpdrachten.size());

        for (UitgaandeOpdracht uitgaandeOpdracht : uitgaandeOpdrachten) {
            Optional<AbstractSender> abstractSenderOptional = senders.stream().filter(abstractSender -> abstractSender.getSoortEntiteiten().contains(uitgaandeOpdracht.getSoortEntiteit()) && abstractSender.getSoortOpdracht() == uitgaandeOpdracht.getInkomendeOpdracht().getSoortOpdracht()).findFirst();
            if (abstractSenderOptional.isPresent()) {
                LOGGER.trace("Gevonden sender: {}", abstractSenderOptional.get());
                abstractSenderOptional.get().send(uitgaandeOpdracht.getBericht());
            } else {
                LOGGER.error("Bij opdracht met Id {}, type {} is geen sender gevonden", uitgaandeOpdracht.getId(), uitgaandeOpdracht.getSoortEntiteit());
            }

            uitgaandeOpdracht.setTijdstipVerzonden(LocalDateTime.now());
        }

        uitgaandeOpdrachtRepository.opslaan(uitgaandeOpdrachten);
    }

}
