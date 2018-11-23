package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonPakket;

import java.util.ArrayList;
import java.util.List;

public class OpvragenPolissenResponse {
    private List<JsonPakket> pakketten;

    public List<JsonPakket> getPakketten() {
        if (pakketten == null) {
            pakketten = new ArrayList<>();
        }
        return pakketten;
    }

    public void setPakketten(List<JsonPakket> pakketten) {
        this.pakketten = pakketten;
    }
}
