package nl.lakedigital.djfc.service;

import nl.lakedigital.djfc.service.verzenden.VerzendService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class WachtwoordVergetenMailService extends CommunicatieProductService {
    @Inject
    private VerzendService verzendService;

    public void stuurMail(String email, String voornaam, String tussenvoegsel, String achternaam, String wachtwoord) {
        Long id = maakCommunicatieProduct(null, SoortCommunicatieProduct.EMAIL, email, voornaam, tussenvoegsel, achternaam, "tekst", "onderwerp", null, null);

        verzendService.verzend();
    }
}

