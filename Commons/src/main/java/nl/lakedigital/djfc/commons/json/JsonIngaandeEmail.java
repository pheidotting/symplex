package nl.lakedigital.djfc.commons.json;

import java.util.Objects;

public class JsonIngaandeEmail extends JsonEmail {
    private boolean ongelezenIndicatie;

    public boolean isOngelezenIndicatie() {
        return ongelezenIndicatie;
    }

    public void setOngelezenIndicatie(boolean ongelezenIndicatie) {
        this.ongelezenIndicatie = ongelezenIndicatie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JsonIngaandeEmail)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        JsonIngaandeEmail that = (JsonIngaandeEmail) o;
        return isOngelezenIndicatie() == that.isOngelezenIndicatie();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isOngelezenIndicatie());
    }
}
