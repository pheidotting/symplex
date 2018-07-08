package nl.lakedigital.as.messaging.request.taak;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "wijzigingTaakOpslaanResponse")
public class WijzigingTaakOpslaanResponse extends AbstractMessage {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
