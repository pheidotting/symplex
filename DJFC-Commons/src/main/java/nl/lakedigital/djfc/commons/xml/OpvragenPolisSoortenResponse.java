package nl.lakedigital.djfc.commons.xml;

import java.util.ArrayList;
import java.util.List;

public class OpvragenPolisSoortenResponse {
    List<String> polisSoorten;

    public List<String> getPolisSoorten() {
        if (polisSoorten == null) {
            polisSoorten = new ArrayList<>();
        }
        return polisSoorten;
    }

    public void setPolisSoorten(List<String> polisSoorten) {
        this.polisSoorten = polisSoorten;
    }
}
