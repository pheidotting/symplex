package nl.symplex.test.builders;

import nl.lakedigital.djfc.domain.response.Adres;

public class AdresBuilder {
    private Adres adres;

    public AdresBuilder() {
        this.adres = new Adres();
    }

    public AdresBuilder defaultAdres() {
        this.adres.setStraat("Boogschutter");
        this.adres.setHuisnummer(26L);
        this.adres.setPostcode("7891TN");
        this.adres.setPlaats("Klazienaveen");
        this.adres.setSoortAdres("WOONADRES");

        return this;
    }

    public Adres build() {
        return adres;
    }
}
