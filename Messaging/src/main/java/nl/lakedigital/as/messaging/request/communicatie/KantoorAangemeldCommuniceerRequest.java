package nl.lakedigital.as.messaging.request.communicatie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "kantoorAangemeldCommuniceerRequest")
public class KantoorAangemeldCommuniceerRequest extends AbstractCommunicatieRequest {
    private String wachtwoord;

    public KantoorAangemeldCommuniceerRequest() {
    }

    public KantoorAangemeldCommuniceerRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String kantoorNaam, String kantoorEmail, String wachtwoord) {
        super(gebruikerId, email, voornaam, tussenvoegsel, achternaam, kantoorNaam, kantoorEmail);
        this.wachtwoord = wachtwoord;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
