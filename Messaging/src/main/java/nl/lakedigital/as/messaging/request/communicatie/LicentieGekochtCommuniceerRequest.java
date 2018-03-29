package nl.lakedigital.as.messaging.request.communicatie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "licentieGekochtCommuniceerRequest")
public class LicentieGekochtCommuniceerRequest extends AbstractCommunicatieRequest {
    private String licentieType;
    private Double prijs;

    public LicentieGekochtCommuniceerRequest() {
    }

    public LicentieGekochtCommuniceerRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String kantoorNaam, String kantoorEmail, String licentieType, Double prijs) {
        super(gebruikerId, email, voornaam, tussenvoegsel, achternaam, kantoorNaam, kantoorEmail);
        this.licentieType = licentieType;
        this.prijs = prijs;
    }

    public String getLicentieType() {
        return licentieType;
    }

    public void setLicentieType(String licentieType) {
        this.licentieType = licentieType;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }
}
