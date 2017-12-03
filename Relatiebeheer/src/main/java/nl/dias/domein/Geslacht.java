package nl.dias.domein;

public enum Geslacht {
    M("Man"), V("Vrouw");

    private String omschrijving;

    private Geslacht(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
