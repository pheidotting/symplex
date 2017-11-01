package nl.dias.web.medewerker;

/**
 * Created by patrickheidotting on 17-01-17.
 */
public class WachtwoordWijzigen {
    private String identificatie;
    private String wachtwoord;

    public WachtwoordWijzigen() {
    }

    public WachtwoordWijzigen(String identificatie, String wachtwoord) {
        this.identificatie = identificatie;
        this.wachtwoord = wachtwoord;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
