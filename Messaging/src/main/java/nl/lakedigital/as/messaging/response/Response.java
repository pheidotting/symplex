package nl.lakedigital.as.messaging.response;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response extends AbstractMessage {
    private Long id;

    public Response() {
    }

    public Response(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
