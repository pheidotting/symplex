package nl.lakedigital.as.messaging.entities;

import java.io.Serializable;

public class Adres implements Serializable {
    private String identificatie;
    private String straat;
    private Long huisnummer;
    private String toevoeging;
    private String postcode;
    private String plaats;
    private String soortAdres;

    public Adres() {
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Long getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Long huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getSoortAdres() {
        return soortAdres;
    }

    public void setSoortAdres(String soortAdres) {
        this.soortAdres = soortAdres;
    }
}
