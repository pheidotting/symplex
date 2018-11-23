package nl.lakedigital.as.messaging.response.relatie;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "opslaanRelatieResponse")
public class OpslaanRelatieResponse extends AbstractMessage {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
