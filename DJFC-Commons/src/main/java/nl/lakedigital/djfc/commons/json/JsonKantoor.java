package nl.lakedigital.djfc.commons.json;

public class JsonKantoor {
    private Long id;
    private String naam;
    private Long kvk;
    private String btwNummer;
    private String datumOprichting;
    private String rechtsvorm;
    private String soortKantoor;
    private String emailadres;
    private String afkorting;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Long getKvk() {
        return kvk;
    }

    public void setKvk(Long kvk) {
        this.kvk = kvk;
    }

    public String getBtwNummer() {
        return btwNummer;
    }

    public void setBtwNummer(String btwNummer) {
        this.btwNummer = btwNummer;
    }

    public String getDatumOprichting() {
        return datumOprichting;
    }

    public void setDatumOprichting(String datumOprichting) {
        this.datumOprichting = datumOprichting;
    }

    public String getRechtsvorm() {
        return rechtsvorm;
    }

    public void setRechtsvorm(String rechtsvorm) {
        this.rechtsvorm = rechtsvorm;
    }

    public String getSoortKantoor() {
        return soortKantoor;
    }

    public void setSoortKantoor(String soortKantoor) {
        this.soortKantoor = soortKantoor;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getAfkorting() {
        return afkorting;
    }

    public void setAfkorting(String afkorting) {
        this.afkorting = afkorting;
    }
}
