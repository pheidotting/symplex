package nl.dias.domein;

public enum SoortKantoor {
    HYP("Hypotheek"), VZ("Verzekering");

    private String omschrijving;

    private SoortKantoor(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
