package nl.lakedigital.djfc.domain.response;

public class AanmeldenKantoor {
    private String bedrijfsnaam;
    private String afkorting;
    private String inlognaam;
    private String voornaam;
    private String achternaam;
    private String emailadres;
    private String nieuwWachtwoord;

    public AanmeldenKantoor() {
        //Moet aanwezig zijn blijkbaar
    }

    public AanmeldenKantoor(String bedrijfsnaam, String afkorting, String inlognaam, String voornaam, String achternaam, String emailadres, String nieuwWachtwoord) {
        this.bedrijfsnaam = bedrijfsnaam;
        this.afkorting = afkorting;
        this.inlognaam = inlognaam;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.emailadres = emailadres;
        this.nieuwWachtwoord = nieuwWachtwoord;
    }

    public String getBedrijfsnaam() {
        return bedrijfsnaam;
    }

    public void setBedrijfsnaam(String bedrijfsnaam) {
        this.bedrijfsnaam = bedrijfsnaam;
    }

    public String getAfkorting() {
        return afkorting;
    }

    public void setAfkorting(String afkorting) {
        this.afkorting = afkorting;
    }

    public String getInlognaam() {
        return inlognaam;
    }

    public void setInlognaam(String inlognaam) {
        this.inlognaam = inlognaam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getNieuwWachtwoord() {
        return nieuwWachtwoord;
    }

    public void setNieuwWachtwoord(String nieuwWachtwoord) {
        this.nieuwWachtwoord = nieuwWachtwoord;
    }
}
