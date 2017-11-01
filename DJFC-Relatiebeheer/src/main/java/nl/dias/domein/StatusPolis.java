package nl.dias.domein;

public enum StatusPolis {
    OFF("Aanvraag/Offerte"), ACT("Actief"), SCH("Geschorst"), PRE("Premievrij"), BEI("Beindigd");

    private String omschrijving;

    private StatusPolis(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOmschrijving() {
        return omschrijving;
    }


}
