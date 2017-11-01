package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class VerwijderRelatiesRequest extends AbstractMessage {
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
