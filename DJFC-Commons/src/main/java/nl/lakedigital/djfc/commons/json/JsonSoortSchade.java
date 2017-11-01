package nl.lakedigital.djfc.commons.json;

public class JsonSoortSchade {
    private final String value;

    public JsonSoortSchade(String tekst) {
        this.value = tekst;
    }

    public String getValue() {
        return value;
    }
}
