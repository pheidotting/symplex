package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.Taak;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpvragenTakenResponse {
    private List<Taak> taken;

    public List<Taak> getTaken() {
        if (taken == null) {
            taken = newArrayList();
        }
        return taken;
    }

    public void setTaken(List<Taak> taken) {
        this.taken = taken;
    }
}
