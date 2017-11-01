package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class ZoekIdentificatieResponse {
    private List<Identificatie> identificaties;

    public List<Identificatie> getIdentificaties() {
        if (identificaties == null) {
            identificaties = new ArrayList<>();
        }
        return identificaties;
    }

    public void setIdentificaties(List<Identificatie> identificaties) {
        this.identificaties = identificaties;
    }
}
