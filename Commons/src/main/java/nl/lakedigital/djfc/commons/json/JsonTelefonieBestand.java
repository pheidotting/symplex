package nl.lakedigital.djfc.commons.json;

public class JsonTelefonieBestand {
    private String bestandsnaam;
    private String telefoonnummer;
    private String tijdstip;

    public JsonTelefonieBestand(String bestandsnaam, String telefoonnummer, String tijdstip) {
        this.bestandsnaam = bestandsnaam;
        this.telefoonnummer = telefoonnummer;
        this.tijdstip = tijdstip;
    }

    public String getBestandsnaam() {
        return bestandsnaam;
    }

    public void setBestandsnaam(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }
}
