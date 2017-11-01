package nl.lakedigital.djfc.commons.xml;


import nl.lakedigital.djfc.commons.json.AbstracteJsonEntiteitMetSoortEnId;

import java.util.List;

public abstract class AbstractOGAResponse {
    private List<AbstracteJsonEntiteitMetSoortEnId> lijst;

    public List<AbstracteJsonEntiteitMetSoortEnId> getLijst() {
        return lijst;
    }

    public void setLijst(List<AbstracteJsonEntiteitMetSoortEnId> lijst) {
        this.lijst = lijst;
    }
}
