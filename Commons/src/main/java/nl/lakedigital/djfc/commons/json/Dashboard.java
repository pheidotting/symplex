package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private List<JsonSchade> openSchades;
    private List<RelatieZoekResultaat> relaties;

    public List<JsonSchade> getOpenSchades() {
        if (openSchades == null) {
            openSchades = new ArrayList<>();
        }
        return openSchades;
    }

    public void setOpenSchades(List<JsonSchade> openSchades) {
        this.openSchades = openSchades;
    }

    public List<RelatieZoekResultaat> getRelaties() {
        if (relaties == null) {
            relaties = new ArrayList<>();
        }
        return relaties;
    }

    public void setRelaties(List<RelatieZoekResultaat> relaties) {
        this.relaties = relaties;
    }
}
