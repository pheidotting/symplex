package nl.lakedigital.djfc.commons.json;

import java.io.Serializable;

public final class WijzigenOmschrijvingBijlage implements Serializable {
    private Long bijlageId;
    private String nieuweOmschrijving;

    public Long getBijlageId() {
        return bijlageId;
    }

    public void setBijlageId(Long bijlageId) {
        this.bijlageId = bijlageId;
    }

    public String getNieuweOmschrijving() {
        return nieuweOmschrijving;
    }

    public void setNieuweOmschrijving(String nieuweOmschrijving) {
        this.nieuweOmschrijving = nieuweOmschrijving;
    }
}
