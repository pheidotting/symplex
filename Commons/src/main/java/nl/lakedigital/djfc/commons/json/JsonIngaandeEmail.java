package nl.lakedigital.djfc.commons.json;

public class JsonIngaandeEmail extends JsonEmail {
    private boolean ongelezenIndicatie;

    public boolean isOngelezenIndicatie() {
        return ongelezenIndicatie;
    }

    public void setOngelezenIndicatie(boolean ongelezenIndicatie) {
        this.ongelezenIndicatie = ongelezenIndicatie;
    }
}
