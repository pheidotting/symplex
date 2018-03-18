package nl.lakedigital.as.messaging.request.communicatie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wachtwoordVergetenRequest")
public class WachtwoordVergetenRequest extends AbstractCommunicatieRequest {
    private String nieuwWachtwoord;

    public WachtwoordVergetenRequest() {
    }

    public WachtwoordVergetenRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String nieuwWachtwoord, String kantoorNaam, String kantoorEmail) {
        super(gebruikerId, email, voornaam, tussenvoegsel, achternaam, kantoorNaam, kantoorEmail);

        this.nieuwWachtwoord = nieuwWachtwoord;
    }

    public String getNieuwWachtwoord() {
        return nieuwWachtwoord;
    }

    public void setNieuwWachtwoord(String nieuwWachtwoord) {
        this.nieuwWachtwoord = nieuwWachtwoord;
    }

}
