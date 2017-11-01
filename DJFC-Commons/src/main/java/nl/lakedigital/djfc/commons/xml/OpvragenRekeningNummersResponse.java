package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonRekeningNummer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpvragenRekeningNummersResponse {
    private List<JsonRekeningNummer> rekeningNummers;

    public List<JsonRekeningNummer> getRekeningNummers() {
        if (rekeningNummers == null) {
            rekeningNummers = newArrayList();
        }
        return rekeningNummers;
    }

    public void setRekeningNummers(List<JsonRekeningNummer> rekeningNummers) {
        this.rekeningNummers = rekeningNummers;
    }
}
