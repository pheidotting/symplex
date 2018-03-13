package nl.lakedigital.djfc.service;

import nl.lakedigital.as.messaging.request.communicatie.Geadresseerde;
import nl.lakedigital.djfc.domain.Email;
import nl.lakedigital.djfc.service.verzenden.VerzendService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.inject.Inject;
import java.util.List;

@Service
public class NieuweVersieMailService extends CommunicatieProductService {
    @Inject
    private VerzendService verzendService;

    public void stuurMail(List<Geadresseerde> geadresseerden, String versie, String releasenotes) {
        Context context = new Context();

        context.setVariable("versie", versie);
        context.setVariable("releasenotes", releasenotes.split("\n"));

        String tekst = templateEngine.process("nieuwe-versie", context);

        for (Geadresseerde geadresseerde : geadresseerden) {
            Email mail = (Email) maakCommunicatieProduct(null, SoortCommunicatieProduct.EMAIL, geadresseerde.getId(), geadresseerde.getEmail(), tekst, "Nieuwe versie van Symplex", null, null);

            mail.setEmailOntvanger(geadresseerde.getEmail());
            mail.setNaamOntvanger(maakNaam(geadresseerde.getVoornaam(), geadresseerde.getTussenvoegsel(), geadresseerde.getAchternaam()).toString());
            mail.setNaamVerzender("Symplex");
            mail.setEmailVerzender("noreply@symplexict.nl");

            communicatieProductRepository.opslaan(mail);
        }
        verzendService.verzend();
    }
}

