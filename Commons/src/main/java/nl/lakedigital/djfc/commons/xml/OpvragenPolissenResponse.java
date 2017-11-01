package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonPolis;

import java.util.ArrayList;
import java.util.List;

public class OpvragenPolissenResponse {
    private List<JsonPolis> polissen;

    public List<JsonPolis> getPolissen() {
        if (polissen == null) {
            polissen = new ArrayList<>();
        }
        return polissen;
    }

    public void setPolissen(List<JsonPolis> polissen) {
        this.polissen = polissen;
    }
}
