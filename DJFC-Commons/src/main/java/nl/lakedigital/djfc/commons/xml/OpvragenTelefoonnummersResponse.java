package nl.lakedigital.djfc.commons.xml;

import nl.lakedigital.djfc.commons.json.JsonTelefoonnummer;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class OpvragenTelefoonnummersResponse {
    private List<JsonTelefoonnummer> telefoonnummers;

    public List<JsonTelefoonnummer> getTelefoonnummers() {
        if (telefoonnummers == null) {
            telefoonnummers = newArrayList();
        }
        return telefoonnummers;
    }

    public void setTelefoonnummers(List<JsonTelefoonnummer> telefoonnummers) {
        this.telefoonnummers = telefoonnummers;
    }
}
