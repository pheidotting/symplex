package nl.dias;

import nl.dias.domein.Bedrijf;
import nl.dias.domein.Relatie;

import java.util.ArrayList;
import java.util.List;

public class ZoekResultaat {
    private List<Relatie> relaties;
    private List<Bedrijf> bedrijven;

    public List<Relatie> getRelaties() {
        if (relaties == null) {
            relaties = new ArrayList();
        }
        return relaties;
    }

    public void setRelaties(List<Relatie> relaties) {
        this.relaties = relaties;
    }

    public List<Bedrijf> getBedrijven() {
        if (bedrijven == null) {
            bedrijven = new ArrayList();
        }
        return bedrijven;
    }

    public void setBedrijven(List<Bedrijf> bedrijven) {
        this.bedrijven = bedrijven;
    }
}
