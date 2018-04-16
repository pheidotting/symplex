package nl.dias.service;

import nl.dias.domein.Kantoor;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
import nl.dias.messaging.sender.KantoorAangemeldRequestSender;
import nl.dias.repository.KantoorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class KantoorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KantoorService.class);

    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private KantoorAangemeldRequestSender kantoorAangemeldRequestSender;

    public void aanmelden(Kantoor kantoor) throws PostcodeNietGoedException, TelefoonnummerNietGoedException, IbanNietGoedException, BsnNietGoedException {
        LOGGER.debug("Aanmelden Kantoor");
        kantoorRepository.opslaan(kantoor);
        kantoorAangemeldRequestSender.send(kantoor);
    }
}
