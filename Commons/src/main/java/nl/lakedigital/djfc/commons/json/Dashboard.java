package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private List<JsonSchade> openSchades;
    private List<RelatieZoekResultaat> relaties;
    private List<BedrijfZoekResultaat> bedrijven;
    private List<Taak> taken;

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

    public List<BedrijfZoekResultaat> getBedrijven() {
        if (bedrijven == null) {
            bedrijven = new ArrayList<>();
        }
        return bedrijven;
    }

    public void setBedrijven(List<BedrijfZoekResultaat> bedrijven) {
        this.bedrijven = bedrijven;
    }

    public List<Taak> getTaken() {
        if (taken == null) {
            taken = new ArrayList<>();
        }
        return taken;
    }

    public void setTaken(List<Taak> taken) {
        this.taken = taken;
    }
}
