package nl.lakedigital.djfc.domain.response;

public class Versie {
    private String versienummer;
    private String releasenotes;

    public Versie(String versienummer, String releasenotes) {
        this.versienummer = versienummer;
        this.releasenotes = releasenotes;
    }

    public String getVersienummer() {
        return versienummer;
    }

    public void setVersienummer(String versienummer) {
        this.versienummer = versienummer;
    }

    public String getReleasenotes() {
        return releasenotes;
    }

    public void setReleasenotes(String releasenotes) {
        this.releasenotes = releasenotes;
    }
}
