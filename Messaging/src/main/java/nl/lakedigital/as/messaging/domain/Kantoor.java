package nl.lakedigital.as.messaging.domain;

public class Kantoor {
    private Long id;
    private String naam;
    private String emailadres;
    private Adres adres;

    public Kantoor() {
    }

    public Kantoor(Long id, String naam, String emailadres, String straat, Long huisnummer, String toevoeging, String postcode, String plaats) {
        adres = new Adres(null, null, null, straat, huisnummer, toevoeging, postcode, plaats, null);

        this.naam = naam;
        this.emailadres = emailadres;
        this.id = id;
    }

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

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public String getStraat() {
        return adres.getStraat();
    }


    public Long getHuisnummer() {
        return adres.getHuisnummer();
    }


    public String getToevoeging() {
        return adres.getToevoeging();
    }


    public String getPostcode() {
        return adres.getPostcode();
    }


    public String getPlaats() {
        return adres.getPlaats();
    }


}