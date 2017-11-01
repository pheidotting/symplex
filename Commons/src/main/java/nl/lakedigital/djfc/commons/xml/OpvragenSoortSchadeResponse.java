package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonSoortSchade;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "opvragenSoortSchadeResponse")
public class OpvragenSoortSchadeResponse {
    private List<JsonSoortSchade> soortSchade;

    public List<JsonSoortSchade> getSoortSchade() {
        if (soortSchade == null) {
            soortSchade = new ArrayList<>();
        }
        return soortSchade;
    }

    public void setSoortSchade(List<JsonSoortSchade> soortSchade) {
        this.soortSchade = soortSchade;
    }
}
