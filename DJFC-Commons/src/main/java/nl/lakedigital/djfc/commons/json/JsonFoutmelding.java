package nl.lakedigital.djfc.commons.json;

public class JsonFoutmelding {
    private String foutmelding;

    public JsonFoutmelding(String foutmelding) {
        this.foutmelding = foutmelding;
    }

    public JsonFoutmelding() {
    }

    public String getFoutmelding() {
        return foutmelding;
    }

    public void setFoutmelding(String foutmelding) {
        this.foutmelding = foutmelding;
    }
}
