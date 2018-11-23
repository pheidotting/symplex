package nl.lakedigital.as.messaging.opdracht.opdracht;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "verwijderPolisOpdracht")
public class VerwijderPolisOpdracht extends AbstractMessage {
    private Long id;

    public VerwijderPolisOpdracht() {
    }

    public VerwijderPolisOpdracht(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
