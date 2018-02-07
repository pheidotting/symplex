package nl.lakedigital.djfc.commons.json;

public class IngelogdeGebruiker {
    private String id;
    private String gebruikersnaam;
    private String kantoor;
    private String kantoorAfkorting;
    private Long kantoorId;

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

    public String getKantoorAfkorting() {
        return kantoorAfkorting;
    }

    public void setKantoorAfkorting(String kantoorAfkorting) {
        this.kantoorAfkorting = kantoorAfkorting;
    }

    public Long getKantoorId() {
        return kantoorId;
    }

    public void setKantoorId(Long kantoorId) {
        this.kantoorId = kantoorId;
    }
}
