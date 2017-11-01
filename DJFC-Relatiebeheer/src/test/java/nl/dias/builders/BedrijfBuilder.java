package nl.dias.builders;

import nl.dias.domein.Bedrijf;
import nl.lakedigital.djfc.commons.json.JsonBedrijf;

public class BedrijfBuilder {
    private Bedrijf bedrijf;
    private JsonBedrijf jsonBedrijf;

    public BedrijfBuilder() {
        this.bedrijf = new Bedrijf();
        this.jsonBedrijf = new JsonBedrijf();
    }

    public BedrijfBuilder withId(Long id) {
        bedrijf.setId(id);
        jsonBedrijf.setId(id.toString());
        return this;
    }

    public BedrijfBuilder withNaam(String naam) {
        bedrijf.setNaam(naam);
        jsonBedrijf.setNaam(naam);
        return this;
    }

    public BedrijfBuilder withKvk(String kvk) {
        bedrijf.setKvk(kvk);
        jsonBedrijf.setKvk(kvk);
        return this;
    }

    public BedrijfBuilder withRechtsvorm(String rechtsvorm) {
        bedrijf.setRechtsvorm(rechtsvorm);
        jsonBedrijf.setRechtsvorm(rechtsvorm);
        return this;
    }

    public BedrijfBuilder withEmail(String email) {
        bedrijf.setEmail(email);
        jsonBedrijf.setEmail(email);
        return this;
    }

    public BedrijfBuilder withInternetadres(String internetadres) {
        bedrijf.setInternetadres(internetadres);
        jsonBedrijf.setInternetadres(internetadres);
        return this;
    }

    public BedrijfBuilder withHoedanigheid(String hoedanigheid) {
        bedrijf.setHoedanigheid(hoedanigheid);
        jsonBedrijf.setHoedanigheid(hoedanigheid);
        return this;
    }

    public BedrijfBuilder withCAoVerplichtingen(String cAoVerplichtingen) {
        bedrijf.setcAoVerplichtingen(cAoVerplichtingen);
        jsonBedrijf.setcAoVerplichtingen(cAoVerplichtingen);
        return this;
    }


    //    private List<JsonPolis> polissen;
    //    private List<JsonOpmerking> opmerkingen;
    //    private List<JsonContactPersoon> contactpersonen;
    //    private List<JsonTelefoonnummer> telefoonnummers;
    //    private List<JsonAdres> adressen;
    //    private List<JsonBijlage> bijlages;

    public Bedrijf buildBedrijf() {
        return bedrijf;
    }

    public JsonBedrijf buildJsonBedrijf() {
        return jsonBedrijf;
    }
}
