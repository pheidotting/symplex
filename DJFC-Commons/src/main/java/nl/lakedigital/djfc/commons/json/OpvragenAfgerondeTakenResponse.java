package nl.lakedigital.djfc.commons.json;

import java.util.ArrayList;
import java.util.List;

public class OpvragenAfgerondeTakenResponse {
    private List<Taak> taken;

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
