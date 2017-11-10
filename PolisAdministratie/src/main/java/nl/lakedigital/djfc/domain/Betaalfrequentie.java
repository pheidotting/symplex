package nl.lakedigital.djfc.domain;

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
