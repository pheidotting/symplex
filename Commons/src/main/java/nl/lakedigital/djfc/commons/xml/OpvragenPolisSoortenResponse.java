package nl.lakedigital.djfc.commons.xml;

import java.util.HashMap;
import java.util.Map;

public class OpvragenPolisSoortenResponse {
    Map<String, String> polisSoorten;

    public Map<String, String> getPolisSoorten() {
        if (polisSoorten == null) {
            polisSoorten = new HashMap<>();
        }
        return polisSoorten;
    }

    public void setPolisSoorten(Map<String, String> polisSoorten) {
        this.polisSoorten = polisSoorten;
    }
}
