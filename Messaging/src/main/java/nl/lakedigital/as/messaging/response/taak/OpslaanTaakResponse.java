package nl.lakedigital.as.messaging.response.taak;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "opslaanTaakResponse")
public class OpslaanTaakResponse extends AbstractMessage {
    private Long id;

    public OpslaanTaakResponse(Long id) {
        this.id = id;
    }

    public OpslaanTaakResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
