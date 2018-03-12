package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Email;
import nl.lakedigital.djfc.service.verzenden.VerzendService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.inject.Inject;

@Service
public class KantoorAangemeldService extends CommunicatieProductService {
    @Inject
    private VerzendService verzendService;

    public void stuurMail(Long relatieId, String email, String voornaam, String tussenvoegsel, String achternaam, String wachtwoord, String kantoorNaam, String kantoorEmail) {
        Context context = new Context();

        context.setVariable("wachtwoord", wachtwoord);

        String tekst = templateEngine.process("kantoor-aangemeld", context);

        Email mail = (Email) maakCommunicatieProduct(null, SoortCommunicatieProduct.EMAIL, relatieId, email, tekst, "Welkom bij Symplex!", null, null);

        mail.setEmailOntvanger(email);
        mail.setNaamOntvanger(maakNaam(voornaam, tussenvoegsel, achternaam).toString());
        mail.setEmailVerzender(kantoorEmail);
        mail.setNaamVerzender(kantoorNaam);

        communicatieProductRepository.opslaan(mail);

        verzendService.verzend();
    }
}

