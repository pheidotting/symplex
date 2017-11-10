package nl.lakedigital.djfc.commons.json;

public class ZoekVelden {
    private String naam;
    private String geboortedatum;
    private String tussenvoegsel;
    private String polisnummer;
    private String voorletters;
    private String schadenummer;
    private String adres;
    private String postcode;
    private String woonplaats;
    private Boolean bedrijf;

    public boolean isEmpty() {
        return (naam == null || "".equals(naam)) &&//
                (geboortedatum == null || "".equals(geboortedatum)) &&//
                (tussenvoegsel == null || "".equals(tussenvoegsel)) &&//
                (polisnummer == null || "".equals(polisnummer)) &&//
                (voorletters == null || "".equals(voorletters)) &&//
                (schadenummer == null || "".equals(schadenummer)) &&//
                (adres == null || "".equals(adres)) &&//
                (postcode == null || "".equals(postcode)) &&//
                (woonplaats == null || "".equals(woonplaats));
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getPolisnummer() {
        return polisnummer;
    }

    public void setPolisnummer(String polisnummer) {
        this.polisnummer = polisnummer;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getSchadenummer() {
        return schadenummer;
    }

    public void setSchadenummer(String schadenummer) {
        this.schadenummer = schadenummer;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public Boolean getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(Boolean bedrijf) {
        this.bedrijf = bedrijf;
    }
}
