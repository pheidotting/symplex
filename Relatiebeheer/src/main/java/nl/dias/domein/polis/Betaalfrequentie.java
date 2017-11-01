package nl.dias.domein.polis;

public enum Betaalfrequentie {
    J("Jaar"), K("Kwartaal"), M("Maand"), H("Half jaar");
    private String omschrijving;

    private Betaalfrequentie(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

}
