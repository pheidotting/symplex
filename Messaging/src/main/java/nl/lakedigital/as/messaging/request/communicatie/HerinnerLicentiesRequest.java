package nl.lakedigital.as.messaging.request.communicatie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "herinnerLicentiesRequest")
public class HerinnerLicentiesRequest extends AbstractCommunicatieRequest {
    private String huidigeLicentie;
    private int aantalDagenNog;

    public HerinnerLicentiesRequest() {
    }

    public HerinnerLicentiesRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String kantoorNaam, String kantoorEmail, String huidigeLicentie, int aantalDagenNog) {
        super(gebruikerId, email, voornaam, tussenvoegsel, achternaam, kantoorNaam, kantoorEmail);

        this.huidigeLicentie = huidigeLicentie;
        this.aantalDagenNog = aantalDagenNog;
    }

    public String getHuidigeLicentie() {
        return huidigeLicentie;
    }

    public void setHuidigeLicentie(String huidigeLicentie) {
        this.huidigeLicentie = huidigeLicentie;
    }

    public int getAantalDagenNog() {
        return aantalDagenNog;
    }

    public void setAantalDagenNog(int aantalDagenNog) {
        this.aantalDagenNog = aantalDagenNog;
    }
}
