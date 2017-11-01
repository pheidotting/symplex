package nl.lakedigital.djfc.commons.json;

public class IngelogdeGebruiker {
    private String id;
    private String gebruikersnaam;
    private String kantoor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getKantoor() {
        return kantoor;
    }

    public void setKantoor(String kantoor) {
        this.kantoor = kantoor;
    }

}
