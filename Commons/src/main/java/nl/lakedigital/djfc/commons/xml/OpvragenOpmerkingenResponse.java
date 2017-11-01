package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonOpmerking;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpvragenOpmerkingenResponse {
    private List<JsonOpmerking> opmerkingen;

    public List<JsonOpmerking> getOpmerkingen() {
        if (opmerkingen == null) {
            opmerkingen = newArrayList();
        }
        return opmerkingen;
    }

    public void setOpmerkingen(List<JsonOpmerking> opmerkingen) {
        this.opmerkingen = opmerkingen;
    }
}
