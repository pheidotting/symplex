package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.domain.Email;
import nl.lakedigital.djfc.service.verzenden.VerzendService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.inject.Inject;

@Service
public class NieuweVersieMailService extends CommunicatieProductService {
    @Inject
    private VerzendService verzendService;

    public void stuurMail(Long relatieId, String email, String voornaam, String tussenvoegsel, String achternaam, String versie, String releasenotes) {
        Context context = new Context();

        context.setVariable("versie", versie);
        context.setVariable("releasenotes", releasenotes.split("\n"));

        String tekst = templateEngine.process("nieuwe-versie", context);

        Email mail = (Email) maakCommunicatieProduct(null, SoortCommunicatieProduct.EMAIL, relatieId, email, tekst, "Nieuwe versie van Symplex", null, null);

        mail.setEmailOntvanger(email);
        mail.setNaamOntvanger(maakNaam(voornaam, tussenvoegsel, achternaam).toString());
        mail.setNaamVerzender("Symplex");
        mail.setEmailVerzender("noreply@symplexict.nl");

        communicatieProductRepository.opslaan(mail);

        verzendService.verzend();
    }
}

