package nl.lakedigital.as.messaging.response.taak;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "nieuweTaakResponse")
public class NieuweTaakResponse extends AbstractMessage {
    private Long id;

    public NieuweTaakResponse(Long id) {
        this.id = id;
    }

    public NieuweTaakResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}