package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonAdres;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpvragenAdressenResponse {
    private List<JsonAdres> adressen;

    public List<JsonAdres> getAdressen() {
        if (adressen == null) {
            adressen = newArrayList();
        }
        return adressen;
    }

    public void setAdressen(List<JsonAdres> adressen) {
        this.adressen = adressen;
    }
}
