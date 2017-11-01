package nl.lakedigital.djfc.commons.xml;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "opvragenStatusSchadeResponse")
public class OpvragenStatusSchadeResponse {
    private List<String> statusSchade;

    public List<String> getStatusSchade() {
        if (statusSchade == null) {
            statusSchade = new ArrayList<>();
        }
        return statusSchade;
    }

    public void setStatusSchade(List<String> statusSchade) {
        this.statusSchade = statusSchade;
    }
}
