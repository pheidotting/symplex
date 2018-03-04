package nl.dias.service;

import nl.dias.domein.Kantoor;
import nl.dias.exception.BsnNietGoedException;
import nl.dias.exception.IbanNietGoedException;
import nl.dias.exception.PostcodeNietGoedException;
import nl.dias.exception.TelefoonnummerNietGoedException;
import nl.dias.messaging.sender.KantoorAangemeldRequestSender;
import nl.dias.repository.KantoorRepository;

import javax.inject.Inject;

public class KantoorService {
    @Inject
    private KantoorRepository kantoorRepository;
    @Inject
    private KantoorAangemeldRequestSender kantoorAangemeldRequestSender;

    public void aanmelden(Kantoor kantoor) throws PostcodeNietGoedException, TelefoonnummerNietGoedException, IbanNietGoedException, BsnNietGoedException {
        kantoorRepository.opslaanKantoor(kantoor);
        kantoorAangemeldRequestSender.send(kantoor);

    }
}
