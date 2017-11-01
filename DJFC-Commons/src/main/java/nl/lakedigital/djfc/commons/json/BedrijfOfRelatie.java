package nl.lakedigital.djfc.commons.json;

public abstract class BedrijfOfRelatie {
    private Long id;
    private String identificatie;
    private JsonAdres adres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public JsonAdres getAdres() {
        return adres;
    }

    public void setAdres(JsonAdres adres) {
        this.adres = adres;
    }
}
