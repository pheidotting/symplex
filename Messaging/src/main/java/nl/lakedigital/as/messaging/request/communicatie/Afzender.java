package nl.lakedigital.as.messaging.request.communicatie;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "afzender")
public class Afzender {
    private String naam;
    private String email;

    public Afzender() {
    }

    public Afzender(String naam, String email) {
        this.naam = naam;
        this.email = email;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
