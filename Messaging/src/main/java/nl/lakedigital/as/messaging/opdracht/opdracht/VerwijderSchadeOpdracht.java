package nl.lakedigital.as.messaging.opdracht.opdracht;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "verwijderSchadeOpdracht")
public class VerwijderSchadeOpdracht extends AbstractMessage {
    private Long id;

    public VerwijderSchadeOpdracht() {
    }

    public VerwijderSchadeOpdracht(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
