package nl.lakedigital.djfc.domain.response;

public class Versie {
    private String versie;
    private String releasenotes;

    public Versie(String versie, String releasenotes) {
        this.versie = versie;
        this.releasenotes = releasenotes;
    }

    public String getVersie() {
        return versie;
    }

    public String getReleasenotes() {
        return releasenotes;
    }
}
