package nl.lakedigital.as.messaging.request;

import nl.lakedigital.as.messaging.AbstractMessage;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class SchadeVerwijderenRequest extends AbstractMessage {
    private List<Long> ids;

    public List<Long> getIds() {
        if (ids == null) {
            ids = new ArrayList<>();
        }
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
