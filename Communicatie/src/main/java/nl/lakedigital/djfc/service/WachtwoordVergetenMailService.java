package nl.lakedigital.djfc.service;

import org.springframework.stereotype.Service;

@Service
public class WachtwoordVergetenMailService extends CommunicatieProductService {

    public void stuurMail(String email, String voornaam, String tussenvoegsel, String achternaam, String wachtwoord) {

        super.maakCommunicatieProduct(null, SoortCommunicatieProduct.EMAIL, email, voornaam, tussenvoegsel, achternaam, "tekst", "onderwerp", null, null);
    }
}

