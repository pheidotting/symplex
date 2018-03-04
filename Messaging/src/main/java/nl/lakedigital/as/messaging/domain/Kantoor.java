package nl.lakedigital.as.messaging.domain;

public class Kantoor {
    private String naam;
    private Adres adres;

    public Kantoor() {
    }

    public Kantoor(String naam, String straat, Long huisnummer, String toevoeging, String postcode, String plaats) {
        adres = new Adres(null, null, null, straat, huisnummer, toevoeging, postcode, plaats, null);

        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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