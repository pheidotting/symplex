package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wachtwoordVergetenRequest")
public class WachtwoordVergetenRequest extends AbstractMessage {
    private Long gebruikerId;
    private String email;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String nieuwWachtwoord;
    private String kantoorNaam;
    private String kantoorEmail;

    public WachtwoordVergetenRequest() {
    }

    public WachtwoordVergetenRequest(Long gebruikerId, String email, String voornaam, String tussenvoegsel, String achternaam, String nieuwWachtwoord, String kantoorNaam, String kantoorEmail) {
        this.gebruikerId = gebruikerId;
        this.email = email;
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.nieuwWachtwoord = nieuwWachtwoord;
        this.kantoorNaam = kantoorNaam;
        this.kantoorEmail = kantoorEmail;
    }

    public Long getGebruikerId() {
        return gebruikerId;
    }

    public void setGebruikerId(Long gebruikerId) {
        this.gebruikerId = gebruikerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getNieuwWachtwoord() {
        return nieuwWachtwoord;
    }

    public void setNieuwWachtwoord(String nieuwWachtwoord) {
        this.nieuwWachtwoord = nieuwWachtwoord;
    }

    public String getKantoorNaam() {
        return kantoorNaam;
    }

    public void setKantoorNaam(String kantoorNaam) {
        this.kantoorNaam = kantoorNaam;
    }

    public String getKantoorEmail() {
        return kantoorEmail;
    }

    public void setKantoorEmail(String kantoorEmail) {
        this.kantoorEmail = kantoorEmail;
    }
}
