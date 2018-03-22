package nl.lakedigital.djfc.commons.json;

public class Licentie {
    private String soort;
    private String einddatum;

    public Licentie() {
    }

    public Licentie(String soort, String einddatum) {
        this.soort = soort;
        this.einddatum = einddatum;
    }

    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public String getEinddatum() {
        return einddatum;
    }

    public void setEinddatum(String einddatum) {
        this.einddatum = einddatum;
    }
}
